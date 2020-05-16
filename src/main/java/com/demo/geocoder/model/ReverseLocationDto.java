package com.demo.geocoder.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ReverseLocationDto {

    @JsonProperty("place_id")
    private Long placeId;
    private String licence;
    private String osm_type;

    @JsonProperty("osm_id")
    private Long osmId;
    private Double lat;
    private Double lon;

    @JsonProperty("display_name")
    private String displayName;
    private Address address;

    @JsonProperty("boundingbox")
    private List<Double> boundingBox;

    public ReverseLocationDto(Long placeId, Double lat, Double lon) {
        this.placeId = placeId;
        this.lat = lat;
        this.lon = lon;
    }

    public Long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public String getOsm_type() {
        return osm_type;
    }

    public void setOsm_type(String osm_type) {
        this.osm_type = osm_type;
    }

    public Long getOsmId() {
        return osmId;
    }

    public void setOsmId(Long osmId) {
        this.osmId = osmId;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Double> getBoundingbox() {
        return boundingBox;
    }

    public void setBoundingbox(List<Double> boundingbox) {
        this.boundingBox = boundingbox;
    }
}
