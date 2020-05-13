package com.demo.geocoder.repository;

import com.demo.geocoder.model.GeoData;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisRepository extends CrudRepository<GeoData, String> {
}
