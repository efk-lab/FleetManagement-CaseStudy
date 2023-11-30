package com.mycomp.vehiclemanager.conf.circuitbreaker;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@Configuration
public class Resilience4jConfiguration {
	
	@Value(value = "${resilience4j.circuitbreaker.id}")
	private String circuitBreakerId;
	
	@Value(value = "${resilience4j.circuitbreaker.fleetmanager.timelimiter-config.timeout-duration}")
	private int timeoutDuration;
	
	@Value(value = "${resilience4j.circuitbreaker.distribution.circuitbreaker-config.failureraate-threshold}")
	private int failureRateThreshold;
	
	@Value(value = "${resilience4j.circuitbreaker.distribution.circuitbreaker-config.waitduration-inopenstate}")
	private int waitDurationInOpenState;
	
	@Value(value = "${resilience4j.circuitbreaker.distribution.circuitbreaker-config.sliding-windowsize}")
	private int slidingWindowSize;


    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> specificCustomConfiguration1() {

        TimeLimiterConfig timeLimiterConfig = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(timeoutDuration))
                .build();
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(failureRateThreshold)
                .waitDurationInOpenState(Duration.ofMillis(waitDurationInOpenState))
                .slidingWindowSize(slidingWindowSize)
                .ignoreExceptions(HttpClientErrorException.class)
                .recordExceptions(HttpServerErrorException.class)
                .build();

        return factory -> factory.configure(builder -> builder.circuitBreakerConfig(circuitBreakerConfig)
                .timeLimiterConfig(timeLimiterConfig).build(), circuitBreakerId);
    }

}
