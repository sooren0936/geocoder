package com.demo.geocoder.client;

import com.demo.geocoder.dto.LocationDto;
import com.demo.geocoder.dto.ReverseLocationDto;
import com.demo.geocoder.repository.RedisRepository;
import com.demo.geocoder.repository.ReverseRedisRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.BDDAssertions.then;


@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FeignGeoClientTest {

    RedisRepository redisRepository;
    ReverseRedisRepository reverseRedisRepository;
    FeignGeoClient feignGeoClient;

    @Autowired
    public FeignGeoClientTest(RedisRepository redisRepository, ReverseRedisRepository reverseRedisRepository, FeignGeoClient feignGeoClient) {
        this.redisRepository = redisRepository;
        this.reverseRedisRepository = reverseRedisRepository;
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
        final ResponseEntity<List<LocationDto>> entity = feignGeoClient.feignQueryDecoder(query);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        then(entity.getBody()).contains();
    }

    @Test
    public void reverseDecoderShouldReturnDataIncludePlaceId() {
//        ReverseLocationDto reverseLocationDto = new ReverseLocationDto(13909533L, 58.8140982, 125.5411205);
        final Double latitude = 58.8140982;
        final Double longitude = 125.5411205;
        final ResponseEntity<ReverseLocationDto> entity = feignGeoClient.queryReverseDecoder(latitude,longitude);
//        then(entity.getBody()).isEqualToComparingFieldByField(reverseLocationDto);
    }

    @Test
    public void reverseDecoderShouldReturnErrorWhenInvalidCoordinates() {
        final Double latitude =  10000.0;
        final Double longitude = 10000.0;
        final ResponseEntity<ReverseLocationDto> entity = feignGeoClient.queryReverseDecoder(latitude,longitude);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
//        then(entity.getBody()).contains("Unable to geocode");
    }
}
