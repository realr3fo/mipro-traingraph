package com.mipro.traingraph.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mipro.traingraph.model.CustomLiveTrain;
import com.mipro.traingraph.service.LiveTrainService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Controller
public class LiveTrainController {
    private final LiveTrainService liveTrainService;

    @Autowired
    public LiveTrainController(LiveTrainService liveTrainService) {
        this.liveTrainService = liveTrainService;
    }

    @GetMapping("/live-trains")
    @ResponseBody
    public ResponseEntity<?> getLiveTrainsFilter() throws IOException {
        List<CustomLiveTrain> liveTrains = liveTrainService.getLiveTrainsCustom();
        return new ResponseEntity<>(liveTrains, HttpStatus.OK);
    }
}
