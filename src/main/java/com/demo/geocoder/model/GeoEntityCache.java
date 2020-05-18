package com.demo.geocoder.model;

import com.demo.geocoder.dto.LocationDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;

@RedisHash("geo.entity.cache")
public class GeoEntityCache implements Serializable {

    @Id
    private String id;
    private List<LocationDto> locationDto;

    public GeoEntityCache(String id, List<LocationDto> locationDto) {
        this.id = id;
        this.locationDto = locationDto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<LocationDto> getLocationDto() {
        return locationDto;
    }

    public void setLocationDto(List<LocationDto> locationDto) {
        this.locationDto = locationDto;
    }

    @Override
    public String toString() {
        return "LocationDto{" +
            "id='" + id + '\'' +
            ", geoDto=" + locationDto +
            '}';
    }
}
