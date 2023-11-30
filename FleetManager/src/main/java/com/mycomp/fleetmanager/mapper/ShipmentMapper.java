package com.mycomp.fleetmanager.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycomp.fleetmanager.constant.ShipmentStatus;
import com.mycomp.fleetmanager.constant.ShipmentType;
import com.mycomp.fleetmanager.dao.ShipmentDao;
import com.mycomp.fleetmanager.document.Shipment;
import com.mycomp.fleetmanager.model.AssignPackageToBagRequest;
import com.mycomp.fleetmanager.model.AssignPackageToBagResponse;
import com.mycomp.fleetmanager.model.GetShipmentResponse;
import com.mycomp.fleetmanager.model.SaveBagRequest;
import com.mycomp.fleetmanager.model.SaveBagResponse;
import com.mycomp.fleetmanager.model.SavePackageRequest;
import com.mycomp.fleetmanager.model.SavePackageResponse;
import com.mycomp.fleetmanager.repository.DeliveryPointRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ShipmentMapper extends BaseMapper {
	
	@Autowired
	private DeliveryPointRepository deliveryPointRepository;
	
	@Autowired
	private ShipmentDao shipmentDao;
	

	public Shipment toShipment(SavePackageRequest savePackageRequest) {

		log.info("Mapping SavePackageRequest to Shipment. SavePackageRequest:" + savePackageRequest.toString() + " User:" + userService.getUser());

		Shipment shipment = new Shipment();
		shipment.setBarcode(savePackageRequest.getBarcode());
		shipment.setDeliveryPoint(deliveryPointRepository.findByDeliveryPointValue(savePackageRequest.getDeliveryPoint()));
		shipment.setShipmentType(ShipmentType.PACKAGE.getValue());
		shipment.setShipmentStatus(ShipmentStatus.CREATED.getValue());
		shipment.setVolumetricWeight(savePackageRequest.getVolumetricWeight());
		
		return shipment;

	}
	
	public Shipment toShipment(SaveBagRequest saveBagRequest) {

		log.info("Mapping SaveBagRequest to Shipment. SaveBagRequest: " + saveBagRequest.toString() + " User:" + userService.getUser());

		Shipment shipment = new Shipment();
		shipment.setBarcode(saveBagRequest.getBarcode());
		shipment.setDeliveryPoint(deliveryPointRepository.findByDeliveryPointValue(saveBagRequest.getDeliveryPoint()));
		shipment.setShipmentType(ShipmentType.BAG.getValue());
		shipment.setShipmentStatus(ShipmentStatus.CREATED.getValue());
		
		return shipment;

	}

	public SavePackageResponse toSavePackageResponse(Shipment shipment) {

		log.info("Mapping Shipment to SavePackageResponse. Shipment: " + shipment.toString() + " User:" + userService.getUser());

		SavePackageResponse savePackageResponse = new SavePackageResponse();
		savePackageResponse = (SavePackageResponse)toBaseResponse(savePackageResponse, shipment);
		savePackageResponse.setShipmentId(shipment.getShipmentId());
		savePackageResponse.setBarcode(shipment.getBarcode());
		savePackageResponse.setBagBarcode(shipment.getBagBarcode());
		savePackageResponse.setDeliveryPoint(shipment.getDeliveryPoint().getDeliveryPointName());
		savePackageResponse.setShipmentType(ShipmentType.nameOf(shipment.getShipmentType()));
		savePackageResponse.setVolumetricWeight(shipment.getVolumetricWeight());
		savePackageResponse.setShipmentStatus(ShipmentStatus.nameOf(shipment.getShipmentStatus()));
		
		return savePackageResponse;

	}
	
	public SaveBagResponse toSaveBagResponse(Shipment shipment) {

		log.info("Mapping Shipment to SaveBagResponse. Shipment: " + shipment.toString() + " User:" + userService.getUser());

		SaveBagResponse saveBagResponse = new SaveBagResponse();
		saveBagResponse = (SaveBagResponse)toBaseResponse(saveBagResponse, shipment);
		saveBagResponse.setShipmentId(shipment.getShipmentId());
		saveBagResponse.setBarcode(shipment.getBarcode());
		saveBagResponse.setDeliveryPoint(shipment.getDeliveryPoint().getDeliveryPointName());
		saveBagResponse.setShipmentType(ShipmentType.nameOf(shipment.getShipmentType()));
		saveBagResponse.setShipmentStatus(ShipmentStatus.nameOf(shipment.getShipmentStatus()));
		
		return saveBagResponse;

	}
	
	public GetShipmentResponse toGetShipmentResponse(Shipment shipment) {
		
		log.info("Mapping Shipment to GetShipmentResponse. Shipment: " + shipment.toString() + " User:" + userService.getUser());

		GetShipmentResponse getShipmentResponse = new GetShipmentResponse();
		getShipmentResponse = (GetShipmentResponse)toBaseResponse(getShipmentResponse, shipment);
		getShipmentResponse.setShipmentId(shipment.getShipmentId());
		getShipmentResponse.setBarcode(shipment.getBarcode());
		getShipmentResponse.setBagBarcode(shipment.getBagBarcode());
		getShipmentResponse.setDeliveryPoint(shipment.getDeliveryPoint().getDeliveryPointName());
		getShipmentResponse.setShipmentType(ShipmentType.nameOf(shipment.getShipmentType()));
		getShipmentResponse.setVolumetricWeight(shipment.getVolumetricWeight());
		getShipmentResponse.setShipmentStatus(ShipmentStatus.nameOf(shipment.getShipmentStatus()));
		
		return getShipmentResponse;
		
	}

	public Shipment toShipment(AssignPackageToBagRequest assignShipmentRequest) {

		log.info("Mapping AssignPackageToBagRequest to Shipment. AssignPackageToBagRequest: " + assignShipmentRequest.toString() + " User:" + userService.getUser());

		Shipment shipment = shipmentDao.findByBarcode(assignShipmentRequest.getPackageBarcode());
		shipment.setBagBarcode(assignShipmentRequest.getBagBarcode());
		
		return shipment;

	}

	public AssignPackageToBagResponse toAssignPackageToBagResponse(Shipment shipment) {

		log.info("Mapping Shipment to AssignPackageToBagResponse. Shipment: " + shipment.toString() + " User:" + userService.getUser());

		AssignPackageToBagResponse assignPackageToBagResponse = new AssignPackageToBagResponse();
		assignPackageToBagResponse = (AssignPackageToBagResponse)toBaseResponse(assignPackageToBagResponse, shipment);
		assignPackageToBagResponse.setBagBarcode(shipment.getBagBarcode());
		assignPackageToBagResponse.setPackageBarcode(shipment.getBarcode());
		
		return assignPackageToBagResponse;

	}

}
