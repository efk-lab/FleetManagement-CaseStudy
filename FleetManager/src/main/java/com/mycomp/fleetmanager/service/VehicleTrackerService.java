package com.mycomp.fleetmanager.service;

import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.mycomp.fleetmanager.constant.DeliveryPoints;
import com.mycomp.fleetmanager.constant.ShipmentStatus;
import com.mycomp.fleetmanager.constant.ShipmentType;
import com.mycomp.fleetmanager.dao.ShipmentDao;
import com.mycomp.fleetmanager.document.DeliveryLocation;
import com.mycomp.fleetmanager.document.DeliveryLocationLog;
import com.mycomp.fleetmanager.document.DeliveryPoint;
import com.mycomp.fleetmanager.document.Distribution;
import com.mycomp.fleetmanager.document.DistributionLog;
import com.mycomp.fleetmanager.document.Shipment;
import com.mycomp.fleetmanager.dto.CheckDeliveryDto;
import com.mycomp.fleetmanager.event.VehicleLocationEvent;
import com.mycomp.fleetmanager.repository.DeliveryPointRepository;
import com.mycomp.fleetmanager.repository.DistributionLogRepository;
import com.mycomp.fleetmanager.repository.DistributionRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class VehicleTrackerService extends BaseService {

	@Autowired
	private DeliveryPointRepository deliveryPointRepository;

	@Autowired
	private DistributionRepository distributionRepository;

	@Autowired
	private DistributionLogRepository distributionLogRepository;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private ShipmentDao shipmentDao;
	
	@Autowired
	private ShipmentService shipmentService;
	

	@KafkaListener(topics = "${kafka.consumer.topic.name}", containerFactory = "vehicleLocationKafkaListenerContainerFactory")
	public void trackVehicle(VehicleLocationEvent vehicleLocation) { 

		if (vehicleLocation != null) {
			processMessageVehicleLocation(vehicleLocation);
		} else {
			log.error("Processing vehicleLocation message failed.");
		}

	}

	private void processMessageVehicleLocation(VehicleLocationEvent vehicleLocation) {

		log.info("Processing vehicleLocation message. DistributionID:" + vehicleLocation.getDistributionId());

		DeliveryPoint messageDeliveryPoint = deliveryPointRepository.findByDeliveryPointValue(vehicleLocation.getDeliveryPoint());
		Distribution distribution = distributionRepository.findById(new ObjectId(vehicleLocation.getDistributionId())).orElseThrow();

		if (distribution != null && messageDeliveryPoint != null) {

			List<DeliveryLocation> distributionRoute = distribution.getRoute();

			if (distributionRoute != null && !distributionRoute.isEmpty()) {
				List<Shipment> shipments = distributionRoute.stream().filter(route -> route.getDeliveryPoint().getDeliveryPointValue() == messageDeliveryPoint.getDeliveryPointValue())
										.map(route -> route.getShipments())
										.collect(Collectors.toList()).get(0);
				
				shipments.stream().forEach(shipment -> checkUnloadRulesForDelivery(new CheckDeliveryDto(messageDeliveryPoint, distribution, shipment)));
            }

		}

	}

	private void checkUnloadRulesForDelivery(CheckDeliveryDto checkDeliveryParams) {
		Shipment shipment = checkDeliveryParams.getShipment();
		DeliveryPoint messageDeliveryPoint = checkDeliveryParams.getMessageDeliveryPoint();
		
		boolean isIncorrectDeliveryPointAndShipmentPair = checkIncorrectDeliveryPointAndShipmentPair(checkDeliveryParams);

		if (!isIncorrectDeliveryPointAndShipmentPair) {
			checkUnloadForPackage(shipment, messageDeliveryPoint);
			checkUnloadForBag(shipment, messageDeliveryPoint);
		}

	}

	private boolean checkIncorrectDeliveryPointAndShipmentPair(CheckDeliveryDto checkDeliveryParams) {

		boolean isIncorrectlyPairedDeliveryPointAndShipment = false;
		Shipment shipment = checkDeliveryParams.getShipment();
		DeliveryPoint messageDeliveryPoint = checkDeliveryParams.getMessageDeliveryPoint();
		
		if (shipment.getDeliveryPoint().getDeliveryPointValue() != messageDeliveryPoint.getDeliveryPointValue()) {
			logIncorrectlyPairedDeliveryPointAndShipment(checkDeliveryParams);

			isIncorrectlyPairedDeliveryPointAndShipment = true;
		}

		return isIncorrectlyPairedDeliveryPointAndShipment;
	}

	private void checkUnloadForPackage(Shipment shipment, DeliveryPoint messageDeliveryPoint) {

		if (ShipmentType.PACKAGE.getValue() == shipment.getShipmentType()) {

			if (shipment.getBagBarcode() == null) {
				if (DeliveryPoints.BRANCH.getValue() == messageDeliveryPoint.getDeliveryPointValue()
						|| DeliveryPoints.DISTRIBUTION_CENTER.getValue() == messageDeliveryPoint.getDeliveryPointValue()) {
					unloadShipment(shipment);
				}

			} else {
				unloadShipmentInBag(shipment);
			}

		}
	}

	private void checkUnloadForBag(Shipment shipment, DeliveryPoint messageDeliveryPoint) {

		if (ShipmentType.BAG.getValue() == shipment.getShipmentType()) {

			if (DeliveryPoints.TRANSFER_CENTER.getValue() == messageDeliveryPoint.getDeliveryPointValue()
					|| DeliveryPoints.DISTRIBUTION_CENTER.getValue() == messageDeliveryPoint.getDeliveryPointValue()) {
				unloadBag(shipment);
			}

		}
	}

	private void unloadShipmentInBag(Shipment shipment) {

		unloadShipment(shipment);

		List<Shipment> packagesInBag = shipmentDao.findByBagBarcode(shipment.getBagBarcode());
		List<Shipment> unloadedPackagesInBag = shipmentDao.findByBagBarcodeAndShipmentStatus(shipment.getBagBarcode(), ShipmentStatus.UNLOADED.getValue());
		
		if (packagesInBag.size() == unloadedPackagesInBag.size()) {
			Shipment bag = shipmentDao.findByBarcode(shipment.getBagBarcode());
			unloadShipment(bag);
		}

	}
	
	private Shipment unloadShipment(Shipment shipment) {

		log.info("Processing unload for delivery. Delivery:" + shipment.getBarcode());

		return shipmentService.changeShipmentStatus(shipment, ShipmentStatus.UNLOADED.getValue());

	}

	private void unloadBag(Shipment shipment) {

		log.info("Processing unload for bag delivery. Delivery:" + shipment.getBarcode());

		unloadShipment(shipment);
		
		List<Shipment> packagesInBag = shipmentDao.findByBagBarcode(shipment.getBarcode());
		packagesInBag.stream().forEach(packageItem -> unloadShipment(packageItem));

	}

	private void logIncorrectlyPairedDeliveryPointAndShipment(CheckDeliveryDto checkDeliveryParams) {

		log.info("Logging incorrectly paired DeliveryPoint and Delivery. DistributionID:" + checkDeliveryParams.getDistribution().getDistributionId() + " Plate:"
				+ checkDeliveryParams.getDistribution().getPlate() + " Delivery:" + checkDeliveryParams.getShipment().getBarcode());

		Shipment shipment = checkDeliveryParams.getShipment();
		Distribution distribution = checkDeliveryParams.getDistribution();
		DeliveryPoint messageDeliveryPoint = checkDeliveryParams.getMessageDeliveryPoint();
		DistributionLog distributionLog = distributionLogRepository.findByDistributionIdAndRouteDeliveryPoint(distribution.getDistributionId(), checkDeliveryParams.getMessageDeliveryPoint().getDeliveryPointValue());
		List<DeliveryLocationLog> routeLog = distributionLog.getRoute();
	
		List<String> shipments = routeLog.stream().filter(route -> route.getDeliveryPoint() == messageDeliveryPoint.getDeliveryPointValue())
				.map(route -> route.getDeliveries())
				.collect(Collectors.toList()).get(0);
		
		shipments.add(shipment.getBarcode());
		
		log.debug("ShipmentsSize:" + shipments.size());
		shipments.stream().forEach(shipmentItem -> log.debug("Shipment:" + shipmentItem));

		Query query = new Query(new Criteria().andOperator(
				Criteria.where("distributionId").is(distribution.getDistributionId()),
				Criteria.where("route").elemMatch(Criteria.where("deliveryPoint").is(messageDeliveryPoint.getDeliveryPointValue())
				)));
		Update update = new Update();
		update.set("route.$.deliveries", shipments);
		mongoTemplate.findAndModify(query, update, DistributionLog.class);
		
	}

}
