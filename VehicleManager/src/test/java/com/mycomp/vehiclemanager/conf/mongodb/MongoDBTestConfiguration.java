package com.mycomp.vehiclemanager.conf.mongodb;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

@TestConfiguration
public class MongoDBTestConfiguration {

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

}
