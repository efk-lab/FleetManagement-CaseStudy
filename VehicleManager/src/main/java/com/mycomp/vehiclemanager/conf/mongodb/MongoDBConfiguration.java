package com.mycomp.vehiclemanager.conf.mongodb;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@EnableMongoRepositories(basePackages = { "com.mycomp.vehiclemanager.repository" })
@EnableMongoAuditing(auditorAwareRef = "auditorAware", dateTimeProviderRef = "dateTimeProvider")
public class MongoDBConfiguration {

	@Bean
	public AuditorAwareImpl auditorAware() {
		return new AuditorAwareImpl();
	}

	@Bean
	public MongoCustomConversions customConversions() {

		List<Converter<?, ?>> converters = new ArrayList<>();

		converters.add(new DateToZonedDateTimeConverter());
		converters.add(new ZonedDateTimeToDateConverter());

		return new MongoCustomConversions(converters);
	}

	class DateToZonedDateTimeConverter implements Converter<Date, ZonedDateTime> {

		@Override
		public ZonedDateTime convert(Date source) {

			return source == null ? null : ZonedDateTime.ofInstant(source.toInstant(), ZoneId.systemDefault());

		}
	}

	class ZonedDateTimeToDateConverter implements Converter<ZonedDateTime, Date> {

		@Override
		public Date convert(ZonedDateTime source) {

			return source == null ? null : Date.from(source.toInstant());

		}
	}

	class AuditorAwareImpl implements AuditorAware<String> {

		@Override
		public Optional<String> getCurrentAuditor() {

			String uname = null;

			if (SecurityContextHolder.getContext() != null && SecurityContextHolder.getContext().getAuthentication() != null) {
				uname = SecurityContextHolder.getContext().getAuthentication().getName();
			}

			return Optional.ofNullable(uname);

		}
	}

}
