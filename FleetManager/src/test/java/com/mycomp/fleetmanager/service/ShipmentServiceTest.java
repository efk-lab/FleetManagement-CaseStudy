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
import com.mycomp.fleetmanager.dao.ShipmentDao;
import com.mycomp.fleetmanager.document.DeliveryPoint;
import com.mycomp.fleetmanager.document.Shipment;
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


@ExtendWith(MockitoExtension.class)
public class ShipmentServiceTest {

	@Mock
	private ShipmentDao shipmentDao;

	@Mock
	private UserService userService;

	@Mock
	private ShipmentMapper shipmentMapper;

	@Mock
	private ShipmentValidator shipmentValidator;

	@InjectMocks
	private ShipmentService shipmentService;

	@Test
	public void savePackage() {
		
		SavePackageRequest savePackageRequest = prepareSavePackageRequest();
		SavePackageResponse savePackageResponseExpected = prepareSavePackageResponse();
		Shipment shipment = prepareShipment();

		doNothing().when(shipmentValidator).validateSavePackageRequest(savePackageRequest);
		given(shipmentMapper.toShipment(savePackageRequest)).willReturn(shipment);
		given(shipmentDao.save(shipment)).willReturn(shipment);
		given(shipmentMapper.toSavePackageResponse(shipment)).willReturn(savePackageResponseExpected);

		SavePackageResponse savePackageResponseActual = shipmentService.savePackage(savePackageRequest);

		assertThat(savePackageResponseActual.getBarcode()).isEqualTo(savePackageResponseExpected.getBarcode());

	}
	
	@Test
	public void saveBag() {
		
		SaveBagRequest saveBagRequest = prepareSaveBagRequest();
		SaveBagResponse saveBagResponseExpected = prepareSaveBagResponse();
		Shipment shipment = prepareShipment();

		doNothing().when(shipmentValidator).validateSaveBagRequest(saveBagRequest);
		given(shipmentMapper.toShipment(saveBagRequest)).willReturn(shipment);
		given(shipmentDao.save(shipment)).willReturn(shipment);
		given(shipmentMapper.toSaveBagResponse(shipment)).willReturn(saveBagResponseExpected);

		SaveBagResponse saveBagResponseActual = shipmentService.saveBag(saveBagRequest);

		assertThat(saveBagResponseActual.getBarcode()).isEqualTo(saveBagResponseExpected.getBarcode());

	}

	
	@Test
	public void getShipment() throws Exception {

		GetShipmentRequest getShipmentRequest = prepareGetShipmentRequest();
		GetShipmentResponse getShipmentResponseExpected = prepareGetShipmentResponse();
		Shipment shipment = prepareShipment();

		doNothing().when(shipmentValidator).validateGetShipmentRequest(getShipmentRequest);
		given(shipmentDao.findByBarcode(getShipmentRequest.getBarcode())).willReturn(shipment);
		given(shipmentMapper.toGetShipmentResponse(shipment)).willReturn(getShipmentResponseExpected);

		GetShipmentResponse getShipmentResponsetActual = shipmentService.getShipment(getShipmentRequest);

		assertThat(getShipmentResponsetActual.getBarcode()).isEqualTo(getShipmentResponseExpected.getBarcode());
		
	}
	
	//AssignPackageToBagResponse assignPackageToBag(AssignPackageToBagRequest assignPackageToBagRequest)
	
	@Test
	public void assignShipment() throws Exception {

		AssignPackageToBagRequest assignPackageToBagRequest = prepareAssignPackageToBagRequest();
		AssignPackageToBagResponse assignPackageToBagResponseExpected = prepareAssignPackageToBagResponse();
		Shipment shipment = prepareShipment();
		
		doNothing().when(shipmentValidator).validateAssignPackageToBagRequest(assignPackageToBagRequest);
		given(shipmentMapper.toShipment(assignPackageToBagRequest)).willReturn(shipment);
		given(shipmentDao.save(shipment)).willReturn(shipment);
		given(shipmentMapper.toAssignPackageToBagResponse(shipment)).willReturn(assignPackageToBagResponseExpected);

		AssignPackageToBagResponse assignPackageToBagResponseActual = shipmentService.assignPackageToBag(assignPackageToBagRequest);

		assertThat(assignPackageToBagResponseActual.getPackageBarcode()).isEqualTo(assignPackageToBagResponseExpected.getPackageBarcode());
		
	}
	
