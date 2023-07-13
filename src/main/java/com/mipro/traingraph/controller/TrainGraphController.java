package com.mipro.traingraph.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class TrainGraphController {

    @GetMapping("/trains")
    public String showTrainGraph(Model model) throws Exception {
        String[] stations = { "HKI", "PSL", "ILA", "KHK", "HPL", "VMO", "PJM", "MÄK", "LPV" };
        String[] fullStationNames = { "Helsinki asema", "Pasila asema", "Ilmala asema", "Helsinki Kivihaka",
                "Huopalahti", "Valimo", "Pitäjänmäki", "Mäkkylä", "Leppävaara" };
        double[] actualDistances = { 0.2, 3.2, 4.4, 4.7, 6.4, 7.5, 8.5, 9.5, 11.2 };

        ObjectMapper objectMapper = new ObjectMapper();
        String stationsJson = objectMapper.writeValueAsString(stations);
        String fullStationNamesJson = objectMapper.writeValueAsString(fullStationNames);
        String actualDistancesJson = objectMapper.writeValueAsString(actualDistances);

        model.addAttribute("stations", stationsJson);
        model.addAttribute("fullStationNames", fullStationNamesJson);
        model.addAttribute("actualDistances", actualDistancesJson);

        return "train-graph";
    }

}
