package com.mycomp.fleetmanager.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.mycomp.fleetmanager.conf.security.UserService;
import com.mycomp.fleetmanager.dao.ShipmentDao;
import com.mycomp.fleetmanager.document.DeliveryLocation;
import com.mycomp.fleetmanager.document.DeliveryPoint;
import com.mycomp.fleetmanager.document.Distribution;
import com.mycomp.fleetmanager.document.DistributionLog;
import com.mycomp.fleetmanager.document.Shipment;
import com.mycomp.fleetmanager.document.Vehicle;
import com.mycomp.fleetmanager.dto.DeliveryDto;
import com.mycomp.fleetmanager.dto.DeliveryLocationDto;
import com.mycomp.fleetmanager.mapper.DistributionLogMapper;
import com.mycomp.fleetmanager.mapper.DistributionMapper;
import com.mycomp.fleetmanager.model.GetDistributionRequest;
import com.mycomp.fleetmanager.model.GetDistributionResponse;
import com.mycomp.fleetmanager.model.SaveDistributionRequest;
import com.mycomp.fleetmanager.model.SaveDistributionResponse;
import com.mycomp.fleetmanager.repository.DistributionLogRepository;
import com.mycomp.fleetmanager.repository.DistributionRepository;
import com.mycomp.fleetmanager.validator.DistributionValidator;



@ExtendWith(MockitoExtension.class)
public class DistributionServiceTest {

	@Mock
	private DistributionRepository distributionRepository;
	
	@Mock
	private DistributionLogRepository distributionLogRepository;

	@Mock
	private UserService userService;

	@Mock
	private DistributionMapper distributionMapper;
	
	@Mock
	private DistributionLogMapper distributionLogMapper;

	@Mock
	private DistributionValidator distributionValidator;
	
	@Mock
	private ShipmentDao shipmentDao;

	@InjectMocks
	private DistributionService distributionService;

	@Test
	public void saveDistribution() throws Exception {

		SaveDistributionRequest saveDistributionRequest = prepareSaveDistributionRequest(); 
		SaveDistributionResponse saveDistributionResponseExpected = prepareSaveDistributionResponse();
		Distribution distribution = prepareDistribution();
		DistributionLog distributionLog = prepareDistributionLog();
		List<Shipment> shipments = prepareShipments();

		doNothing().when(distributionValidator).validateSaveDistributionRequest(saveDistributionRequest);
		given(distributionMapper.toDistribution(saveDistributionRequest)).willReturn(distribution);
		given(distributionRepository.save(distribution)).willReturn(distribution);
		given(shipmentDao.saveAll(shipments)).willReturn(shipments);
		given(distributionLogMapper.toDistributionLog(distribution)).willReturn(distributionLog);
		given(distributionLogRepository.save(distributionLog)).willReturn(distributionLog);
		given(distributionMapper.toSaveDistributionResponse(distribution)).willReturn(saveDistributionResponseExpected);

		SaveDistributionResponse saveDistributionResponseActual = distributionService.saveDistribution(saveDistributionRequest);

		assertThat(saveDistributionResponseActual.getDistributionId()).isEqualTo(saveDistributionResponseExpected.getDistributionId());

	}

	@Test
	public void getDistribution() throws Exception {

		GetDistributionRequest getDistributionRequest = prepareGetDistributionRequest();
		GetDistributionResponse getDistributionResponseExpected = prepareGetDistributionResponse();
		Distribution distribution = prepareDistribution();

		doNothing().when(distributionValidator).validateGetDistributionRequest(getDistributionRequest);
		given(distributionRepository.findById(new ObjectId(getDistributionRequest.getDistributionId()))).willReturn(Optional.of(distribution));
		given(distributionMapper.toGetDistributionResponse(Optional.of(distribution))).willReturn(getDistributionResponseExpected);

		GetDistributionResponse getDistributionResponseActual = distributionService.getDistribution(getDistributionRequest);

		assertThat(getDistributionResponseActual.getPlate()).isEqualTo(getDistributionResponseExpected.getPlate());

	}
	
