package com.challengers.accounts.gateways.input.api.v1;

import com.challengers.accounts.gateways.input.api.constants.StatusCodeConstants;
import com.challengers.accounts.gateways.input.api.linkbuilders.BuildLinks;
import com.challengers.accounts.gateways.input.api.v1.resources.CreateTransactionRequest;
import com.challengers.accounts.gateways.input.api.v1.resources.TransactionResponse;
import com.challengers.accounts.usecases.CreateTransaction;
import com.challengers.accounts.usecases.FindTransaction;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@Validated
@RestController
@RequestMapping("/api/v1/transactions")
@Tag(name = "Transactions v1")
@RequiredArgsConstructor
public class TransactionController {

  private static final String GET_BY_ID_PATH = "/{transaction_id}";

  private final CreateTransaction createTransaction;
  private final FindTransaction findTransaction;
  private final BuildLinks<TransactionResponse> transactionResponseLinkBuilder;

  @Operation(summary = "Creates a new transaction")
  @ApiResponse(responseCode = "201", description = StatusCodeConstants.HTTP_201_MSG)
  @ApiResponse(responseCode = "400", description = StatusCodeConstants.HTTP_400_MSG)
  @ApiResponse(responseCode = "404", description = StatusCodeConstants.HTTP_404_MSG)
  @ApiResponse(responseCode = "500", description = StatusCodeConstants.HTTP_500_MSG)
  @PostMapping(
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<TransactionResponse> createTransaction(
      @Valid @RequestBody final CreateTransactionRequest createTransactionRequest)
      throws URISyntaxException {

    final var transactionResponse =
        new TransactionResponse(
            createTransaction.execute(
                createTransactionRequest.getAccount_id(),
                createTransactionRequest.getOperation_type_id(),
                createTransactionRequest.getAmount()));
    transactionResponse.add(transactionResponseLinkBuilder.build(transactionResponse));
    return ResponseEntity.created(
            new URI(transactionResponse.getRequiredLink(IanaLinkRelations.SELF).getHref()))
        .body(transactionResponse);
  }

  @Operation(summary = "Requests a transaction by its id")
  @ApiResponse(responseCode = "200", description = StatusCodeConstants.HTTP_200_MSG)
  @ApiResponse(responseCode = "404", description = StatusCodeConstants.HTTP_404_MSG)
  @ApiResponse(responseCode = "500", description = StatusCodeConstants.HTTP_500_MSG)
  @GetMapping(value = GET_BY_ID_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<TransactionResponse> requestTransaction(
      @PathVariable final Long transaction_id) {
    final var transactionResponse =
        new TransactionResponse(findTransaction.execute(transaction_id));
    transactionResponse.add(transactionResponseLinkBuilder.build(transactionResponse));
    return ResponseEntity.ok(transactionResponse);
  }
}
