package com.mycomp.vehiclemanager.circuitbreaker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.stereotype.Component;

@Component
public class VehicleManagerCircuitBreakerFactory {
	
	@Value(value = "${resilience4j.circuitbreaker.id}")
	private String circuitBreakerId;

	@Autowired
	private CircuitBreakerFactory<?, ?> circuitBreakerFactory;

	
	public CircuitBreaker getFleetmanagerCircuitBreaker() {
		return circuitBreakerFactory.create(circuitBreakerId);
	}

}
