package com.mycomp.fleetmanager.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycomp.fleetmanager.dao.ShipmentDao;
import com.mycomp.fleetmanager.document.Shipment;
import com.mycomp.fleetmanager.error.FleetManagerException;
import com.mycomp.fleetmanager.model.AssignPackageToBagRequest;
import com.mycomp.fleetmanager.model.GetShipmentRequest;
import com.mycomp.fleetmanager.model.SaveBagRequest;
import com.mycomp.fleetmanager.model.SavePackageRequest;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ShipmentValidator extends BaseValidator {
	
	@Autowired
	private ShipmentDao shipmentDao;
	

	public void validateSavePackageRequest(SavePackageRequest savePackageRequest) throws FleetManagerException {

		validateRequest(savePackageRequest);
		validateBarcode(savePackageRequest.getBarcode());
		validateVolumetricWeight(savePackageRequest.getVolumetricWeight());

		log.info("SavePackageRequest validated. SavePackageRequest: " + savePackageRequest.toString() + " User:" + userService.getUser());

	}

	public void validateSaveBagRequest(SaveBagRequest saveBagRequest) throws FleetManagerException {

		validateRequest(saveBagRequest);
		validateBarcode(saveBagRequest.getBarcode());

		log.info("SaveBagRequest validated. SaveBagRequest : " + saveBagRequest.toString() + " User:" + userService.getUser());

	}

	private void validateBarcode(String barcode) {

		Shipment shipment = shipmentDao.findByBarcode(barcode);

		if (shipment != null) {
			throw new FleetManagerException("Shipment already exists.");
		}

	}

	private void validateVolumetricWeight(double volumetricWeight) {

		if (volumetricWeight <= 0) {
			throw new FleetManagerException("VolumetricWeight cannot be negative or zero.");
		}

	}

	public void validateGetShipmentRequest(GetShipmentRequest getShipmentRequest) {

		validateRequest(getShipmentRequest);
		validateShipmentBarcode(getShipmentRequest.getBarcode());

		log.info("GetShipmentRequest validated. GetShipmentRequest : " + getShipmentRequest.toString() + " User:" + userService.getUser());

	}

	private void validateShipmentBarcode(String barcode) {

		if (barcode == null || barcode.isBlank()) {
			throw new FleetManagerException("Barcode cannot be empty.");
		}

		Shipment shipment = shipmentDao.findByBarcode(barcode);
		if (shipment == null) {
			throw new FleetManagerException("Shipment does not exist.");
		}

	}

	public void validateAssignPackageToBagRequest(AssignPackageToBagRequest assignPackageToBagRequest) {

		validateRequest(assignPackageToBagRequest);
		validateBagBarcode(assignPackageToBagRequest.getBagBarcode());
		validatePackageBarcode(assignPackageToBagRequest.getPackageBarcode());
		validateDeliveryPointsForAssignment(assignPackageToBagRequest.getBagBarcode(), assignPackageToBagRequest.getPackageBarcode());

		log.info("AssignPackageToBagRequest validated. AssignPackageToBagRequest : " + assignPackageToBagRequest.toString() + " User:" + userService.getUser());

	}

	private void validateBagBarcode(String bagBarcode) {

		Shipment bagShipment = shipmentDao.findByBarcode(bagBarcode);

		if (bagShipment == null) {
			throw new FleetManagerException("Bag does not exist.");
		}
	}

	private void validatePackageBarcode(String packageBarcode) {

		Shipment packageShipment = shipmentDao.findByBarcode(packageBarcode);

		if (packageShipment == null) {
			throw new FleetManagerException("Package does not exist.");
		}

	}

	private void validateDeliveryPointsForAssignment(String bagBarcode, String packageBarcode) {

		Shipment bagShipment = shipmentDao.findByBarcode(bagBarcode);
		Shipment packageShipment = shipmentDao.findByBarcode(packageBarcode);

		if (bagShipment.getDeliveryPoint().getDeliveryPointId().compareTo(packageShipment.getDeliveryPoint().getDeliveryPointId()) != 0) {
			throw new FleetManagerException("Package and bag cannot have different delivery point.");
		}

	}

}
