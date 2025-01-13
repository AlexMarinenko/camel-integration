package com.gamingtec.camel.loyalty;

import com.gamingtec.camel.commons.model.LoyaltyBalanceResponse;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Service;

@Service
public class LoyaltyService extends RouteBuilder {

  @Override
  public void configure() throws Exception {
    from("kafka:balance-request?brokers=localhost:9092")
      .log("Received balance request: ${body}, ${headers}")
      .process(exchange -> exchange.getMessage().setBody(LoyaltyBalanceResponse.dummy()))
      .marshal().json()
      .to("kafka:balance-response?brokers=localhost:9092");;
  }
}
