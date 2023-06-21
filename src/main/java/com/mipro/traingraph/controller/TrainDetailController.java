package com.mipro.traingraph.controller;

import java.io.IOException;
import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mipro.traingraph.service.TrainDetailService;

@RestController
@RequestMapping("/trains")
public class TrainDetailController {
    private final TrainDetailService trainDetailService;

    public TrainDetailController(TrainDetailService trainDetailService) {
        this.trainDetailService = trainDetailService;
    }

    @GetMapping("/{trainNumber}")
    public ResponseEntity<String> getTrainDetails(
            @PathVariable int trainNumber) {
        try {
            String currentDate = LocalDate.now().toString();
            String trainDetails = trainDetailService.getTrainDetails(currentDate, trainNumber);
            return ResponseEntity.ok(trainDetails);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving train details");
        }
    }
}
