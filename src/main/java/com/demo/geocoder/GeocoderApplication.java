package com.demo.geocoder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@EnableRedisRepositories
@EnableFeignClients
@SpringBootApplication
public class GeocoderApplication {

	public static void main(String[] args) {
	    SpringApplication.run(GeocoderApplication.class, args);
	}
}
