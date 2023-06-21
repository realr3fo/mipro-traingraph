package com.mipro.traingraph.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mipro.traingraph.model.Station;
import com.mipro.traingraph.model.StationMetadataResponse;
import com.mipro.traingraph.service.StationService;

@Controller
public class StationController {

    private final StationService stationService;

    @Autowired
    public StationController(StationService stationService) {
        this.stationService = stationService;
    }

    @GetMapping("/stations")
    @ResponseBody
    public List<Station> getStations() throws IOException {
        return stationService.fetchStationMetadata();
    }

}
