package com.example.investment_api.virtual.account.controller.dto;

import org.springframework.web.bind.annotation.RequestParam;

public record OrderRequest( String stockName,
                            int limitPrice,
                            int quantity) {
}
