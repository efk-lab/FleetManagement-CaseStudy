package com.mycomp.fleetmanager.constant;

public enum ShipmentStatus {

	CREATED(1), LOADED_INTO_BAG(2), LOADED(3), UNLOADED(4);

	private int value;
	

	ShipmentStatus(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	

	static public ShipmentStatus nameOf(int value) {

		ShipmentStatus[] shipmentStatusArray = ShipmentStatus.values();
		for (ShipmentStatus shipmentStatusItem : shipmentStatusArray) {
			if (shipmentStatusItem.value == value) {
				return shipmentStatusItem;
			}
		}
		
		return null;
		
	}

}
