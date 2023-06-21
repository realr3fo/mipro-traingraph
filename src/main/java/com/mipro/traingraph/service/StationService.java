package com.mipro.traingraph.service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.mipro.traingraph.model.Station;
import com.mipro.traingraph.model.StationMetadataResponse;
import com.mipro.traingraph.provider.StationNameProvider;

@Service
public class StationService {
    private final RestTemplate restTemplate;

    public StationService() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * Fetches station metadata from the API.
     *
     * @return a list of StationMetadataResponse objects
     * @throws IOException
     */
    public List<Station> fetchStationMetadata() throws IOException {
        // Create the HTTP request factory
        final HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory(
                HttpClientBuilder.create().build());

        // Create the RestTemplate instance with the request factory
        final RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory);

        // Set request headers
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Accept-Encoding", "gzip");
        headers.add("Digitraffic-User", "DT/Mipro-candidate");
        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        // Make the API request
        final ResponseEntity<List<StationMetadataResponse>> response = restTemplate.exchange(
                "https://rata.digitraffic.fi/api/v1/metadata/stations",
                HttpMethod.GET, entity, new ParameterizedTypeReference<List<StationMetadataResponse>>() {
                });

        // Print the response for debugging purposes
        // System.out.println(response.toString());
        List<String> stationNames = new StationNameProvider().getStationNames();
        System.out.println(stationNames.toString());

        List<Station> stations = response.getBody().stream()
                .map(metadata -> new Station(metadata.getStationName(), metadata.getStationShortCode(),
                        metadata.getLongitude(), metadata.getLatitude()))
                .collect(Collectors.toList());

        List<Station> filteredStations = stations.stream()
                .filter(station -> stationNames.contains(station.getStationName()))
                .collect(Collectors.toList());
        // System.out.println(filteredStations.toString());

        // Handle the response
        if (response.getStatusCode().is2xxSuccessful()) {
            return filteredStations;
        } else {
            // Handle error case
            return Collections.emptyList();
        }
    }
}