	private SaveDistributionRequest prepareSaveDistributionRequest() {
		
		SaveDistributionRequest saveDistributionRequest = new SaveDistributionRequest();
		
		saveDistributionRequest.setPlate("34 TL 34");
		List<DeliveryLocationDto>  deliveryLocationDtoList = new ArrayList<DeliveryLocationDto>();
		DeliveryLocationDto deliveryLocationDto = new DeliveryLocationDto();
		deliveryLocationDto.setDeliveryPoint(1);
		List<DeliveryDto> deliveryList = new ArrayList<DeliveryDto>();
		DeliveryDto deliveryDto = new DeliveryDto();
		deliveryDto.setBarcode("P8988000125");
		deliveryList.add(deliveryDto);
		deliveryLocationDto.setDeliveries(deliveryList);
		deliveryLocationDtoList.add(deliveryLocationDto);
		saveDistributionRequest.setRoute(deliveryLocationDtoList);
		
		return saveDistributionRequest;
	}
	
	private SaveDistributionResponse prepareSaveDistributionResponse() {
		
		SaveDistributionResponse saveDistributionResponse = new SaveDistributionResponse();
		
		saveDistributionResponse.setPlate("34 TL 34");
		
		return saveDistributionResponse;
		
	}
	
	private Distribution prepareDistribution() {
		

			Distribution distribution = new Distribution();
			
			distribution.setDistributionId(new ObjectId("62d322ddf9f5e01864bed242"));
			Vehicle vehicle = new Vehicle();
			vehicle.setPlate("34 TL 34");
			distribution.setPlate(vehicle);
			List<DeliveryLocation>  deliveryLocationList = new ArrayList<DeliveryLocation>();
			DeliveryLocation deliveryLocation = new DeliveryLocation();
			DeliveryPoint deliveryPoint = new DeliveryPoint();
			deliveryPoint.setDeliveryPointName("Transfer Center");
			deliveryLocation.setDeliveryPoint(deliveryPoint);
			List<Shipment> shipmentList = new ArrayList<Shipment>();
			Shipment shipment = new Shipment();
			shipment.setBarcode("P8988000125");
			shipment.setShipmentStatus(0);
			shipmentList.add(shipment);
			deliveryLocation.setShipments(shipmentList);
			deliveryLocationList.add(deliveryLocation);
			distribution.setRoute(deliveryLocationList);
			distribution.setCreatedBy("eda@gmail.com");
			distribution.setCreationDate(ZonedDateTime.of(2022, 7, 12, 10, 30, 30, 20, ZoneId.systemDefault()));
			distribution.setModifiedBy("eda@gmail.com");
			distribution.setModificationDate(ZonedDateTime.of(2022, 7, 12, 10, 30, 30, 20, ZoneId.systemDefault()));
			
			return distribution;
			

		
	}
	
	private DistributionLog prepareDistributionLog() {
	
		DistributionLog distributionLog = new DistributionLog();
		
		distributionLog.setDistributionId(new ObjectId("62d322ddf9f5e01864bed242"));
		distributionLog.setPlate("34 TL 34");
		
		return distributionLog;
	}
	
	private GetDistributionRequest prepareGetDistributionRequest() {
		
		GetDistributionRequest getDistributionRequest = new GetDistributionRequest();
		
		getDistributionRequest.setDistributionId("62d322ddf9f5e01864bed242");
		
		return getDistributionRequest;
	}
	
	private GetDistributionResponse prepareGetDistributionResponse() {
		
		GetDistributionResponse getDistributionResponse = new GetDistributionResponse();
		
		getDistributionResponse.setDistributionId(new ObjectId("62d322ddf9f5e01864bed242"));
		getDistributionResponse.setPlate("34 TL 34");
		
		return getDistributionResponse;
	}
	
	private List<Shipment> prepareShipments(){
		Shipment shipment = new Shipment();
		shipment.setBarcode("P8988000125");
		
		List<Shipment> shipments = new ArrayList<Shipment>();
		shipments.add(shipment);
		
		return shipments;
	}
	
}
