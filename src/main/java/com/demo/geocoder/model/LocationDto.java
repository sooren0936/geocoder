package com.demo.geocoder.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class LocationDto {

    @JsonProperty("place_id")
    private Long placeId;
    private String licence;
    private String osm_type;

    @JsonProperty("osm_id")
    private Long osmId;

    @JsonProperty("boundingbox")
    private List<Double> boundingBox;
    private Double lat;
    private Double lon;

    @JsonProperty("display_name")
    private String displayName;

    @JsonProperty("class")
    private String geoClass;
    private String type;
    private Double importance;

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

    public List<Double> getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(List<Double> boundingBox) {
        this.boundingBox = boundingBox;
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

    public String getGeoClass() {
        return geoClass;
    }

    public void setGeoClass(String geoClass) {
        this.geoClass = geoClass;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getImportance() {
        return importance;
    }

    public void setImportance(Double importance) {
        this.importance = importance;
    }
}
