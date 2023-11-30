package com.mycomp.fleetmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycomp.fleetmanager.dao.ShipmentDao;
import com.mycomp.fleetmanager.document.Shipment;
import com.mycomp.fleetmanager.error.FleetManagerException;
import com.mycomp.fleetmanager.mapper.ShipmentMapper;
import com.mycomp.fleetmanager.model.AssignPackageToBagRequest;
import com.mycomp.fleetmanager.model.AssignPackageToBagResponse;
import com.mycomp.fleetmanager.model.GetShipmentRequest;
import com.mycomp.fleetmanager.model.GetShipmentResponse;
import com.mycomp.fleetmanager.model.SaveBagRequest;
import com.mycomp.fleetmanager.model.SaveBagResponse;
import com.mycomp.fleetmanager.model.SavePackageRequest;
import com.mycomp.fleetmanager.model.SavePackageResponse;
import com.mycomp.fleetmanager.validator.ShipmentValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ShipmentService extends BaseService {

	@Autowired
	private ShipmentValidator shipmentValidator;

	@Autowired
	private ShipmentMapper shipmentMapper;
	
	@Autowired
	private ShipmentDao shipmentDao;
	

	public SavePackageResponse savePackage(SavePackageRequest savePackageRequest) throws FleetManagerException {
		
		Shipment packageToBeSaved = null;
		Shipment savedPackage = null;
		SavePackageResponse savePackageResponse = null;

		shipmentValidator.validateSavePackageRequest(savePackageRequest);
		packageToBeSaved = shipmentMapper.toShipment(savePackageRequest);
		savedPackage = shipmentDao.save(packageToBeSaved);
		savePackageResponse = shipmentMapper.toSavePackageResponse(savedPackage);
		
		log.info("Package saved. SavePackageResponse: " + savePackageResponse.toString() + " User:" + userService.getUser());
		

		return savePackageResponse;
		
	}

	public GetShipmentResponse getShipment(GetShipmentRequest getShipmentRequest) throws FleetManagerException {

		Shipment shipment = null;
		GetShipmentResponse getShipmentResponse = null;

		shipmentValidator.validateGetShipmentRequest(getShipmentRequest);
		shipment = shipmentDao.findByBarcode(getShipmentRequest.getBarcode());
		getShipmentResponse = shipmentMapper.toGetShipmentResponse(shipment);
		
		log.info("Shipment retrieved. GetShipmentResponse: " + getShipmentResponse.toString() + " User:" + userService.getUser());
		
		return getShipmentResponse;
		
	}

	public SaveBagResponse saveBag(SaveBagRequest saveBagRequest) throws FleetManagerException {
		
		Shipment bagToBeSaved = null;
		Shipment savedBag = null;
		SaveBagResponse saveBagResponse = null;

		shipmentValidator.validateSaveBagRequest(saveBagRequest);
		bagToBeSaved = shipmentMapper.toShipment(saveBagRequest);
		savedBag = shipmentDao.save(bagToBeSaved);
		saveBagResponse = shipmentMapper.toSaveBagResponse(savedBag);
		
		log.info("Bag saved. SaveBagResponse: " + saveBagResponse.toString() + " User:" + userService.getUser());
		
		return saveBagResponse;
		
	}
	
	public AssignPackageToBagResponse assignPackageToBag(AssignPackageToBagRequest assignPackageToBagRequest) throws FleetManagerException {
	
		Shipment shipment = null;
		Shipment savedShipment = null;
		AssignPackageToBagResponse assignPackageToBagResponse = null;

		shipmentValidator.validateAssignPackageToBagRequest(assignPackageToBagRequest);
		shipment = shipmentMapper.toShipment(assignPackageToBagRequest);
		savedShipment = shipmentDao.save(shipment);
		assignPackageToBagResponse = shipmentMapper.toAssignPackageToBagResponse(savedShipment);
		
		
		log.info("Package assined to bag. AssignPackageToBagResponse: " + assignPackageToBagResponse.toString() + " User:" + userService.getUser());
		
		return assignPackageToBagResponse;
		
	}
	
	public Shipment changeShipmentStatus(Shipment shipment, int shipmentStatus) {

		shipment.setShipmentStatus(shipmentStatus);
		
		return shipmentDao.save(shipment);

	}

}
