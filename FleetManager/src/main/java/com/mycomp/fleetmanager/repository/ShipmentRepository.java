package com.mycomp.fleetmanager.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.mycomp.fleetmanager.document.Shipment;

@Repository
public interface ShipmentRepository extends MongoRepository<Shipment, ObjectId> {
	
	Shipment findByBarcode(String barcode);
	
	Shipment findByBarcodeAndShipmentStatus(String barcode, int shipmentStatus);
	
	List<Shipment> findByBagBarcode(String bagBarcode);
	
	List<Shipment> findByBagBarcodeAndShipmentStatus(String bagBarcode, int shipmentStatus);

}
