package com.gamingtec.camel.wallet;

import static com.gamingtec.camel.wallet.Constants.CORRELATION_ID;

import com.gamingtec.camel.commons.model.BalanceResponsePart;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Service;

@Service
public class AggregationService extends RouteBuilder {
  @Override
  public void configure() throws Exception {
    from("kafka:balance-response?brokers=localhost:9092")
      .unmarshal().json(BalanceResponsePart.class)
      .log("Received balance response: ${body}, ${headers}")
      .aggregate(header(CORRELATION_ID), new BalanceAggregationStrategy())
      .completionSize(3)
      .completionTimeout(1000)
      .log("Aggregated balance response: ${body}, ${headers}")
      .to("direct:balance-response");
  }
}
