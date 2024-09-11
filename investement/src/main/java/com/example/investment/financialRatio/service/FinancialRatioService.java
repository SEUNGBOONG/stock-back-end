package com.example.investment.financialRatio.service;

import com.example.investment.financialRatio.controller.dto.FinancialRatioDTO;
import com.example.investment.financialRatio.service.client.FinancialRatioDataFetcher;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FinancialRatioService {

    private final FinancialRatioDataFetcher financialRatioDataFetcher;

    public FinancialRatioService(FinancialRatioDataFetcher financialRatioDataFetcher) {
        this.financialRatioDataFetcher = financialRatioDataFetcher;
    }

    public List<FinancialRatioDTO> getFinancialRatio(String fidInputIscd) {
        JSONObject jsonObject = financialRatioDataFetcher.fetchFinancialRatioData(fidInputIscd);
        JSONArray outputArray = jsonObject.getJSONArray("output");
        return toFinancialRatioDTO(outputArray);
    }

    private static List<FinancialRatioDTO> toFinancialRatioDTO(final JSONArray outputArray) {
        List<FinancialRatioDTO> financialRatios = new ArrayList<>();

        for (int i = 1; i <= 4; i++) {
            if (i < outputArray.length()) {
                JSONObject record = outputArray.getJSONObject(i);

                FinancialRatioDTO dto = new FinancialRatioDTO(
                        record.optString("stac_yymm", ""),
                        record.optString("grs", ""),
                        record.optString("bsop_prfi_inrt", ""),
                        record.optString("ntin_inrt", ""),
                        record.optString("roe_val", ""),
                        record.optString("eps", ""),
                        record.optString("sps", ""),
                        record.optString("bps", ""),
                        record.optString("rsrv_rate", ""),
                        record.optString("lblt_rate", "")
                );
                financialRatios.add(dto);
            }
        }

        return financialRatios;
    }
}