	private SavePackageRequest prepareSavePackageRequest() {
		
		SavePackageRequest savePackageRequest = new SavePackageRequest();
		
		savePackageRequest.setBarcode("P8988000125");
		savePackageRequest.setDeliveryPoint(1);
		savePackageRequest.setVolumetricWeight(10);
		
		return savePackageRequest;
		
	}
	
	private SaveBagRequest prepareSaveBagRequest() {
		
		SaveBagRequest saveBagRequest = new SaveBagRequest();
		
		saveBagRequest.setBarcode("P8988000125");
		saveBagRequest.setDeliveryPoint(1);
		
		return saveBagRequest;
		
	}
	
	
	private SavePackageResponse prepareSavePackageResponse() {
		
		SavePackageResponse savePackageResponse = new SavePackageResponse();
		
		savePackageResponse.setShipmentId(new ObjectId("62d322ddf9f5e01864bed242"));
		savePackageResponse.setBarcode("P8988000125");
//		savePackageResponse.setDeliveryPoint(1);
//		saveShipmentResult.setShipmentType(ShipmentType.PACKAGE);
//		saveShipmentResult.setVolumetricWeight(10);
//		saveShipmentResult.setCreatedBy("eda@gmail.com");
//		saveShipmentResult.setCreationDate(ZonedDateTime.of(2022, 7, 12, 10, 30, 30, 20, ZoneId.systemDefault()));
//		saveShipmentResult.setModifiedBy("eda@gmail.com");
//		saveShipmentResult.setModificationDate(ZonedDateTime.of(2022, 7, 12, 10, 30, 30, 20, ZoneId.systemDefault()));
		
		return savePackageResponse;
		
	}
	
	private SaveBagResponse prepareSaveBagResponse() {
		
		SaveBagResponse saveBagResponse = new SaveBagResponse();
		
		saveBagResponse.setShipmentId(new ObjectId("62d322ddf9f5e01864bed242"));
		saveBagResponse.setBarcode("P8988000125");
//		savePackageResponse.setDeliveryPoint(1);
//		saveShipmentResult.setShipmentType(ShipmentType.PACKAGE);
//		saveShipmentResult.setVolumetricWeight(10);
//		saveShipmentResult.setCreatedBy("eda@gmail.com");
//		saveShipmentResult.setCreationDate(ZonedDateTime.of(2022, 7, 12, 10, 30, 30, 20, ZoneId.systemDefault()));
//		saveShipmentResult.setModifiedBy("eda@gmail.com");
//		saveShipmentResult.setModificationDate(ZonedDateTime.of(2022, 7, 12, 10, 30, 30, 20, ZoneId.systemDefault()));
		
		return saveBagResponse;
		
	}
	
	private Shipment prepareShipment() {
		
		Shipment shipment = new Shipment();
		
		shipment.setShipmentId(new ObjectId("62d322ddf9f5e01864bed242"));
		shipment.setBarcode("P8988000125");
		DeliveryPoint deliveryPoint = new DeliveryPoint();
		shipment.setDeliveryPoint(deliveryPoint);
		shipment.setShipmentType(1);
		shipment.setVolumetricWeight(10);
		
		return shipment;
		
	}
	
	private GetShipmentRequest prepareGetShipmentRequest() {
		GetShipmentRequest getShipmentRequest = new GetShipmentRequest();
		
		getShipmentRequest.setBarcode("P8988000125");
		
		return getShipmentRequest;
	}
	
	private GetShipmentResponse prepareGetShipmentResponse() {
		
		GetShipmentResponse getShipmentResponse = new GetShipmentResponse();
		
		getShipmentResponse.setShipmentId(new ObjectId("62d322ddf9f5e01864bed242"));
		getShipmentResponse.setBarcode("P8988000125");
		
		return getShipmentResponse;
	}
	
	private AssignPackageToBagRequest prepareAssignPackageToBagRequest() {
		
		AssignPackageToBagRequest assignPackageToBagRequest = new AssignPackageToBagRequest();
		
		assignPackageToBagRequest.setPackageBarcode("P8988000124");
		assignPackageToBagRequest.setBagBarcode("P8988000125");
		
		return assignPackageToBagRequest;
		
	}
	
	private AssignPackageToBagResponse prepareAssignPackageToBagResponse() {
		
		AssignPackageToBagResponse assignPackageToBagResponse = new AssignPackageToBagResponse();
		
		assignPackageToBagResponse.setPackageBarcode("P8988000124");
		assignPackageToBagResponse.setBagBarcode("P8988000125");
	
		return assignPackageToBagResponse;
		
	}
	
}
