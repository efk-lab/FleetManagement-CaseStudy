package com.mycomp.fleetmanager.conf.redis;

import java.time.Duration;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

@Configuration
@EnableCaching
public class RedisConfiguration {

	@Value("${spring.redis.host}")
	private String redisHost;

	@Value("${spring.redis.port}")
	private int redisPort;

	@Value("${spring.redis.database}")
	private int database;

	@Value("${spring.redis.password}")
	private String password;

	@Value("${spring.redis.jedis.pool.max-active}")
	private int poolMaxActive;

	@Value("${spring.redis.jedis.pool.max-idle}")
	private int poolMaxIdle;

	@Value("${spring.redis.jedis.pool.min-idle}")
	private int poolMinIdle;
	

	@Bean
	public JedisConnectionFactory jedisConnectionFactory() {
		RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration(redisHost, redisPort);
		configuration.setDatabase(database);
		configuration.setPassword(password);

		GenericObjectPoolConfig<String> poolConfig = new GenericObjectPoolConfig<String>();
		poolConfig.setMaxTotal(poolMaxActive);
		poolConfig.setMaxIdle(poolMaxIdle);
		poolConfig.setMinIdle(poolMinIdle);
		JedisClientConfiguration jedisClientConfiguration = JedisClientConfiguration.builder().usePooling().poolConfig(poolConfig).build();

		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(configuration, jedisClientConfiguration);
		jedisConnectionFactory.afterPropertiesSet();

		return jedisConnectionFactory;
	}

	@Bean
	public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofHours(1)).disableCachingNullValues();

		return RedisCacheManager.builder(connectionFactory).cacheDefaults(config).build();
	}

}
