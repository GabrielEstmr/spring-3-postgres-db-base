package com.challengers.accounts.containertests;

import com.challengers.accounts.gateways.postgres.repositories.AccountRepository;
import com.challengers.accounts.gateways.postgres.repositories.TransactionRepository;
import com.challengers.accounts.support.TestContainerSupport;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegisterAccountContainerTest extends TestContainerSupport {

  private static final String MSG_ERROR_ACCOUNT_DOC_NOT_FOUND = "AccountCacheDocument not found";
  private static final String ACCOUNT_PATH = "/api/v1/accounts";

  private static final String REQUEST_DOCUMENT_ID = "999999" + (int) (Math.random() * 50 + 1);

  @Autowired private TestRestTemplate restTemplate;
  @Autowired private TransactionRepository transactionRepository;
  @Autowired private AccountRepository accountRepository;

  @BeforeEach
  public void init() {
    transactionRepository.deleteAll();
    accountRepository.deleteAll();
  }

//  @Test
//  public void shouldPersistAndRetrieveAccount() {
//    final var accountRequest = new CreateAccountRequest(REQUEST_DOCUMENT_ID);
//    final AccountResponse accountResponse = createAccount(accountRequest);
//    final AccountDocument accountDocument =
//        accountRepository
//            .findById(accountResponse.getAccount_id())
//            .orElseThrow(() -> new RuntimeException(MSG_ERROR_ACCOUNT_DOC_NOT_FOUND));
//
//    assertEquals(1, accountRepository.findAll().size());
//
//    assertEquals(accountRequest.getDocument_number(), accountResponse.getDocument_number());
//    assertEquals(accountRequest.getDocument_number(), accountDocument.getDocumentNumber());
//
//    assertEquals(accountResponse.getDocument_number(), accountDocument.getDocumentNumber());
//    assertEquals(accountResponse.getAccount_id(), accountDocument.getId());
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
}
