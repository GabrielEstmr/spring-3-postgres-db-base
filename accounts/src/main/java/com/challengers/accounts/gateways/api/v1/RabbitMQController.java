package com.challengers.accounts.gateways.api.v1;

import com.challengers.accounts.domains.Transaction;
import com.challengers.accounts.gateways.AmqpEventSender;
import com.challengers.accounts.gateways.api.constants.StatusCodeConstants;
import com.challengers.accounts.gateways.api.v1.resources.CreateTransactionRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/v1/rabbitmq")
@Tag(name = "RabbitMQ v1")
@RequiredArgsConstructor
public class RabbitMQController {

  private final AmqpEventSender amqpEventSender;

  @Operation(summary = "Creates a new rabbitmq event")
  @ApiResponse(responseCode = "201", description = StatusCodeConstants.HTTP_204_MSG)
  @ApiResponse(responseCode = "400", description = StatusCodeConstants.HTTP_400_MSG)
  @ApiResponse(responseCode = "500", description = StatusCodeConstants.HTTP_500_MSG)
  @PostMapping(
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public void createAccount(
      @Valid @RequestBody final CreateTransactionRequest createTransactionRequest)
      throws URISyntaxException {
    final var transaction =
        Transaction.builder().id(createTransactionRequest.getAccount_id()).build();
    amqpEventSender.send(transaction);
  }
}
