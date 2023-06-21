package com.mipro.traingraph.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
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

import com.mipro.traingraph.model.Train;

@Service
public class LiveTrainService {
    private final RestTemplate restTemplate;

    public LiveTrainService() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * Fetches live train data from the API.
     *
     * @return a JSON response as a string
     * @throws IOException
     */
    public String getLiveTrains() throws IOException {
        // Set request headers
        final HttpHeaders headers = new HttpHeaders();
        headers.add("Accept", "application/json");
        headers.add("Digitraffic-User", "DT/Mipro-candidate");
        headers.add("Accept-Encoding", "gzip"); // Add the Accept-Encoding header for gzip encoding
        HttpEntity<String> entity = new HttpEntity<>("body", headers);

        // Make the API request
        final ResponseEntity<byte[]> response = restTemplate.exchange(
                "https://rata.digitraffic.fi/api/v1/live-trains/station/HKI?minutes_before_departure=30&minutes_after_departure=30&minutes_before_arrival=30&minutes_after_arrival=30&include_nonstopping=true",
                HttpMethod.GET, entity, byte[].class);

        // Handle the response
        if (response.getStatusCode().is2xxSuccessful()) {
            byte[] responseBody = response.getBody();
            String contentEncoding = response.getHeaders().getFirst(HttpHeaders.CONTENT_ENCODING);

            // If the content is encoded using gzip, decode it
            if (contentEncoding != null && contentEncoding.equalsIgnoreCase("gzip")) {
                responseBody = decompressGzip(responseBody);
            }

            String jsonResponse = new String(responseBody, StandardCharsets.UTF_8);
            return jsonResponse;
        } else {
            // Handle error case
            return null;
        }
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
