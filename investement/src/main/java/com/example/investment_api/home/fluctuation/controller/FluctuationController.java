//package com.example.investment_api.home.fluctuation.controller;
//
//import com.example.investment_api.home.fluctuation.controller.dto.response.FluctuationDTO;
//
//import com.example.investment_api.home.fluctuation.service.FluctuationService;
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.io.IOException;
//import java.util.List;
//
//@RestController
//@RequestMapping("home/fluctuation")
//public class FluctuationController {
//
//    private final FluctuationService fluctuationService;
//
//    public FluctuationController(FluctuationService fluctuationService) {
//        this.fluctuationService = fluctuationService;
//    }
//
//    @GetMapping
//    public List<FluctuationDTO> getFluctuation() throws IOException {
//        return fluctuationService.getFluctuation();
//    }
//}
