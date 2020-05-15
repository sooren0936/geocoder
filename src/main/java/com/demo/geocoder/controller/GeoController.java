package com.demo.geocoder.controller;

import com.demo.geocoder.model.GeoData;
import com.demo.geocoder.model.LocationDto;
import com.demo.geocoder.repository.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@RestController
public class GeoController {

    RedisRepository redisRepository;

    @Autowired
    public GeoController(RedisRepository redisRepository){
        this.redisRepository = redisRepository;
    }

    @GetMapping(value = "/decoder", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LocationDto>> queryDecoder(@RequestParam(name = "query") final String query) {
        final RestTemplate restTemplate = new RestTemplate();
//        final var cachedGeoData = redisRepository.findById(query);
//        if (cachedGeoData.isPresent()) {
//            return ResponseEntity.ok(cachedGeoData.map(GeoData::getEntity).orElse(null));
//        }
        final ResponseEntity<List<LocationDto>> responseEntity = restTemplate.exchange(
            "https://nominatim.openstreetmap.org/search/" + query + "?format=json",
            HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<LocationDto>>() {} );
//        redisRepository.save(new GeoData(query, responseEntity.getBody()));
        return responseEntity;
    }

    @GetMapping(value = "/reversedecoder", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LocationDto>> queryReverseDecoder(@RequestParam(name = "lat") final String latitude,
                                                      @RequestParam(name = "lon") final String longitude) {
        final RestTemplate restTemplate = new RestTemplate();
//        final var cachedGeoData = redisRepository.findById(latitude + longitude);
//        if (cachedGeoData.isPresent()) {
//            return ResponseEntity.ok(cachedGeoData.map(GeoData::getEntity).orElse(null));
//        }
        final ResponseEntity<List<LocationDto>> responseEntity = restTemplate.exchange(
            "https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat=" + latitude + "&lon=" + longitude,
            HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<LocationDto>>() {});
//        redisRepository.save(new GeoData(latitude + longitude, responseEntity.getBody()));
        return responseEntity;
    }
}

//    @GetMapping(value = "/decoder", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<String> queryDecoder(@RequestParam(name = "query") final String query) {
//        final RestTemplate restTemplate = new RestTemplate();
//        final var cachedGeoData = redisRepository.findById(query);
//        if (cachedGeoData.isPresent()) {
//            return ResponseEntity.ok(cachedGeoData.map(GeoData::getEntity).orElse(null));
//        }
//        final ResponseEntity<String> responseEntity = restTemplate.exchange(
//            "https://nominatim.openstreetmap.org/search/" + query + "?format=json",
//            HttpMethod.GET, HttpEntity.EMPTY, String.class);
//        redisRepository.save(new GeoData(query, responseEntity.getBody()));
//        return responseEntity;
//    }
//
//    @GetMapping(value = "/reversedecoder", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<String> queryReverseDecoder(@RequestParam(name = "lat") final String latitude,
//                                                 @RequestParam(name = "lon") final String longitude) {
//        final RestTemplate restTemplate = new RestTemplate();
//        final var cachedGeoData = redisRepository.findById(latitude + longitude);
//        if (cachedGeoData.isPresent()) {
//            return ResponseEntity.ok(cachedGeoData.map(GeoData::getEntity).orElse(null));
//        }
//        final ResponseEntity<String> responseEntity = restTemplate.exchange(
//            "https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat=" + latitude + "&lon=" + longitude,
//            HttpMethod.GET, HttpEntity.EMPTY, String.class);
//        redisRepository.save(new GeoData(latitude + longitude, responseEntity.getBody()));
//        return responseEntity;
//    }
//}
