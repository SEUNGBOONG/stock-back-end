package com.example.investment_api.virtual.account.mapper;

import com.example.investment_api.virtual.account.controller.dto.AccountStockData;
import com.example.investment_api.virtual.account.controller.dto.OrderData;
import com.example.investment_api.virtual.account.domain.MemberAccount;
import com.example.investment_api.virtual.account.domain.StockOrder;
import org.springframework.stereotype.Component;

@Component
public class OrderDataMapper {

    public OrderData mapToOrderDTO(StockOrder stockOrder, int totalQuantity) {
        int remainQuantity = totalQuantity - stockOrder.getQuantity();
        return new OrderData(
                stockOrder.getId(),
                stockOrder.getStockName(),
                remainQuantity,
                stockOrder.getQuantity(),
                stockOrder.getLimitPrice(),
                stockOrder.getIsBuyOrder()
        );
    }

    public AccountStockData mapToAccountStockData(MemberAccount account, int currentPrice, double prevChangeRate) {

        return new AccountStockData(
                account.getStockName(),
                account.getBuyPrice(),
                account.getStockCount(),
                currentPrice,
                prevChangeRate
        );
    }
}