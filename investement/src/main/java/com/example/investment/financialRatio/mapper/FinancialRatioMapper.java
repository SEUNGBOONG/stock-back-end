package com.example.investment.financialRatio.mapper;

import com.example.investment.financialRatio.controller.dto.FinancialRatioDTO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class FinancialRatioMapper {

    public void toFinancialRatioDTO(final JSONArray outputArray) {
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

    }
}
