package com.example.investment.home.fluctuation.controller;


import com.example.investment.home.fluctuation.controller.dto.response.FluctuationDTO;
import com.example.investment.home.fluctuation.service.FluctuationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/home/fluctuation")
public class FluctuationController {

    private final FluctuationService fluctuationService;

    @Autowired
    public FluctuationController(FluctuationService fluctuationService) {
        this.fluctuationService = fluctuationService;
    }

    @GetMapping
    public List<FluctuationDTO> getFluctuation() throws IOException {
        return fluctuationService.getFluctuation();
    }


}
