package com.demo.geocoder.model;

import com.demo.geocoder.dto.ReverseLocationDto;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("reverse.geo.entity.cache")
public class ReverseGeoEntityCache implements Serializable {

    @Id
    private String id;
    private ReverseLocationDto reverseLocationDto;

    public ReverseGeoEntityCache(String id, ReverseLocationDto reverseLocationDto) {
        this.id = id;
        this.reverseLocationDto = reverseLocationDto;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ReverseLocationDto getReverseLocationDto() {
        return reverseLocationDto;
    }

    public void setReverseLocationDto(ReverseLocationDto reverseLocationDto) {
        this.reverseLocationDto = reverseLocationDto;
    }

    @Override
    public String toString() {
        return "Reverse{" +
            "id='" + id + '\'' +
            ", getReverseDto=" + reverseLocationDto +
            '}';
    }
}
