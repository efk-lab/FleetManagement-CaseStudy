package com.mycomp.fleetmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mycomp.fleetmanager.document.DeliveryPoint;
import com.mycomp.fleetmanager.error.FleetManagerException;
import com.mycomp.fleetmanager.mapper.DeliveryPointMapper;
import com.mycomp.fleetmanager.model.SaveDeliveryPointRequest;
import com.mycomp.fleetmanager.model.SaveDeliveryPointResponse;
import com.mycomp.fleetmanager.repository.DeliveryPointRepository;
import com.mycomp.fleetmanager.validator.DeliveryPointValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class DeliveryPointService extends BaseService {

	@Autowired
	private DeliveryPointValidator deliveryPointValidator;

	@Autowired
	private DeliveryPointMapper deliveryPointMapper;

	@Autowired
	private DeliveryPointRepository deliveryPointRepository;


	@Transactional
	public SaveDeliveryPointResponse saveDeliveryPoint(SaveDeliveryPointRequest saveDeliveryPointRequest) throws FleetManagerException {

		DeliveryPoint deliveryPoint = null;
		SaveDeliveryPointResponse saveDeliveryPointResponse = null;
		DeliveryPoint savedDeliveryPoint = null;

		deliveryPointValidator.validateSaveDeliveryPointRequest(saveDeliveryPointRequest);
		deliveryPoint = deliveryPointMapper.toDeliveryPoint(saveDeliveryPointRequest);
		savedDeliveryPoint = deliveryPointRepository.save(deliveryPoint);
		saveDeliveryPointResponse = deliveryPointMapper.toSaveDeliveryPointResponse(savedDeliveryPoint);
		
		log.info("DeliveryPoint saved. SaveDeliveryPointResponse: " + saveDeliveryPointResponse.toString() + " User:" + userService.getUser());

		return saveDeliveryPointResponse;
		
	}

}
