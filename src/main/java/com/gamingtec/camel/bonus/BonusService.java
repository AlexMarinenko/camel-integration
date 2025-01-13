package com.gamingtec.camel.bonus;

import com.gamingtec.camel.commons.model.BonusBalanceResponse;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Service;

@Service
public class BonusService extends RouteBuilder {
  @Override
  public void configure() throws Exception {
    from("kafka:balance-request?brokers=localhost:9092")
      .log("Received balance request: ${body}, ${headers}")
      .process(exchange -> exchange.getMessage().setBody(BonusBalanceResponse.dummy()))
      .marshal().json()
      .to("kafka:balance-response?brokers=localhost:9092");
  }
}
