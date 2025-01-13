package com.gamingtec.camel.commons.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class LoyaltyBalanceResponse extends BalanceResponsePart {
    private long loyaltyBalance;
    public static LoyaltyBalanceResponse dummy() {
        LoyaltyBalanceResponse response = new LoyaltyBalanceResponse();
        response.setLoyaltyBalance(1234567890L);
        return response;
    }
}
