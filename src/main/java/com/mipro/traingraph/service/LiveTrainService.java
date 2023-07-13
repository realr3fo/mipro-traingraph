package com.mipro.traingraph.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;
import java.util.zip.GZIPInputStream;

import javax.validation.constraints.Null;

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
import com.mipro.traingraph.model.CustomLiveTrain;
import com.mipro.traingraph.model.TableRowEntry;
import com.mipro.traingraph.model.TimeTableRow;
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
                        // trains.removeIf(train -> !train.isRunningCurrently());

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
     * Fetches filtered live train data from the API.
     *
     * @return a JSON response as a string
     * @throws IOException
     */
    public List<CustomLiveTrain> getLiveTrainsCustom() throws IOException {
        /**
         * BACKEND:
         * for each of live trains:
         * 1. train number as it is
         * 2. get scheduleTimes:
         * - for each element in timtablerows, compare the times with current time,
         * first element is 30 mins ago
         * - if stationshortcode is within stationNames, add to the array the scheduled
         * time and actual time if exist HH:MM:ss
         * - iterate the next ones
         * 3. get actualTimes:
         * - if timetablerow has actualtime, add to the actualtime arrays, same concept
         * as scheduledtimes
         * 4. handle returning trains
         * finally, returns
         * [
         * {
         * "trainNumber": 8893,
         * "stationCodes": [HKI, PSL, ILA, KHK]
         * "scheduledTimes": ["14:11:01", "14:12:01", "14:13:01", ""14:14:01"",
         * "14:15:01"],
         * "actualTimes": ["14:11:31", "14:12:31", "14:13:31", ""14:14:31"",
         * "14:15:31"],
         * },
         * {
         * "trainNumber": 8369,
         * "stationCodes": [KHK, ILA, PSL, HKI]
         * "scheduledTimes": ["14:11:01", "14:12:01", "14:13:01", ""14:14:01"",
         * "14:15:01"],
         * "actualTimes": ["14:11:31", "14:12:31", "14:13:31", ""14:14:31"",
         * "14:15:31"],
         * },
         * ]
         */
        List<Train> liveTrains = getLiveTrains();
        List<CustomLiveTrain> filteredTrains = new ArrayList<>();
        Set<String> stationCodes = new HashSet<>(
                Arrays.asList("HKI", "PSL", "ILA", "KHK", "HPL", "VMO", "PJM", "M\u00C4K", "LPV"));

        LocalDateTime currentTime = LocalDateTime.now();
        // Check if scheduledTime is within 30 minutes ago or 30 minutes later from the
        // current time
        LocalDateTime thirtyMinutesAgo = currentTime.minus(30, ChronoUnit.MINUTES);
        LocalDateTime thirtyMinutesLater = currentTime.plus(30, ChronoUnit.MINUTES);
        for (Train liveTrain : liveTrains) {
            Long trainNumber = liveTrain.getTrainNumber();
            if (!(trainNumber >= 8000 && trainNumber <= 8999 || trainNumber >= 900 && trainNumber <= 999
                    || trainNumber > 10000 && trainNumber < 20000)) {
                continue;
            }
            String trainCategory = liveTrain.getTrainCategory();
            String trainColor = "rgb(0, 68, 0)";
            if (trainCategory.equals("Long-distance")) {
                trainColor = "rgb(255, 0, 0)";
        }
            CustomLiveTrain filteredTrain = new CustomLiveTrain(liveTrain.getTrainNumber(), new ArrayList<>(),
                    new ArrayList<>(), new ArrayList<>(),
                    new ArrayList<>(), trainColor);

            // System.out.println(liveTrain.getTrainNumber());
            List<String> scheduledStationCodes = new ArrayList<>();
            List<String> scheduledTimes = new ArrayList<>();
            List<String> actualStationCodes = new ArrayList<>();
            List<String> actualTimes = new ArrayList<>();
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            for (TimeTableRow timeTableRow : liveTrain.getTimeTableRows()) {
                if (stationCodes.contains(timeTableRow.getStationShortCode())) {
                    // ScheduleTime
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

                    // ActualTime
                    String actualTimeString = timeTableRow.getActualTime();
                    if (actualTimeString == null) {
                        continue;
                    }
                    ZonedDateTime utcDateActualTime = ZonedDateTime.parse(actualTimeString,
                            formatter);
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
        List<CustomLiveTrain> postProcessedTrains = new ArrayList<>();

        for (CustomLiveTrain train : filteredTrains) {
            List<Integer> splitIndices = new ArrayList<>();
            List<String> scheduledTimes = train.getScheduledTimes();

            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

            for (int i = 0; i < scheduledTimes.size() - 1; i++) {
                LocalTime time1 = LocalTime.parse(scheduledTimes.get(i), timeFormatter);
                LocalTime time2 = LocalTime.parse(scheduledTimes.get(i + 1), timeFormatter);

                // Calculate the duration, handling the case when the time range crosses
                // midnight
                long minutesBetween = ChronoUnit.MINUTES.between(time1, time2);
                if (minutesBetween < 0) {
                    minutesBetween += 24 * 60; // Add 24 hours in minutes to get the correct duration
                }

                // Check if the difference between two consecutive scheduled times is greater
                // than 30 minutes
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

        return postProcessedTrains;
    }

    // Helper method to create a new CustomLiveTrain instance by splitting the
    // original train
    private CustomLiveTrain createSplitTrain(CustomLiveTrain originalTrain, int startIndex, int endIndex) {
        List<String> scheduledStationCodes = originalTrain.getScheduledStationCodes().subList(startIndex, endIndex + 1);
        List<String> scheduledTimes = originalTrain.getScheduledTimes().subList(startIndex, endIndex + 1);
        List<String> actuaStationCodes = new ArrayList<>();
        List<String> actualTimes = new ArrayList<>();
        boolean hasActualTimes = false;
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
