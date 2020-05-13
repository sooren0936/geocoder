package com.demo.geocoder.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GeoControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void decoderShouldReturnDataIncludePlaceId() {
        final String query = "Vasya";
        final ResponseEntity<String> entity = restTemplate.exchange(
            "http://localhost:" + port + "/decoder?query=" + query,
            HttpMethod.GET, HttpEntity.EMPTY, String.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).contains("13909533");
    }

    @Test
    public void decoderShouldReturnSquareBracketsWhenInvalidOrEmptyQuery() {
        final String query = "%%%%%";
        final ResponseEntity<String> entity = restTemplate.exchange(
            "http://localhost:" + port + "/decoder?query=" + query,
            HttpMethod.GET, HttpEntity.EMPTY, String.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).isEqualTo("[]");
    }

    @Test
    public void reverseDecoderShouldReturnDataIncludePlaceId() {
        final String lat = "58.8140982";
        final String lon = "125.5411205";
        final ResponseEntity<String> entity = restTemplate.exchange(
            "http://localhost:" + port + "/reversedecoder?lat=" + lat + "&lon=" + lon,
            HttpMethod.GET, HttpEntity.EMPTY, String.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).contains("13909533");
    }

    @Test
    public void reverseDecoderShouldReturnErrorWhenInvalidCoordinates() {
        final String lat = "10000";
        final String lon = "10000";
        final ResponseEntity<String> entity = restTemplate.exchange(
            "http://localhost:" + port + "/reversedecoder?lat=" + lat + "&lon=" + lon,
            HttpMethod.GET, HttpEntity.EMPTY, String.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).contains("Unable to geocode");
    }

    @Test
    public void reverseDecoderShouldReturnInternalServerErrorWhenEmptyCoordinates() {
        final String lat = "";
        final String lon = "50";
        final ResponseEntity<String> entity = restTemplate.exchange(
            "http://localhost:" + port + "/reversedecoder?lat=" + lat + "&lon=" + lon,
            HttpMethod.GET, HttpEntity.EMPTY, String.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        then(entity.getBody()).contains("400 Bad Request");
    }
}
