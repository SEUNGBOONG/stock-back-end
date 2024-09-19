package com.example.investment_api.search.financialRatio.domain;

import com.example.investment_api.search.financialRatio.controller.dto.FinancialRatioDTO;
import com.example.investment_api.search.financialRatio.infrastructure.FinancialRatioParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FinancialDomainTest {

    private FinancialRatioParser financialRatioParser;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        financialRatioParser = new FinancialRatioParser(objectMapper);
    }

    @Test
    void 제무재표_dto_반환값_테스트() throws IOException {
        String jsonResponse = new String(Files.readAllBytes(Paths.get("src/test/resources/financialRatioResponse.json")));

        FinancialRatioDTO dto = financialRatioParser.parseFinancialRatio(jsonResponse).get(0);

        Assertions.assertEquals("202406", dto.stockAccountingYearMonth());
        Assertions.assertEquals("100.0", dto.grossMarginRatio());
        Assertions.assertEquals("1.0", dto.businessProfitRate());
        Assertions.assertEquals("2.0", dto.netInterestRate());
        Assertions.assertEquals("3.0", dto.roeValue());
        Assertions.assertEquals("1000.00", dto.earningsPerShare());
        Assertions.assertEquals("2000", dto.salesPerShare());
        Assertions.assertEquals("4.00", dto.bookValuePerShare());
        Assertions.assertEquals("3000.0", dto.reserveRate());
        Assertions.assertEquals("4000.0", dto.liabilityRate());
    }
}
