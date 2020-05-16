package com.demo.geocoder.client;

import com.demo.geocoder.model.LocationDto;
import com.demo.geocoder.model.ReverseLocationDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "GeoController", url = "https://nominatim.openstreetmap.org")
public interface FeignGeoClient {

    @GetMapping(value = "/search/{query}?format=json", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<List<LocationDto>> queryDecoder(@PathVariable(name = "query") final String query);

    @GetMapping(value = "/reverse?format=json&lat={lat}&lon={lon}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<ReverseLocationDto> queryReverseDecoder(@PathVariable(name = "lat") final String latitude,
                                                           @PathVariable(name = "lon") final String longitude);
}

