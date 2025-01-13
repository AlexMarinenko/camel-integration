package com.gamingtec.camel.wallet;

import static com.gamingtec.camel.wallet.Constants.CORRELATION_ID;

import com.gamingtec.camel.wallet.dto.BalanceRequest;
import com.gamingtec.camel.wallet.dto.BalanceResponse;
import java.math.BigDecimal;
import java.util.UUID;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Service;

@Service
public class WalletService extends RouteBuilder {

  @Override
  public void configure() {

    restConfiguration()
        .component("netty-http")
        .bindingMode(RestBindingMode.json)
        .dataFormatProperty("prettyPrint", "true")
        .contextPath("/api")
        .port(9999);

    rest("/balance")
        .produces("application/json")
        .consumes("application/json")
        .post()
        .type(BalanceRequest.class)
        .outType(BalanceResponse.class)
        .to("direct:balance");

    from("direct:balance")
        .setHeader(CORRELATION_ID, () -> UUID.randomUUID().toString())
        .marshal().json()
        .to("kafka:balance-request?brokers=localhost:9092")
        .pollEnrich("direct:balance-response", 1000);

  }
}
