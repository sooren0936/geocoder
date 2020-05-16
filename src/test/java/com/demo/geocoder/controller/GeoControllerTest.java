package com.demo.geocoder.controller;

import com.demo.geocoder.client.FeignGeoClient;
import com.demo.geocoder.model.GeoEntityCache;
import com.demo.geocoder.model.LocationDto;
import com.demo.geocoder.model.ReverseLocationDto;
import com.demo.geocoder.repository.RedisRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.xml.stream.Location;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GeoControllerTest {

    RedisRepository redisRepository;
    FeignGeoClient feignGeoClient;

    @Autowired
    public GeoControllerTest(RedisRepository redisRepository, FeignGeoClient feignGeoClient) {
        this.redisRepository = redisRepository;
        this.feignGeoClient = feignGeoClient;
    }

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void decoderShouldReturnDataIncludePlaceId() {
        LocationDto locationDto = new LocationDto();
        final String query = "Vasya";
        final ResponseEntity<List<LocationDto>> entity =  feignGeoClient.queryDecoder(query);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).contains();
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
        ReverseLocationDto reverseLocationDto = new ReverseLocationDto(13909533L, 58.8140982, 125.5411205);
        final String latitude = "58.8140982";
        final String longitude = "125.5411205";
        final ResponseEntity<ReverseLocationDto> entity = feignGeoClient.queryReverseDecoder(latitude,longitude);
        then(entity.getBody()).isEqualToComparingFieldByField(reverseLocationDto);
    }

    @Test
    public void reverseDecoderShouldReturnErrorWhenInvalidCoordinates() {
        final String latitude = "10000";
        final String longitude = "10000";
        final ResponseEntity<ReverseLocationDto> entity = feignGeoClient.queryReverseDecoder(latitude,longitude);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        then(entity.getBody()).contains("Unable to geocode");
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
