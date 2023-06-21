package com.mipro.traingraph.provider;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class StationNameProvider {
    public List<String> getStationNames() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        // Load the JSON file as a resource stream
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data/stationNames.json");

        // Parse the JSON into a list of strings
        List<String> stationNames = objectMapper.readValue(inputStream, new TypeReference<List<String>>() {
        });

        return stationNames;
    }
}
