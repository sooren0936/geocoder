package com.demo.geocoder.controller;

import com.demo.geocoder.client.FeignGeoClient;
import com.demo.geocoder.dto.LocationDto;
import com.demo.geocoder.dto.ReverseLocationDto;
import com.demo.geocoder.model.GeoEntityCache;
import com.demo.geocoder.model.ReverseGeoEntityCache;
import com.demo.geocoder.repository.ReverseRedisRepository;
import com.demo.geocoder.repository.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Validated
@RestController
public class GeoController {

    RedisRepository redisRepository;
    ReverseRedisRepository reverseRedisRepository;
    FeignGeoClient feignGeoClient;

    @Autowired
    public GeoController(RedisRepository redisRepository, FeignGeoClient feignGeoClient, ReverseRedisRepository reverseRedisRepository) {
        this.redisRepository = redisRepository;
        this.feignGeoClient = feignGeoClient;
        this.reverseRedisRepository = reverseRedisRepository;
    }

    @GetMapping(value = "/decoder", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LocationDto>> queryDecoder(@RequestParam(name = "query") final String query) {
        final var cachedGeoDto = redisRepository.findById(query);
        if (cachedGeoDto.isPresent()) {
            return ResponseEntity.ok(cachedGeoDto.get().getLocationDto());
        }
        final ResponseEntity<List<LocationDto>> responseEntity = feignGeoClient.feignQueryDecoder(query);
        if (!responseEntity.getBody().isEmpty() && responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            redisRepository.save(new GeoEntityCache(query, responseEntity.getBody()));
        }
        return responseEntity;
    }

    @GetMapping(value = "/reversedecoder", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReverseLocationDto> queryReverseDecoder(@RequestParam(name = "lat") @Min(-90) @Max(90) final Double latitude,
                                                                  @RequestParam(name = "lon") @Min(-180) @Max(180) final Double longitude) {
        final var cachedGeoDto = reverseRedisRepository.findById(latitude + "," + longitude);
        if (cachedGeoDto.isPresent()) {
            return ResponseEntity.ok(cachedGeoDto.get().getReverseLocationDto());
        }
        final ResponseEntity<ReverseLocationDto> responseEntity = feignGeoClient.feignQueryReverseDecoder(latitude, longitude);
        reverseRedisRepository.save(new ReverseGeoEntityCache(latitude + "," + longitude, responseEntity.getBody()));
        return responseEntity;
    }
}
