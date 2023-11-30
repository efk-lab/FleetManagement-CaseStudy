package com.mycomp.vehiclemanager.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;

import com.mycomp.vehiclemanager.circuitbreaker.VehicleManagerCircuitBreakerFactory;
import com.mycomp.vehiclemanager.error.VehicleManagerException;
import com.mycomp.vehiclemanager.model.GetDistributionRequest;
import com.mycomp.vehiclemanager.model.GetDistributionResponse;
import com.mycomp.vehiclemanager.model.GetVehicleRequest;
import com.mycomp.vehiclemanager.model.GetVehicleResponse;
import com.mycomp.vehiclemanager.model.StartDistributionRequest;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DistributionValidator extends BaseValidator {
	
	@Value(value = "${fleetmanager.vehicleservice.getvehicle.url}")
	private String getVehicleUrl;
	
	@Value(value = "${fleetmanager.distributionservice.getdistribution.url}")
	private String getDistributionUrl;
	
	@Autowired
	private OAuth2RestOperations oAuth2RestTemplate;

	@Autowired
	private VehicleManagerCircuitBreakerFactory circuitBreaker;
	

	public void validateStartDistributionRequest(StartDistributionRequest startDistributionRequest) {

		validateRequest(startDistributionRequest);
		validatePlate(startDistributionRequest.getPlate());
		validateDistributionId(startDistributionRequest.getDistributionId());

		log.info("StartDistributionRequest validated. StartDistributionRequest: " + startDistributionRequest.toString() + " User:" + userService.getUser());
	}

	private void validatePlate(String plate) {

		GetVehicleRequest getVehicleRequest = new GetVehicleRequest();
		getVehicleRequest.setPlate(plate);

		ResponseEntity<GetVehicleResponse> getVehicleResponse = null;
		try {
			getVehicleResponse = circuitBreaker.getFleetmanagerCircuitBreaker().run(() -> oAuth2RestTemplate
					.postForEntity(getVehicleUrl, getVehicleRequest, GetVehicleResponse.class),
					throwable -> getPlateFallback(plate, (HttpServerErrorException) throwable));
		} catch (ClassCastException ex) {
			try {
				getVehicleResponse = oAuth2RestTemplate.postForEntity(getVehicleUrl, getVehicleRequest, GetVehicleResponse.class);
			} catch (Exception e) {
				log.info("HttpClientErrorException occured.");
				throw new VehicleManagerException(e.getMessage());
			}
		}

		if (getVehicleResponse.getBody().getVehicleId() == null) {
			throw new VehicleManagerException("Plate is not valid.");
		}

	}

	private ResponseEntity<GetVehicleResponse> getPlateFallback(String plate, HttpServerErrorException e) {

		log.info("GetPlateFallback executed for Plate: " + plate + " Received Message:" + e.getMessage());

		throw new VehicleManagerException("Plate cannot be validated right now. Please try again later.");

	}

	private void validateDistributionId(String distributionId) {

		GetDistributionRequest getDistributionRequest = new GetDistributionRequest();
		getDistributionRequest.setDistributionId(distributionId);

		ResponseEntity<GetDistributionResponse> getDistributionResponse = null;
		try {
			getDistributionResponse = circuitBreaker
					.getFleetmanagerCircuitBreaker().run(
							() -> oAuth2RestTemplate.postForEntity(getDistributionUrl, getDistributionRequest, GetDistributionResponse.class),
							throwable -> getDistributionFallback(distributionId, (HttpServerErrorException) throwable));
		} catch (ClassCastException ex) {
			try {
				getDistributionResponse = oAuth2RestTemplate.postForEntity(getDistributionUrl, getDistributionRequest, GetDistributionResponse.class);
			} catch (Exception e) {
				log.info("HttpClientErrorException occured.");
				throw new VehicleManagerException(e.getMessage());
			}
		}

		if (getDistributionResponse.getBody().getDistributionId() == null) {
			throw new VehicleManagerException("Distribution is not valid.");
		}

	}

	private ResponseEntity<GetDistributionResponse> getDistributionFallback(String distributionId, HttpServerErrorException e) {

		log.debug("GetDistributionFallback executed for DistributionId: " + distributionId + " Received Message:" + e.getMessage());

		throw new VehicleManagerException("Distribution cannot be validated right now. Please try again later.");

	}

}
