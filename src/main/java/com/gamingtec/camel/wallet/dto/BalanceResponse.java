package com.gamingtec.camel.wallet.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class BalanceResponse {
  private BigDecimal cash;
  private BigDecimal bonus;
  private long loyaltyPoints;
}
