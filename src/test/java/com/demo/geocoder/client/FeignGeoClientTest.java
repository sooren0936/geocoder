package com.demo.geocoder.client;

import com.demo.geocoder.TestApplication;
import com.demo.geocoder.dto.LocationDto;
import com.demo.geocoder.dto.ReverseLocationDto;
import feign.FeignException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableFeignClients
class FeignGeoClientTest {

    @Autowired
    private FeignGeoClient feignGeoClient;

    @Test
    public void feignQueryDecoderShouldReturnDataIncludeDisplayName() {
        final String query = "Moscow State University";
        final ResponseEntity<List<LocationDto>> entity = feignGeoClient.feignQueryDecoder(query);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody().size()).isEqualTo(4);
        then(entity.getBody().get(0).getDisplayName()).contains("Московский государственный университет");
        then(entity.getBody().get(0).getLat()).isEqualTo(55.70229715);
        then(entity.getBody().get(0).getLon()).isEqualTo(37.531797769429105);
    }

    @Test
    public void feignQueryDecoderShouldReturnEmptyAnswerWhenInvalidOrEmptyQuery() {
        final String query = "%%%%%";
        final ResponseEntity<List<LocationDto>> entity = feignGeoClient.feignQueryDecoder(query);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody().size()).isEqualTo(0);
    }

    @Test
    public void reverseFeignGeoClientDecoderShouldReturnDataIncludePlaceId() {
        final Double latitude = 55.70229715;
        final Double longitude = 37.531797769429105;
        final ResponseEntity<ReverseLocationDto> entity = feignGeoClient.feignQueryReverseDecoder(latitude, longitude);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody().getDisplayName()).contains("Московский государственный университет");
        then(entity.getBody().getPlaceId()).isEqualTo(235803036L);
        then(entity.getBody().getOsmId()).isEqualTo(2800169L);
    }

    @Test
    public void reverseFeignGeoClientDecoderShouldReturnNullBodyIncludePlaceId() {
        final Double latitude = 1000.0;
        final Double longitude = 37.531797769429105;
        final ResponseEntity<ReverseLocationDto> entity = feignGeoClient.feignQueryReverseDecoder(latitude, longitude);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody().getPlaceId()).isNull();
    }

    @Test
    public void reverseFeignGeoClientDecoderShouldReturnInternalServerErrorWhenEmptyCoordinates() {
        final Double latitude = null;
        final Double longitude = 37.531797769429105;
        final ResponseEntity<ReverseLocationDto> entity;
        Assertions.assertThrows(FeignException.class, () -> {
            feignGeoClient.feignQueryReverseDecoder(latitude, longitude);
        });
    }
}
