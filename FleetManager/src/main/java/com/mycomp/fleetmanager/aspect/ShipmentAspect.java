package com.mycomp.fleetmanager.aspect;

import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mycomp.fleetmanager.constant.Role;
import com.mycomp.fleetmanager.constant.ShipmentEventType;
import com.mycomp.fleetmanager.constant.ShipmentStatus;
import com.mycomp.fleetmanager.constant.ShipmentType;
import com.mycomp.fleetmanager.dao.ShipmentDao;
import com.mycomp.fleetmanager.document.Shipment;
import com.mycomp.fleetmanager.document.ShipmentState;
import com.mycomp.fleetmanager.dto.DeliveryStatusDto;
import com.mycomp.fleetmanager.event.ShipmentEvent;
import com.mycomp.fleetmanager.model.AssignPackageToBagResponse;
import com.mycomp.fleetmanager.model.SaveBagResponse;
import com.mycomp.fleetmanager.model.SaveDistributionResponse;
import com.mycomp.fleetmanager.model.SavePackageResponse;
import com.mycomp.fleetmanager.repository.ShipmentStateRepository;
import com.mycomp.fleetmanager.service.ShipmentEventProducerService;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class ShipmentAspect {
	
	@Autowired
	private ShipmentStateRepository shipmentStateRepository;
	
	@Autowired
	private ShipmentDao shipmentDao;
	
	@Autowired
	private ShipmentEventProducerService shipmentEventProducerService;
	

	@AfterReturning(pointcut = "execution(* com.mycomp.fleetmanager.service.ShipmentService.savePackage(..))", returning = "returnObject")
	public void logSavePackage(JoinPoint joinPoint, Object returnObject) {

		SavePackageResponse savePackageResponse = (SavePackageResponse) returnObject;
		ShipmentState event = new ShipmentState();

		event.setShipmentId(savePackageResponse.getShipmentId().toString());
		event.setBarcode(savePackageResponse.getBarcode());
		event.setShipmentType(savePackageResponse.getShipmentType());
		event.setShipmentEventType(ShipmentEventType.PACKAGE_CREATED);
		event.setShipmentStatus(savePackageResponse.getShipmentStatus());
		event.setEventDate(new Date());
		event.setCreatedBy(Role.FLEET_MNGR_SYSTEM.name());
		event.setCreationDate(new Date());

		produceAndSaveEvent(event);

		log.info("PACKAGE_SAVED event occured. Event: " + event.toString());

	}
	
	@AfterReturning(pointcut = "execution(* com.mycomp.fleetmanager.service.ShipmentService.saveBag(..))", returning = "returnObject")
	public void logSaveBag(JoinPoint joinPoint, Object returnObject) {

		SaveBagResponse saveBagResponse = (SaveBagResponse) returnObject;
		ShipmentState event = new ShipmentState();

		event.setShipmentId(saveBagResponse.getShipmentId().toString());
		event.setBarcode(saveBagResponse.getBarcode());
		event.setShipmentType(saveBagResponse.getShipmentType());
		event.setShipmentEventType(ShipmentEventType.BAG_CREATED);
		event.setShipmentStatus(saveBagResponse.getShipmentStatus());
		event.setEventDate(new Date());
		event.setCreatedBy(Role.FLEET_MNGR_SYSTEM.name());
		event.setCreationDate(new Date());

		produceAndSaveEvent(event);

		log.info("BAG_SAVED event occured. Event: " + event.toString());

	}
	
	
	@AfterReturning(pointcut = "execution(* com.mycomp.fleetmanager.service.ShipmentService.assignPackageToBag(..))", returning = "returnObject")
	public void logAssignPackageToBag(JoinPoint joinPoint, Object returnObject) {

		AssignPackageToBagResponse assignPackageToBagResponse = (AssignPackageToBagResponse) returnObject;
		ShipmentState shipmentState = new ShipmentState();

		Shipment packageShipment = shipmentDao.findByBarcode(assignPackageToBagResponse.getPackageBarcode());
		shipmentState.setShipmentId(packageShipment.getShipmentId().toString());
		shipmentState.setBarcode(packageShipment.getBarcode());
		shipmentState.setShipmentType(ShipmentType.nameOf(packageShipment.getShipmentType()));
		shipmentState.setShipmentEventType(ShipmentEventType.PACKAGE_ASSIGNED_BAG);
		shipmentState.setShipmentStatus(ShipmentStatus.nameOf(packageShipment.getShipmentStatus()));
		shipmentState.setEventDate(new Date());
		shipmentState.setCreatedBy(Role.FLEET_MNGR_SYSTEM.name());
		shipmentState.setCreationDate(new Date());

		produceAndSaveEvent(shipmentState);

		log.info("PACKAGE_ASSIGNED_BAG event occured. ShipmentState: " + shipmentState.toString());

	}
	
	@AfterReturning(pointcut = "execution(* com.mycomp.fleetmanager.service.DistributionService.saveDistribution(..))", returning = "returnObject")
	public void logSaveDistribution(JoinPoint joinPoint, Object returnObject) {

		SaveDistributionResponse saveDistributionResponse = (SaveDistributionResponse) returnObject;
		
		saveDistributionResponse.getRoute().stream()
				.forEach(deliveryLocationStatus -> deliveryLocationStatus.getDeliveries().stream().forEach(deliveryStatus -> logLoadShipment(deliveryStatus)));

	}
	
	private void logLoadShipment(DeliveryStatusDto deliveryStatus) {
		
		ShipmentState shipmentState = new ShipmentState();
		Shipment shipment = shipmentDao.findByBarcode(deliveryStatus.getBarcode());

		shipmentState.setShipmentId(shipment.getShipmentId().toString());
		shipmentState.setBarcode(shipment.getBarcode());
		shipmentState.setShipmentType(ShipmentType.nameOf(shipment.getShipmentType()));
		shipmentState.setShipmentEventType(shipment.getShipmentType() == ShipmentType.PACKAGE.getValue() ? ShipmentEventType.PACKAGE_LOADED : ShipmentEventType.BAG_LOADED);
		shipmentState.setShipmentStatus(ShipmentStatus.nameOf(shipment.getShipmentStatus()));
		shipmentState.setEventDate(new Date());
		shipmentState.setCreatedBy(Role.FLEET_MNGR_SYSTEM.name());
		shipmentState.setCreationDate(new Date());

		produceAndSaveEvent(shipmentState);
		
		log.info("SHIPMENT_LOADED event occured. ShipmentState: " + shipmentState.toString());
	}
	
	@AfterReturning(pointcut = "execution( * com.mycomp.fleetmanager.service.ShipmentService.changeShipmentStatus(..))", returning = "returnObject")
	public void logUnloadShipment(JoinPoint joinPoint, Object returnObject) {

		Shipment shipment = (Shipment) returnObject;
		ShipmentState shipmentState = new ShipmentState();
		
		shipmentState.setShipmentId(shipment.getShipmentId().toString());
		shipmentState.setBarcode(shipment.getBarcode());
		shipmentState.setShipmentType(ShipmentType.nameOf(shipment.getShipmentType()));
		if(shipment.getShipmentStatus() == ShipmentStatus.UNLOADED.getValue()) {
			shipmentState.setShipmentEventType(shipment.getShipmentType() == ShipmentType.PACKAGE.getValue() ? ShipmentEventType.PACKAGE_UNLOADED : ShipmentEventType.BAG_UNLOADED);
		}
		shipmentState.setShipmentStatus(ShipmentStatus.nameOf(shipment.getShipmentStatus()));
		shipmentState.setEventDate(new Date());
		shipmentState.setCreatedBy(Role.FLEET_MNGR_SYSTEM.name());
		shipmentState.setCreationDate(new Date());

		produceAndSaveEvent(shipmentState);
		
		log.info("SHIPMENT_UNLOADED event occured. ShipmentState: " + shipmentState.toString());
	}
	
	private void produceAndSaveEvent(ShipmentState shipmentState) {
		
		shipmentStateRepository.save(shipmentState);
		
		ShipmentEvent event = new ShipmentEvent();
		event.setShipmentId(shipmentState.getShipmentId());
		event.setBarcode(shipmentState.getBarcode());
		event.setShipmentType(shipmentState.getShipmentType().name());
		event.setShipmentEventType(shipmentState.getShipmentEventType().name());
		event.setShipmentStatus(shipmentState.getShipmentStatus().name());
		event.setEventDate(shipmentState.getEventDate());
		
		shipmentEventProducerService.produceShipmentEvent(event);
	}
}
