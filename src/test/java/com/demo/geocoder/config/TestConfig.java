package com.demo.geocoder.config;

import com.demo.geocoder.repository.RedisRepository;
import com.mmnaseri.utils.spring.data.dsl.factory.RepositoryFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class TestConfig {

    @Bean
    @Profile("test")
    public RedisRepository redisRepository() {
        return RepositoryFactoryBuilder.builder().mock(RedisRepository.class);
    }
}
