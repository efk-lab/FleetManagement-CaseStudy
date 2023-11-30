package com.mycomp.fleetmanager.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mycomp.fleetmanager.conf.security.UserService;
import com.mycomp.fleetmanager.document.DeliveryPoint;
import com.mycomp.fleetmanager.mapper.DeliveryPointMapper;
import com.mycomp.fleetmanager.model.SaveDeliveryPointRequest;
import com.mycomp.fleetmanager.model.SaveDeliveryPointResponse;
import com.mycomp.fleetmanager.repository.DeliveryPointRepository;
import com.mycomp.fleetmanager.validator.DeliveryPointValidator;


@ExtendWith(MockitoExtension.class)
public class DeliveryPointServiceTest {

	@Mock
	private DeliveryPointRepository deliveryPointRepository;

	@Mock
	private UserService userService;

	@Mock
	private DeliveryPointMapper deliveryPointMapper;

	@Mock
	private DeliveryPointValidator deliveryPointValidator;

	@InjectMocks
	private DeliveryPointService deliveryPointService;

	@Test
	public void saveDeliveryPoint() {

		SaveDeliveryPointRequest saveDeliveryPointRequest = prepareSaveDeliveryPointRequest();
		SaveDeliveryPointResponse saveDeliveryPointResponseExpected = prepareSaveDeliveryPointResponse();
		DeliveryPoint deliveryPoint = prepareDeliveryPoint();

		doNothing().when(deliveryPointValidator).validateSaveDeliveryPointRequest(saveDeliveryPointRequest);
		given(deliveryPointMapper.toDeliveryPoint(saveDeliveryPointRequest)).willReturn(deliveryPoint);
		given(deliveryPointRepository.save(deliveryPoint)).willReturn(deliveryPoint);
		given(deliveryPointMapper.toSaveDeliveryPointResponse(deliveryPoint)).willReturn(saveDeliveryPointResponseExpected);

		SaveDeliveryPointResponse saveDeliveryPointResponseActual = deliveryPointService.saveDeliveryPoint(saveDeliveryPointRequest);

		assertThat(saveDeliveryPointResponseActual.getDeliveryPoint()).isEqualTo(saveDeliveryPointResponseExpected.getDeliveryPoint());

	}

	private SaveDeliveryPointRequest prepareSaveDeliveryPointRequest() {

		SaveDeliveryPointRequest saveDeliveryPointRequest = new SaveDeliveryPointRequest();

		saveDeliveryPointRequest.setDeliveryPointName("Branch");
		saveDeliveryPointRequest.setDeliveryPointValue(1);

		return saveDeliveryPointRequest;
	}

	private SaveDeliveryPointResponse prepareSaveDeliveryPointResponse() {

		SaveDeliveryPointResponse saveDeliveryPointResponse = new SaveDeliveryPointResponse();

		saveDeliveryPointResponse.setDeliveryPointId(new ObjectId("62d322ddf9f5e01864bed242"));
		saveDeliveryPointResponse.setDeliveryPoint("Branch");

		return saveDeliveryPointResponse;

	}

	private DeliveryPoint prepareDeliveryPoint() {

		DeliveryPoint deliveryPoint = new DeliveryPoint();

		deliveryPoint.setDeliveryPointId(new ObjectId("62d322ddf9f5e01864bed242"));
		deliveryPoint.setDeliveryPointName("Branch");
		
		return deliveryPoint;
	}

}
