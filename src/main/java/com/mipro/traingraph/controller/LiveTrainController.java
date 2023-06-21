package com.mipro.traingraph.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mipro.traingraph.model.Station;
import com.mipro.traingraph.model.Train;
import com.mipro.traingraph.service.LiveTrainService;
import com.mipro.traingraph.service.StationService;

@Controller
public class LiveTrainController {
    private final LiveTrainService liveTrainService;

    @Autowired
    public LiveTrainController(LiveTrainService liveTrainService) {
        this.liveTrainService = liveTrainService;
    }

    @GetMapping("/live-trains")
    @ResponseBody
    public String getLiveTrains() throws IOException {
        return liveTrainService.getLiveTrains();
    }
}
