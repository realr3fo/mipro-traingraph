package com.mipro.traingraph.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.zip.GZIPInputStream;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mipro.traingraph.model.Train;

@Service
public class LiveTrainService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final StationService stationService;

    public LiveTrainService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
        this.stationService = new StationService();
    }

    /**
     * Fetches live train data from the API.
     *
     * @return a JSON response as a string
     * @throws IOException
     */
    public List<Train> getLiveTrains() throws IOException {
        List<String> stationCodes = stationService.fetchStationCodes();
        Set<Train> uniqueTrains = new HashSet<>();

        // Set request headers
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");
        headers.add("Digitraffic-User", "DT/Mipro-candidate");
        headers.add("Accept-Encoding", "gzip"); // Add the Accept-Encoding header for gzip encoding
        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        // Create a list of CompletableFuture to hold the asynchronous tasks
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (String stationCode : stationCodes) {
            // Replace "HKI" in the URL with the current station code
            String apiUrl = "https://rata.digitraffic.fi/api/v1/live-trains/station/" + stationCode
                    + "?minutes_before_departure=30&minutes_after_departure=30&minutes_before_arrival=30&minutes_after_arrival=30&include_nonstopping=true";

            // Create a CompletableFuture for each API request
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                try {
                    // Make the API request
                    final ResponseEntity<byte[]> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity,
                            byte[].class);

                    // Handle the response
                    if (response.getStatusCode().is2xxSuccessful()) {
                        byte[] responseBody = response.getBody();
                        String contentEncoding = response.getHeaders().getFirst(HttpHeaders.CONTENT_ENCODING);

                        // If the content is encoded using gzip, decode it
                        if (contentEncoding != null && contentEncoding.equalsIgnoreCase("gzip")) {
                            responseBody = decompressGzip(responseBody);
                        }

                        String jsonResponse = new String(responseBody, StandardCharsets.UTF_8);
                        List<Train> trains = objectMapper.readValue(jsonResponse, new TypeReference<List<Train>>() {
                        });

                        // Iterate over the list and filter out trains where runningCurrently is false
                        trains.removeIf(train -> !train.isRunningCurrently());

                        // Add the trains to the uniqueTrains set
                        synchronized (uniqueTrains) {
                            uniqueTrains.addAll(trains);
                        }
                    } else {
                        // TODO: TODO: Handle error case

                    }
                } catch (IOException e) {
                    // TODO: Handle exception
                }
            });

            // Add the CompletableFuture to the list
            futures.add(future);
        }

        // Wait for all CompletableFuture tasks to complete
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        // Convert the uniqueTrains set to a list and return it
        return new ArrayList<>(uniqueTrains);
    }

    /**
     * Decompresses GZIP-encoded content.
     *
     * @param compressedData the compressed data
     * @return the decompressed data
     * @throws IOException if an I/O error occurs
     */
    private byte[] decompressGzip(byte[] compressedData) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(compressedData))) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = gzipInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
        }
        return outputStream.toByteArray();
    }
}
