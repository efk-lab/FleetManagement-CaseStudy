package com.mycomp.fleetmanager.validator;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycomp.fleetmanager.dao.ShipmentDao;
import com.mycomp.fleetmanager.document.DeliveryPoint;
import com.mycomp.fleetmanager.document.Shipment;
import com.mycomp.fleetmanager.document.Vehicle;
import com.mycomp.fleetmanager.dto.DeliveryDto;
import com.mycomp.fleetmanager.dto.DeliveryLocationDto;
import com.mycomp.fleetmanager.error.FleetManagerException;
import com.mycomp.fleetmanager.model.GetDistributionRequest;
import com.mycomp.fleetmanager.model.SaveDistributionRequest;
import com.mycomp.fleetmanager.repository.DeliveryPointRepository;
import com.mycomp.fleetmanager.repository.VehicleRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DistributionValidator extends BaseValidator {

	@Autowired
	private VehicleRepository vehicleRepository;

	@Autowired
	private DeliveryPointRepository deliveryPointRepository;
	
	@Autowired
	private ShipmentDao shipmentDao;
	
	
	public void validateSaveDistributionRequest(SaveDistributionRequest saveDistributionRequest) throws FleetManagerException {

		validateRequest(saveDistributionRequest);
		validatePlate(saveDistributionRequest.getPlate());
		validateRoute(saveDistributionRequest.getRoute());

		log.info("SaveDistributionRequest validated. SaveDistributionRequest : " + saveDistributionRequest.toString() + " User:" + userService.getUser());

	}

	private void validatePlate(String plate) {

		Vehicle vehicle = vehicleRepository.findByPlate(plate);

		if (vehicle == null) {
			throw new FleetManagerException("Vehicle does not exist.");
		}

	}

	private void validateRoute(List<DeliveryLocationDto> route) {

		if (route == null || route.isEmpty()) {
			throw new FleetManagerException("Route cannot be empty.");
		}

		route.forEach(distributionRoute -> validateDeliveryLocation(distributionRoute));

	}

	private void validateDeliveryLocation(DeliveryLocationDto deliveryLocationDto) {

		validateDeliveryPoint(deliveryLocationDto);
		validateDeliveries(deliveryLocationDto);

	}

	private void validateDeliveryPoint(DeliveryLocationDto deliveryLocationDto) {
		
		DeliveryPoint deliveryPoint = deliveryPointRepository.findByDeliveryPointValue(deliveryLocationDto.getDeliveryPoint());
		
		if (deliveryPoint == null) {
			throw new FleetManagerException("DeliveryPoint does not exist.");
		}

	}

	private void validateDeliveries(DeliveryLocationDto deliveryLocationDto) {

		List<DeliveryDto> deliveries = deliveryLocationDto.getDeliveries();
		
		if (deliveries == null || deliveries.isEmpty()) {
			throw new FleetManagerException("Deliveries cannot be empty.");
		} 

		deliveries.stream().forEach(deliveryDto -> validateDelivery(deliveryLocationDto.getDeliveryPoint(), deliveryDto));

	}
	
	private void validateDelivery(int deliveryPoint, DeliveryDto deliveryDto) {
		
		String barcode = deliveryDto.getBarcode();
		if (barcode == null || barcode.isBlank()) {
			throw new FleetManagerException("Delivery cannot be empty, DeliveryPoint:" + deliveryPoint);
		} 

		Shipment shipment = shipmentDao.findByBarcode(barcode);
		if (shipment == null) {
			throw new FleetManagerException("Delivery cannot be found, DeliveryPoint :" + deliveryPoint + " Delivery Barcode:" + barcode + ". ");
		}
		
	}

	public void validateGetDistributionRequest(GetDistributionRequest getDistributionRequest) throws FleetManagerException {

		validateRequest(getDistributionRequest);
		validateDistributionId(new ObjectId(getDistributionRequest.getDistributionId()));
		
		log.info("GetDistributionRequest validated. GetDistributionRequest: " + getDistributionRequest.toString() + " User:" + userService.getUser());

	}

}
