package com.demo.geocoder.controller;

import com.demo.geocoder.TestApplication;
import com.demo.geocoder.client.FeignGeoClient;
import com.demo.geocoder.dto.LocationDto;
import com.demo.geocoder.dto.ReverseLocationDto;
import com.demo.geocoder.repository.RedisRepository;
import org.assertj.core.api.AbstractAtomicReferenceAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GeoControllerTest {

    @Autowired
    RedisRepository redisRepository;

    @Autowired
    TestRestTemplate restTemplate;

    @MockBean
    protected FeignGeoClient feignGeoClient;

    @LocalServerPort
    private int port;

    private static ResponseEntity<List<LocationDto>> buildTestLocationDto() {

        final LocationDto locationDto = new LocationDto();
        final List <Double> boundingBox = new ArrayList<>();
        boundingBox.add(55.6935861);
        boundingBox.add(55.7571071);
        boundingBox.add(37.5063561);
        boundingBox.add(37.613069);

        locationDto.setPlaceId(235803036L);
        locationDto.setLicence("Data © OpenStreetMap contributors, ODbL 1.0. https://osm.org/copyright");
        locationDto.setOsm_type("relation");
        locationDto.setOsmId(2800169L);
        locationDto.setBoundingBox(boundingBox);
        locationDto.setLat(55.70229715);
        locationDto.setLon(37.531797769429105);
        locationDto.setDisplayName("Московский государственный университет им. М. В. Ломоносова, проезд Апакова, " +
                                   "Бабий городок, район Якиманка, Центральный административный округ, " +
                                   "Москва, Центральный федеральный округ, 119049, Россия");
        locationDto.setGeoClass("amenity");
        locationDto.setType("university");
        locationDto.setImportance(0.5895141029627923);

        List<LocationDto> list = new ArrayList<>();
        list.add(locationDto);

        return ResponseEntity.ok(list);
    }

    private static ResponseEntity<ReverseLocationDto> buildTestReverseLocationDto() {
        final ReverseLocationDto reverseLocationDto = new ReverseLocationDto();
        final ReverseLocationDto.Address address = new ReverseLocationDto.Address();
        address.setVillage("setVillage");
        address.setCounty("setCounty");
        address.setState("setState");
        address.setCountry("setCountry");
        address.setCountryCode("1");

        final List <Double> boundingBox = new ArrayList<>();
        boundingBox.add(55.6935861);
        boundingBox.add(55.7571071);
        boundingBox.add(37.5063561);
        boundingBox.add(37.613069);

        reverseLocationDto.setPlaceId(235803L);
        reverseLocationDto.setLicence("Data © OpenStreetMap contributors, ODbL 1.0. https://osm.org/copyright");
        reverseLocationDto.setOsm_type("relation");
        reverseLocationDto.setOsmId(2800169L);

        reverseLocationDto.setLat(55.70229715);
        reverseLocationDto.setLon(37.531797769429105);
        reverseLocationDto.setDisplayName("Московский государственный университет им. М. В. Ломоносова, проезд Апакова, " +
            "Бабий городок, район Якиманка, Центральный административный округ, " +
            "Москва, Центральный федеральный округ, 119049, Россия");
        reverseLocationDto.setAddress(address);
        reverseLocationDto.setBoundingBox(boundingBox);

        return ResponseEntity.ok(reverseLocationDto);
    }

    @BeforeEach
    public void setUp(){
        when(feignGeoClient.feignQueryDecoder(anyString())).thenReturn(buildTestLocationDto());
        when(feignGeoClient.queryReverseDecoder(anyDouble(), anyDouble())).thenReturn(buildTestReverseLocationDto());
    }

    @Test
    public void decoderShouldReturnDataIncludePlaceId() {
        final String query = "Moscow State University";
        final ResponseEntity<List<LocationDto>> entity = restTemplate.exchange(
            "http://localhost:" + port + "/decoder?query=" + query,
            HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<LocationDto>>() {});
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody().size()).isEqualTo(1);
        then(entity.getBody().get(0)).isEqualTo(buildTestLocationDto().getBody().get(0));
    }

    @Test
    public void decoderShouldReturnSquareBracketsWhenInvalidOrEmptyQuery() {
        when(feignGeoClient.feignQueryDecoder(anyString())).thenReturn(ResponseEntity.ok(new ArrayList<>()));
        final String query = "%%%%%";
        final ResponseEntity<List<LocationDto>> entity = restTemplate.exchange(
            "http://localhost:" + port + "/decoder?query=" + query,
            HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<LocationDto>>() {});
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody().size()).isEqualTo(0);
    }

    @Test
    public void reverseDecoderShouldReturnDataIncludePlaceId() {
        final String latitude = "58.8140982";
        final String longitude = "125.5411205";
        final ResponseEntity<ReverseLocationDto> entity = restTemplate.exchange(
            "http://localhost:" + port + "/reversedecoder?lat=" + latitude + "&lon=" + longitude,
            HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<ReverseLocationDto>() {});
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody().getPlaceId()).isEqualTo(buildTestReverseLocationDto().getBody().getPlaceId());
    }

    @Test
    public void reverseDecoderShouldReturnInternalServerErrorWhenEmptyCoordinates() {
        final String latitude = "";
        final String longitude = "50";
        final ResponseEntity<ReverseLocationDto> entity = restTemplate.exchange(
            "http://localhost:" + port + "/reversedecoder?lat=" + latitude + "&lon=" + longitude,
            HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<ReverseLocationDto>() {});
        then(entity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
//        then(entity.getBody()).isNull();
    }
}
