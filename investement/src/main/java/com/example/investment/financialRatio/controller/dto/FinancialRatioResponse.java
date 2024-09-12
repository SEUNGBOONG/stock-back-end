package com.example.investment.financialRatio.controller.dto;

import java.util.List;

public class FinancialRatioResponse {

    private List<FinancialRatioDTO> output;

    public List<FinancialRatioDTO> getOutput() {
        return output;
    }

    public void setOutput(List<FinancialRatioDTO> output) {
        this.output = output;
    }
}
