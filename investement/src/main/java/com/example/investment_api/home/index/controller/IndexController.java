package com.example.investment_api.home.index.controller;

import com.example.investment_api.home.index.controller.dto.KOSDAQResponse;
import com.example.investment_api.home.index.controller.dto.KOSPIResponse;

import com.example.investment_api.home.index.service.IndexService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/home/index")
public class IndexController {

    private final IndexService indexService;

    public IndexController(IndexService indexService) {
        this.indexService = indexService;
    }

    @GetMapping("/kospi")
    public KOSPIResponse getKOSPIIndex() {
        return indexService.getKOSPIIndex();
    }

    @GetMapping("/kosdaq")
    public KOSDAQResponse getKOSDAQIndex() {
        return indexService.getKOSDAQIndex();
    }
}
