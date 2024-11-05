package com.example.investment_api.member.exception;

import java.util.NoSuchElementException;

public class NotEnoughDeposit extends NoSuchElementException {
    public NotEnoughDeposit() {
        super("예수금이 부족합니다.");
    }
}
