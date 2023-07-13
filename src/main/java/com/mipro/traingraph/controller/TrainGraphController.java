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
        return "train-graph";
    }

}
