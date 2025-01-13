package com.gamingtec.camel.commons.model;

import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CashBalanceResponse extends BalanceResponsePart {
    private BigDecimal cashBalance;
    public static CashBalanceResponse dummy() {
        CashBalanceResponse response = new CashBalanceResponse();
        response.setCashBalance(BigDecimal.valueOf(123.5d));
        return response;
    }
}
