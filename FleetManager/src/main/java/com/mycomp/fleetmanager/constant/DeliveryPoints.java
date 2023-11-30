package com.mycomp.fleetmanager.constant;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum DeliveryPoints {

	BRANCH(1), DISTRIBUTION_CENTER(2), TRANSFER_CENTER(3);

	private int value;
	

	DeliveryPoints(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
	static public boolean isMember(DeliveryPoints deliveryPoint) {
		
		log.info("İsMember:" + deliveryPoint.name());

		boolean isMember = false;

		DeliveryPoints[] deliveryPoints = DeliveryPoints.values();
		for (DeliveryPoints deliveryPointItem : deliveryPoints) {
			if (deliveryPointItem.name().equals(deliveryPoint.name().toUpperCase())) {
				isMember = true;
			}
		}

		return isMember;
	}

	static public DeliveryPoints nameOf(int value) {

		DeliveryPoints[] deliveryPoints = DeliveryPoints.values();
		for (DeliveryPoints deliveryPoint : deliveryPoints) {
			if (deliveryPoint.value == value) {
				return deliveryPoint;
			}
		}

		return null;
	}

}