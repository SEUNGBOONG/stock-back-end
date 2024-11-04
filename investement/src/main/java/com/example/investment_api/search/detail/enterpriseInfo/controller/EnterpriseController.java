package com.example.investment_api.search.detail.enterpriseInfo.controller;

import com.example.investment_api.search.detail.enterpriseInfo.controller.dto.EnterpriseDTO;
import com.example.investment_api.search.detail.enterpriseInfo.service.EnterpriseService;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/search/enterpriseInfo")
public class EnterpriseController {

    private final EnterpriseService enterpriseService;

    public EnterpriseController(final EnterpriseService enterpriseService) {
        this.enterpriseService = enterpriseService;
    }

    @GetMapping
    public ResponseEntity<List<EnterpriseDTO>> getEnterprise(@RequestParam String stockName) throws IOException {
        return getListResponseEntity(stockName);
    }

    private ResponseEntity<List<EnterpriseDTO>> getListResponseEntity(final String stockName) throws IOException {
        List<EnterpriseDTO> enterpriseDTOS = enterpriseService.getFinancialRatio(stockName);
        return ResponseEntity.ok(enterpriseDTOS);
    }
}
