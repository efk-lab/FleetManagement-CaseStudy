package com.mycomp.fleetmanager.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.mycomp.fleetmanager.document.Shipment;
import com.mycomp.fleetmanager.repository.ShipmentRepository;

@Component
public class ShipmentDao {

	@Autowired
	private ShipmentRepository shipmentRepository;
	
	
	@CacheEvict(value = "shipments", allEntries = true) 
	public Shipment save(Shipment shipment) {
		return shipmentRepository.save(shipment);
	}

	@CacheEvict(value = "shipments", allEntries = true) 
	public List<Shipment> saveAll(List<Shipment> shipments) {
		return shipmentRepository.saveAll(shipments);
	}
	
	@Cacheable(cacheNames = "shipments", unless="#result == null")
	public Shipment findByBarcode(String barcode) {
		return shipmentRepository.findByBarcode(barcode);
	}
	
	@Cacheable(cacheNames = "shipments", unless="#result == null")
	public List<Shipment> findByBagBarcode(String bagBarcode){
		return shipmentRepository.findByBagBarcode(bagBarcode);
	}
	
	@Cacheable(cacheNames = "shipments", unless="#result == null")
	public List<Shipment> findByBagBarcodeAndShipmentStatus(String bagBarcode, int shipmentStatus) {
		return shipmentRepository.findByBagBarcodeAndShipmentStatus(bagBarcode, shipmentStatus);
	}
	
}
