package com.gamingtec.camel.commons.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = CashBalanceResponse.class, name = "cash"),
    @JsonSubTypes.Type(value = BonusBalanceResponse.class, name = "bonus"),
    @JsonSubTypes.Type(value = LoyaltyBalanceResponse.class, name = "loyalty")
})
public class BalanceResponsePart {
}
