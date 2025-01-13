package com.gamingtec.camel.wallet;

import static com.gamingtec.camel.wallet.Constants.CORRELATION_ID;

import com.gamingtec.camel.commons.model.BonusBalanceResponse;
import com.gamingtec.camel.commons.model.CashBalanceResponse;
import com.gamingtec.camel.commons.model.LoyaltyBalanceResponse;
import com.gamingtec.camel.wallet.dto.BalanceResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

public class BalanceAggregationStrategy implements AggregationStrategy {

  private final Map<String, BalanceResponse> aggregatedResponses = new ConcurrentHashMap<>();

  @Override
  public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
    String correlationId = getCorrelationId(newExchange);
    Object body = newExchange.getIn().getBody();
    handleBody(correlationId, body);
    return newExchange;
  }

  private void handleBody(String correlationId, Object body) {

    BalanceResponse response = aggregatedResponses.get(correlationId);

    if (response == null) {
      response = new BalanceResponse();
      aggregatedResponses.put(correlationId, response);
    }

    if (body instanceof BonusBalanceResponse) {
      response.setBonus(((BonusBalanceResponse) body).getBonusBalance());
    } else if (body instanceof CashBalanceResponse) {
      response.setCash(((CashBalanceResponse) body).getCashBalance());
    } else if (body instanceof LoyaltyBalanceResponse) {
      response.setLoyaltyPoints(((LoyaltyBalanceResponse) body).getLoyaltyBalance());
    }
  }

  @Override
  public void onCompletion(Exchange exchange) {
    String correlationId = getCorrelationId(exchange);
    BalanceResponse response = aggregatedResponses.get(correlationId);
    exchange.getMessage().setBody(response);
    releaseResources(exchange);
  }

  @Override
  public void timeout(Exchange exchange, int index, int total, long timeout) {
    releaseResources(exchange);
  }

  private void releaseResources(Exchange exchange) {
    String correlationId = getCorrelationId(exchange);
    aggregatedResponses.remove(correlationId);
  }

  private String getCorrelationId(Exchange exchange) {
    return new String((byte[]) exchange.getIn().getHeader(CORRELATION_ID), StandardCharsets.UTF_8);
  }
}
