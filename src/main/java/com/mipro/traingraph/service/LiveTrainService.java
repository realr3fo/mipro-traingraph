package com.mipro.traingraph.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.zip.GZIPInputStream;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mipro.traingraph.model.CustomLiveTrain;
import com.mipro.traingraph.model.TimeTableRow;
import com.mipro.traingraph.model.Train;

@Service
public class LiveTrainService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final Set<String> stationCodes;

    public LiveTrainService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
        // TODO: get this from stationShortCodeProviders;
        this.stationCodes = new HashSet<>(
                Arrays.asList("HKI", "PSL", "ILA", "KHK", "HPL", "VMO", "PJM", "M\u00C4K", "LPV"));
    }

    /**
     * Fetches live train data from the API.
     *
     * @return a JSON response as a string
     * @throws IOException
     */
    public List<Train> getLiveTrains() throws IOException {
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
                        // trains.removeIf(train -> !train.isRunningCurrently());

                        // Add the trains to the uniqueTrains set
                        synchronized (uniqueTrains) {
                            uniqueTrains.addAll(trains);
                        }
                    } else {
                        // Handle error case
                        System.err.println("Error: API request failed with status code " + response.getStatusCode());
                    }
                } catch (IOException e) {
                    // Handle exception
                    System.err.println("Error: Exception occurred during API request: " + e.getMessage());
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
     * Fetches filtered live train data from the API.
     *
     * @return a JSON response as a string
     * @throws IOException
     */
    public List<CustomLiveTrain> getLiveTrainsCustom() throws IOException {
        List<Train> liveTrains = getLiveTrains();
        List<CustomLiveTrain> filteredTrains = filterTrains(liveTrains);
        List<CustomLiveTrain> postProcessedTrains = postProcessTrains(filteredTrains);
        return postProcessedTrains;
    }

    private List<CustomLiveTrain> filterTrains(List<Train> liveTrains) {
        List<CustomLiveTrain> filteredTrains = new ArrayList<>();
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime thirtyMinutesAgo = currentTime.minus(30, ChronoUnit.MINUTES);
        LocalDateTime thirtyMinutesLater = currentTime.plus(30, ChronoUnit.MINUTES);

        try {
            for (Train liveTrain : liveTrains) {
                Long trainNumber = liveTrain.getTrainNumber();
                if (!(trainNumber >= 8000 && trainNumber <= 8999 || trainNumber >= 900 && trainNumber <= 999
                        || trainNumber > 10000 && trainNumber < 20000)) {
                    continue;
                }
                String trainCategory = liveTrain.getTrainCategory();
                String trainColor = "rgb(0, 68, 0)";
                if (trainCategory.equals("Long-distance") || trainNumber >= 10000) {
                    trainColor = "rgb(255, 0, 0)";
                }
                CustomLiveTrain filteredTrain = new CustomLiveTrain(liveTrain.getTrainNumber(), new ArrayList<>(),
                        new ArrayList<>(), new ArrayList<>(),
                        new ArrayList<>(), trainColor);

                List<String> scheduledStationCodes = new ArrayList<>();
                List<String> scheduledTimes = new ArrayList<>();
                List<String> actualStationCodes = new ArrayList<>();
                List<String> actualTimes = new ArrayList<>();
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

                for (TimeTableRow timeTableRow : liveTrain.getTimeTableRows()) {
                    if (stationCodes.contains(timeTableRow.getStationShortCode())) {
                        String scheduledTimeString = timeTableRow.getScheduledTime();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                                .withZone(ZoneOffset.UTC);
                        ZonedDateTime utcDateTime = ZonedDateTime.parse(scheduledTimeString, formatter);
                        LocalDateTime scheduledTime = utcDateTime.withZoneSameInstant(ZoneId.systemDefault())
                                .toLocalDateTime();

                        if (scheduledTime.isAfter(thirtyMinutesAgo) && scheduledTime.isBefore(thirtyMinutesLater)) {
                            scheduledStationCodes.add(timeTableRow.getStationShortCode());
                            String formattedTime = scheduledTime.format(timeFormatter);
                            scheduledTimes.add(formattedTime);
                        }

                        String actualTimeString = timeTableRow.getActualTime();
                        if (actualTimeString == null) {
                            continue;
                        }
                        ZonedDateTime utcDateActualTime = ZonedDateTime.parse(actualTimeString, formatter);
                        LocalDateTime actualTime = utcDateActualTime.withZoneSameInstant(ZoneId.systemDefault())
                                .toLocalDateTime();

                        if (actualTime.isAfter(thirtyMinutesAgo) && actualTime.isBefore(thirtyMinutesLater)) {
                            actualStationCodes.add(timeTableRow.getStationShortCode());
                            String formattedTime = actualTime.format(timeFormatter);
                            actualTimes.add(formattedTime);
                        }
                    }
                }
                if (scheduledStationCodes.size() > 2) {
                    filteredTrain.setScheduledStationCodes(scheduledStationCodes);
                    filteredTrain.setScheduledTimes(scheduledTimes);
                }
                if (actualStationCodes.size() > 2) {
                    filteredTrain.setActualStationCodes(actualStationCodes);
                    filteredTrain.setActualTimes(actualTimes);
                }
                filteredTrains.add(filteredTrain);
            }
        } catch (Exception e) {
            // Handle exception
        }
        return filteredTrains;
    }

    private List<CustomLiveTrain> postProcessTrains(List<CustomLiveTrain> filteredTrains) {
        List<CustomLiveTrain> postProcessedTrains = new ArrayList<>();

        try {
            for (CustomLiveTrain train : filteredTrains) {
                List<Integer> splitIndices = new ArrayList<>();
                List<String> scheduledTimes = train.getScheduledTimes();
                DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

                for (int i = 0; i < scheduledTimes.size() - 1; i++) {
                    LocalTime time1 = LocalTime.parse(scheduledTimes.get(i), timeFormatter);
                    LocalTime time2 = LocalTime.parse(scheduledTimes.get(i + 1), timeFormatter);
                    long minutesBetween = ChronoUnit.MINUTES.between(time1, time2);
                    if (minutesBetween < 0) {
                        minutesBetween += 24 * 60;
                    }
                    if (minutesBetween > 30) {
                        splitIndices.add(i);
                    }
                }

                if (splitIndices.isEmpty()) {
                    postProcessedTrains.add(train);
                } else {
                    int startIndex = 0;
                    for (int splitIndex : splitIndices) {
                        CustomLiveTrain splitTrain = createSplitTrain(train, startIndex, splitIndex);
                        postProcessedTrains.add(splitTrain);
                        startIndex = splitIndex + 1;
                    }
                    CustomLiveTrain splitTrain = createSplitTrain(train, startIndex, scheduledTimes.size() - 1);
                    postProcessedTrains.add(splitTrain);
                }
            }
        } catch (Exception e) {
            // Handle exception
        }
        return postProcessedTrains;
    }

    private CustomLiveTrain createSplitTrain(CustomLiveTrain originalTrain, int startIndex, int endIndex) {
        List<String> scheduledStationCodes = originalTrain.getScheduledStationCodes().subList(startIndex, endIndex + 1);
        List<String> scheduledTimes = originalTrain.getScheduledTimes().subList(startIndex, endIndex + 1);
        List<String> actuaStationCodes = new ArrayList<>();
        List<String> actualTimes = new ArrayList<>();
        boolean hasActualTimes = false;

        try {
            for (int i = startIndex; i <= endIndex; i++) {
                String actualTime = i < originalTrain.getActualTimes().size() ? originalTrain.getActualTimes().get(i)
                        : null;
                actualTimes.add(actualTime);
                if (actualTime != null) {
                    hasActualTimes = true;
                }
            }

            if (!hasActualTimes) {
                actualTimes.clear();
            }
        } catch (Exception e) {
            // Handle exception
        }

        CustomLiveTrain splitTrain = new CustomLiveTrain(originalTrain.getTrainNumber(),
                new ArrayList<>(scheduledStationCodes), new ArrayList<>(actuaStationCodes),
                new ArrayList<>(scheduledTimes), new ArrayList<>(actualTimes),
                originalTrain.getTrainColor());
        return splitTrain;
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
