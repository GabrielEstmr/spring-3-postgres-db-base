package com.challengers.accounts.containertests;

import com.challengers.accounts.gateways.postgres.repositories.AccountRepository;
import com.challengers.accounts.gateways.postgres.repositories.TransactionRepository;
import com.challengers.accounts.support.TestContainerSupport;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegisterTransactionContainerTest extends TestContainerSupport {

  private static final String MSG_ERROR_TRANSACTION_DOC_NOT_FOUND = "TransactionDocument not found";
  private static final String TRANSACTION_PATH = "/api/v1/transactions";
  private static final String ACCOUNT_PATH = "/api/v1/accounts";

  private static final String REQUEST_DOCUMENT_ID = "999999" + (int) (Math.random() * 50 + 1);
  private static final BigDecimal AMOUNT = BigDecimal.valueOf(123.45);

  @Autowired private TestRestTemplate restTemplate;
  @Autowired private TransactionRepository transactionRepository;
  @Autowired private AccountRepository accountRepository;

  @BeforeEach
  public void init() {
    transactionRepository.deleteAll();
    accountRepository.deleteAll();
  }

//  @Test
//  public void shouldPersistAndRetrieveTransactionByNegativeValueDueToWithdrawOpType() {
//    final var accountRequest = new CreateAccountRequest(REQUEST_DOCUMENT_ID);
//    final var accountResponse = createAccount(accountRequest);
//
//    final TransactionResponse transactionResponse =
//        createTransaction(
//            new CreateTransactionRequest(
//                accountResponse.getAccount_id(), OperationType.WITHDRAW.getId(), AMOUNT));
//    final TransactionDocument transactionDocument =
//        transactionRepository
//            .findById(transactionResponse.getId())
//            .orElseThrow(() -> new RuntimeException(MSG_ERROR_TRANSACTION_DOC_NOT_FOUND));
//
//    assertAll(
//        () -> assertEquals(1, transactionRepository.findAll().size()),
//        () -> assertEquals(accountResponse.getAccount_id(), transactionResponse.getAccount_id()),
//        () ->
//            assertEquals(AMOUNT.multiply(BigDecimal.valueOf(-1)), transactionResponse.getAmount()),
//        () ->
//            assertEquals(
//                OperationType.WITHDRAW.getId(), transactionResponse.getOperation_type_id()),
//        () -> assertEquals(accountResponse.getAccount_id(), transactionDocument.getAccountId()),
//        () ->
//            assertEquals(AMOUNT.multiply(BigDecimal.valueOf(-1)), transactionDocument.getAmount()),
//        () ->
//            assertEquals(OperationType.WITHDRAW.getId(), transactionDocument.getOperationTypeId()));
//  }
//
//  @Test
//  public void shouldPersistAndRetrieveTransactionByPositiveValueDueToPaymentOpType() {
//    final var accountRequest = new CreateAccountRequest(REQUEST_DOCUMENT_ID);
//    final var accountResponse = createAccount(accountRequest);
//
//    final TransactionResponse transactionResponse =
//        createTransaction(
//            new CreateTransactionRequest(
//                accountResponse.getAccount_id(), OperationType.PAYMENT.getId(), AMOUNT));
//    final TransactionDocument transactionDocument =
//        transactionRepository
//            .findById(transactionResponse.getId())
//            .orElseThrow(() -> new RuntimeException(MSG_ERROR_TRANSACTION_DOC_NOT_FOUND));
//
//    assertAll(
//        () -> assertEquals(1, transactionRepository.findAll().size()),
//        () -> assertEquals(accountResponse.getAccount_id(), transactionResponse.getAccount_id()),
//        () -> assertEquals(AMOUNT, transactionResponse.getAmount()),
//        () ->
//            assertEquals(OperationType.PAYMENT.getId(), transactionResponse.getOperation_type_id()),
//        () -> assertEquals(accountResponse.getAccount_id(), transactionDocument.getAccountId()),
//        () -> assertEquals(AMOUNT, transactionDocument.getAmount()),
//        () ->
//            assertEquals(OperationType.PAYMENT.getId(), transactionDocument.getOperationTypeId()));
//  }
//
//  private AccountResponse createAccount(final CreateAccountRequest createAccountRequest) {
//    final var headers = new HttpHeaders();
//    headers.setContentType(MediaType.APPLICATION_JSON);
//    final var requestJson = JsonUtils.convertObjectToJSON(createAccountRequest);
//    final var entity = new HttpEntity<>(requestJson, headers);
//    final ResponseEntity<AccountResponse> response =
//        restTemplate.postForEntity(ACCOUNT_PATH, entity, AccountResponse.class);
//    return response.getBody();
//  }
//
//  private TransactionResponse createTransaction(
//      final CreateTransactionRequest createTransactionRequest) {
//    final var headers = new HttpHeaders();
//    headers.setContentType(MediaType.APPLICATION_JSON);
//    final var requestJson = JsonUtils.convertObjectToJSON(createTransactionRequest);
//    final var entity = new HttpEntity<>(requestJson, headers);
//    final ResponseEntity<TransactionResponse> response =
//        restTemplate.postForEntity(TRANSACTION_PATH, entity, TransactionResponse.class);
//    return response.getBody();
//  }
}
