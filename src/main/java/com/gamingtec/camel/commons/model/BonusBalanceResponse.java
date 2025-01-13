package com.gamingtec.camel.commons.model;

import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BonusBalanceResponse extends BalanceResponsePart {
    private BigDecimal bonusBalance;

    public static BonusBalanceResponse dummy() {
        BonusBalanceResponse response = new BonusBalanceResponse();
        response.setBonusBalance(BigDecimal.valueOf(100));
        return response;
    }

}
