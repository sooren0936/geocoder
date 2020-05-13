package com.demo.geocoder.controller;

import com.demo.geocoder.model.GeoData;
import com.demo.geocoder.repository.RedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class GeoController {

    @Autowired
    RedisRepository redisRepository;

    @GetMapping(value = "/decoder", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> queryDecoder(@RequestParam final String query) {
        final RestTemplate restTemplate = new RestTemplate();
        if (redisRepository.existsById(query)) {
            return new ResponseEntity<>(redisRepository.findById(query), HttpStatus.OK);
        } else {
             final ResponseEntity<String> responseEntity = restTemplate.exchange(
                "https://nominatim.openstreetmap.org/search/" + query + "?format=json",
                HttpMethod.GET, HttpEntity.EMPTY, String.class);
//             if (responseEntity.getBody().equals("[]") == true)
            redisRepository.save(new GeoData(query, responseEntity.getBody()));
            return responseEntity;
        }
    }

    @GetMapping(value = "/reversedecoder", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> queryReverseDecoder(@RequestParam(required = false, defaultValue = "") final String lat,
                                                 @RequestParam(required = false, defaultValue = "") final String lon) {
        final RestTemplate restTemplate = new RestTemplate();
        if (redisRepository.existsById(lat + lon)) {
            return new ResponseEntity<>(redisRepository.findById(lat + lon), HttpStatus.OK);
        } else {
            final ResponseEntity<String> responseEntity = restTemplate.exchange(
                "https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat=" + lat + "&lon=" + lon,
                HttpMethod.GET, HttpEntity.EMPTY, String.class);
            redisRepository.save(new GeoData(lat + lon, responseEntity.getBody()));
            return responseEntity;
        }
    }
}
