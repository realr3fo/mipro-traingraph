package com.mipro.traingraph.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mipro.traingraph.model.TimeTableRow;
import com.mipro.traingraph.model.Train;

public class TrainGraphService {
    private LiveTrainService liveTrainService;

    public TrainGraphService(LiveTrainService liveTrainService) {
        this.liveTrainService = liveTrainService;
    }

    public List<Train> getTrainsByRoutes() throws IOException {
        List<Train> liveTrains = liveTrainService.getLiveTrains();
        List<Train> filteredTrains = new ArrayList<>();

        Set<String> validStations = new HashSet<>();
        validStations.add("HKI");
        validStations.add("PSL");
        validStations.add("HPL");
        validStations.add("LPV");

        for (Train train : liveTrains) {
            boolean validRoute = false;

            List<TimeTableRow> timeTableRows = train.getTimeTableRows();
            String origin = timeTableRows.get(0).getStationShortCode();
            String destination = timeTableRows.get(timeTableRows.size() - 1).getStationShortCode();

            if (validStations.contains(origin) && validStations.contains(destination)) {
                for (TimeTableRow row : timeTableRows) {
                    String stationCode = row.getStationShortCode();
                    if (validStations.contains(stationCode)) {
                        validRoute = true;
                        break;
                    }
                }
            }

            if (validRoute) {
                filteredTrains.add(train);
            }
        }

        return filteredTrains;
    }
}
