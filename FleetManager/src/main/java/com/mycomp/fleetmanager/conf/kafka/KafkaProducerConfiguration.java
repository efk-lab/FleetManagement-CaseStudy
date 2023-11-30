package com.mycomp.fleetmanager.conf.kafka;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

@Configuration
public class KafkaProducerConfiguration {

	@Value(value = "${kafka.producer.bootstrapAddress}")
	private String bootstrapAddress;

	@Value(value = "${kafka.producer.topic.name}")
	private String topicName;

	@Bean
	public KafkaAdmin kafkaAdmin() {

		Map<String, Object> configs = new HashMap<>();

		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);

		return new KafkaAdmin(configs);

	}

	@Bean
	public ProducerFactory<?, ?> producerFactory() {
		
		Map<String, Object> configProps = new HashMap<>();

		configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		configProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);

		return new DefaultKafkaProducerFactory<>(configProps);
		
	}

	@Bean
	public KafkaTemplate<?, ?> kafkaTemplate() {

		return new KafkaTemplate<>(producerFactory());

	}

	@Bean
	public NewTopic topic() {

		return TopicBuilder.name(topicName).partitions(1).replicas(1).build();

	}

}
