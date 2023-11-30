package com.mycomp.fleetmanager.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycomp.fleetmanager.conf.security.UserService;
import com.mycomp.fleetmanager.constant.ShipmentStatus;
import com.mycomp.fleetmanager.constant.ShipmentType;
import com.mycomp.fleetmanager.dao.ShipmentDao;
import com.mycomp.fleetmanager.document.DeliveryLocation;
import com.mycomp.fleetmanager.document.Distribution;
import com.mycomp.fleetmanager.document.Shipment;
import com.mycomp.fleetmanager.dto.DeliveryDto;
import com.mycomp.fleetmanager.dto.DeliveryLocationDto;
import com.mycomp.fleetmanager.dto.DeliveryLocationStatusDto;
import com.mycomp.fleetmanager.dto.DeliveryStatusDto;
import com.mycomp.fleetmanager.model.GetDistributionResponse;
import com.mycomp.fleetmanager.model.SaveDistributionRequest;
import com.mycomp.fleetmanager.model.SaveDistributionResponse;
import com.mycomp.fleetmanager.repository.DeliveryPointRepository;
import com.mycomp.fleetmanager.repository.VehicleRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DistributionMapper extends BaseMapper {

	@Autowired
	private DeliveryPointRepository deliveryPointRepository;

	@Autowired
	private VehicleRepository vehicleRepository;

	@Autowired
	private UserService userService;
	
	@Autowired
	private ShipmentDao shipmentDao;
	

	public Distribution toDistribution(SaveDistributionRequest saveDistributionRequest) {

		log.info("Mapping SaveDistributionRequest to Distribution. " + saveDistributionRequest.toString() + " User:" + userService.getUser());

		Distribution distribution = new Distribution();
		distribution.setPlate(vehicleRepository.findByPlate(saveDistributionRequest.getPlate()));
		distribution.setRoute(toDeliveryLocation(saveDistributionRequest.getRoute()));

		return distribution;

	}

	private List<DeliveryLocation> toDeliveryLocation(List<DeliveryLocationDto> route) {

		List<DeliveryLocation> deliveryLocations = new ArrayList<DeliveryLocation>();

		for (DeliveryLocationDto deliveryLocationDto : route) {
			DeliveryLocation deliveryLocation = new DeliveryLocation();

			deliveryLocation.setDeliveryPoint(deliveryPointRepository.findByDeliveryPointValue(deliveryLocationDto.getDeliveryPoint()));
			deliveryLocation.setShipments(toShipment(deliveryLocationDto.getDeliveries()));

			deliveryLocations.add(deliveryLocation);
		}

		return deliveryLocations;

	}

	private List<Shipment> toShipment(List<DeliveryDto> deliveries) {
		List<Shipment> shipmentList = new ArrayList<Shipment>();

		for (DeliveryDto deliveryDto : deliveries) {
			Shipment shipment = shipmentDao.findByBarcode(deliveryDto.getBarcode());

			if (ShipmentType.PACKAGE.getValue() == shipment.getShipmentType() && shipment.getBagBarcode() != null && !shipment.getBagBarcode().isEmpty()) {

				shipment.setShipmentStatus(ShipmentStatus.LOADED_INTO_BAG.getValue());

			} else {
				shipment.setShipmentStatus(ShipmentStatus.LOADED.getValue());

			}

			shipmentList.add(shipment);
		}

		return shipmentList;
	}

	public SaveDistributionResponse toSaveDistributionResponse(Distribution distribution) {

		log.info("Mapping Distribution to SaveDistributionResponse. Distribution:" + distribution.toString() + " User:" + userService.getUser());

		SaveDistributionResponse saveDistributionResult = new SaveDistributionResponse();
		saveDistributionResult = (SaveDistributionResponse) toBaseResponse(saveDistributionResult, distribution);
		saveDistributionResult.setDistributionId(distribution.getDistributionId());
		saveDistributionResult.setPlate(distribution.getPlate().getPlate());
		saveDistributionResult.setRoute(toDeliveryLocationStatusDto(distribution.getRoute()));

		return saveDistributionResult;

	}

	private List<DeliveryLocationStatusDto> toDeliveryLocationStatusDto(List<DeliveryLocation> route) {

		List<DeliveryLocationStatusDto> deliveryLocationStatusList = new ArrayList<DeliveryLocationStatusDto>();

		for (DeliveryLocation deliveryLocation : route) {
			DeliveryLocationStatusDto deliveryLocationStatusDto = new DeliveryLocationStatusDto();

			deliveryLocationStatusDto.setDeliveryPoint(deliveryLocation.getDeliveryPoint().getDeliveryPointValue());
			deliveryLocationStatusDto.setDeliveries(toDeliveryStatusDto(deliveryLocation.getShipments()));

			deliveryLocationStatusList.add(deliveryLocationStatusDto);
		}

		return deliveryLocationStatusList;

	}

	private List<DeliveryStatusDto> toDeliveryStatusDto(List<Shipment> shipments) {

		List<DeliveryStatusDto> deliveryStatusList = new ArrayList<DeliveryStatusDto>();

		for (Shipment shipment : shipments) {
			DeliveryStatusDto deliveryStatusDto = new DeliveryStatusDto();

			deliveryStatusDto.setBarcode(shipment.getBarcode());
			deliveryStatusDto.setStatus(shipment.getShipmentStatus());

			deliveryStatusList.add(deliveryStatusDto);
		}

		return deliveryStatusList;

	}

	public GetDistributionResponse toGetDistributionResponse(Optional<Distribution> distribution) {

		Distribution resultDistribution = distribution.orElseThrow();
		log.info("Mapping Distribution to GetDistributionResponse. Distribution: " + resultDistribution.getDistributionId() + " User:" + userService.getUser());

		GetDistributionResponse distributionResult = new GetDistributionResponse();
		distributionResult = (GetDistributionResponse) toBaseResponse(distributionResult, resultDistribution);
		distributionResult.setDistributionId(resultDistribution.getDistributionId());
		distributionResult.setPlate(resultDistribution.getPlate().getPlate());
		distributionResult.setRoute(toDeliveryLocationStatusDto(resultDistribution.getRoute()));

		return distributionResult;

	}

}
