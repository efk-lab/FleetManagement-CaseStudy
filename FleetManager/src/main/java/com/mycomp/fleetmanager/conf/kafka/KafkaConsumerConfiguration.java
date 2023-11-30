package com.mycomp.fleetmanager.conf.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.mycomp.fleetmanager.event.VehicleLocationEvent;

@EnableKafka
@Configuration
public class KafkaConsumerConfiguration {

	@Value(value = "${kafka.consumer.bootstrapAddress}")
	private String bootstrapAddress;

	@Value(value = "${kafka.consumer.groupId}")
	private String groupId;

	@Bean
	public ConsumerFactory<String, VehicleLocationEvent> vehicleLocationgConsumerFactory() {
		
		Map<String, Object> props = new HashMap<>();

		JsonDeserializer<VehicleLocationEvent> deserializer = new JsonDeserializer<>(VehicleLocationEvent.class);
		deserializer.setRemoveTypeHeaders(false);
		deserializer.addTrustedPackages("*");
		deserializer.setUseTypeMapperForKey(true);

		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);

		return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, VehicleLocationEvent> vehicleLocationKafkaListenerContainerFactory() {

		ConcurrentKafkaListenerContainerFactory<String, VehicleLocationEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(vehicleLocationgConsumerFactory());

		return factory;
	}
}
