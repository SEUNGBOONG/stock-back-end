package com.example.investment.home.fluctuation.mapper;

import com.example.investment.home.fluctuation.controller.dto.response.fluctuationDTO;
import org.json.JSONObject;
import org.springframework.stereotype.Component;


@Component
public class FluctuationMapper {

    public fluctuationDTO toFluctuationDTO(final JSONObject record){

        return new fluctuationDTO(
                record.optString("hts_kor_isnm", ""),    // HTS 한글 종목명
                record.optInt("data_rank", 0),            // 데이터 순위
                record.optInt("stck_prpr", 0),            // 현재가
                record.optInt("prdy_vrss", 0),            // 전일 대비
                record.optString("prdy_vrss_sign", ""),   // 전일 대비 부호
                record.optDouble("prdy_ctrt", 0.0)
                );
    }

}
