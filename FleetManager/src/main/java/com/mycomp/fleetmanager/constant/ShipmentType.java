package com.mycomp.fleetmanager.constant;

public enum ShipmentType {

	PACKAGE(1), BAG(2);

	private int value;
	

	ShipmentType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
	static public boolean isMember(ShipmentType shipmentType) {

		boolean isMember = false;
		
		ShipmentType[] shipmentTypes = ShipmentType.values();
		for (ShipmentType shipmentTypeItem : shipmentTypes) {
			if (shipmentTypeItem.name().equals(shipmentType.name().toUpperCase())) {
				isMember = true;
			}
		}

		return isMember;
	}
	
	static public ShipmentType nameOf(int value) {

		ShipmentType[] shipmentTypes = ShipmentType.values();
		for (ShipmentType shipmentTypeItem : shipmentTypes) {
			if (shipmentTypeItem.value == value) {
				return shipmentTypeItem;
			}
		}
		
		return null;
		
	}

}
