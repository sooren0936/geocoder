package com.demo.geocoder.controller;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class GeoController {

    @GetMapping(value = "/decoder", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> queryDecoder(@RequestParam final String query) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(
            "https://nominatim.openstreetmap.org/search/" + query + "?format=json",
            HttpMethod.GET, HttpEntity.EMPTY, String.class);
    }

    @GetMapping(value = "/reversedecoder", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> queryReverseDecoder(@RequestParam(required = false, defaultValue = "") final String lat,
                                                  @RequestParam(required = false, defaultValue = "") final String lon) {
        RestTemplate restTemplate = new RestTemplate();
        return  restTemplate.exchange(
            "https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat=" + lat + "&lon=" + lon,
            HttpMethod.GET, HttpEntity.EMPTY, String.class);
    }
}
