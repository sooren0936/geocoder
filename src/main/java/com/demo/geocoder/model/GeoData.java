package com.demo.geocoder.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash("geo.data")
public class GeoData implements Serializable {

    @Id
    private String id;
    private String entity;

    public GeoData(String id, String entity) {
        this.id = id;
        this.entity = entity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    @Override
    public String toString() {
        return "GeoData{" +
            "id='" + id + '\'' +
            ", entity='" + entity + '\'' +
            '}';
    }
}
