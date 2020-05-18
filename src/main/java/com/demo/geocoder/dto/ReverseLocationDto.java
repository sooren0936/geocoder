package com.demo.geocoder.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ReverseLocationDto {

    @JsonProperty("place_id")
    private Long placeId;

    @JsonProperty("licence")
    private String licence;

    @JsonProperty("osm_type")
    private String osmType;

    @JsonProperty("osm_id")
    private Long osmId;

    @JsonProperty("lat")
    private Double lat;

    @JsonProperty("lon")
    private Double lon;

    @JsonProperty("display_name")
    private String displayName;

    @JsonProperty("address")
    private Address address;

    @JsonProperty("boundingbox")
    private List<Double> boundingBox;

    public ReverseLocationDto(Long placeId, String licence, String osmType, Long osmId, Double lat, Double lon, String displayName, Address address, List<Double> boundingBox) {
        this.placeId = placeId;
        this.licence = licence;
        this.osmType = osmType;
        this.osmId = osmId;
        this.lat = lat;
        this.lon = lon;
        this.displayName = displayName;
        this.address = address;
        this.boundingBox = boundingBox;
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
        return osmType;
    }

    public void setOsm_type(String osm_type) {
        this.osmType = osm_type;
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

    public List<Double> getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(List<Double> boundingBox) {
        this.boundingBox = boundingBox;
    }

    public static class Address {

        @JsonProperty("village")
        private String village;

        @JsonProperty("county")
        private String county;

        @JsonProperty("state")
        private String state;

        @JsonProperty("country")
        private String country;

        @JsonProperty("country_code")
        private String countryCode;

        public String getVillage() {
            return village;
        }

        public void setVillage(String village) {
            this.village = village;
        }

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCountry_code() {
            return countryCode;
        }

        public void setCountry_code(String country_code) {
            this.countryCode = country_code;
        }
    }
}
