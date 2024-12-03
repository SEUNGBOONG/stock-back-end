package com.example.investment_api.virtual.account.controller.dto;

import java.util.List;

public record UserStockDTO(
        List<String> stockNames
) {
}
