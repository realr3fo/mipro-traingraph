package com.mipro.traingraph.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TrainGraphController {

    @GetMapping("/trains")
    public String showTrainGraph(Model model) {
        List<String> stationNames = Arrays.asList(
                "Helsinki asema",
                "Pasila asema",
                "Ilmala asema",
                "Helsinki Kivihaka",
                "Huopalahti",
                "Valimo",
                "Pitäjänmäki",
                "Mäkkylä",
                "Leppävaara");
        List<Integer> distances = Arrays.asList(
                // Distances between stations in km
                0, 5, 10, 15, 20, 25, 30, 35, 40);

        model.addAttribute("stationNames", stationNames);
        model.addAttribute("distances", distances);

        return "train-graph";
    }

    @GetMapping("/graph")
    public String showLineGraph(Model model) {

        return "plot-lines";
    }

}
