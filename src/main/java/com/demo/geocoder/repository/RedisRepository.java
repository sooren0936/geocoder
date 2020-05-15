package com.demo.geocoder.repository;

import com.demo.geocoder.model.GeoData;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("!test")
public interface RedisRepository extends CrudRepository<GeoData, String> {
}
