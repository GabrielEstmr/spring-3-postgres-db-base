package com.challengers.accounts.gateways.input.api.v1;

import com.challengers.accounts.domains.Account;
import com.challengers.accounts.domains.exceptions.ConflictException;
import com.challengers.accounts.domains.exceptions.ResourceNotFoundException;
import com.challengers.accounts.gateways.input.api.linkbuilders.BuildLinks;
import com.challengers.accounts.gateways.input.api.v1.exceptions.CustomExceptionHandler;
import com.challengers.accounts.gateways.input.api.v1.resources.AccountResponse;
import com.challengers.accounts.gateways.input.api.v1.resources.CreateAccountRequest;
import com.challengers.accounts.support.ControllerTestSupport;
import com.challengers.accounts.support.JsonUtils;
import com.challengers.accounts.templates.FixtureTemplate;
import com.challengers.accounts.usecases.CreateAccount;
import com.challengers.accounts.usecases.FindAccount;
import org.apache.commons.lang.StringUtils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.hateoas.Link;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountControllerTest extends ControllerTestSupport {

  private static final String BASE_PATH = "/api/v1/accounts";
  private static final String DOCUMENT_NUMBER = "1234123";
  private static final String ACCOUNT_ID = "123456";
  private static final String FIELD_EMPTY_ERROR_MSG = "Field can't be empty.";
  private static final String FIELD_NULL_ERROR_MSG = "Field can't be null.";

  @InjectMocks private AccountController provider;

  @Mock private CreateAccount createAccount;
  @Mock private FindAccount findAccount;
  @Mock private BuildLinks<AccountResponse> accountResponseLinkBuilder;

  @Override
  @BeforeEach
  public void init() {
    mvc =
        MockMvcBuilders.standaloneSetup(provider)
            .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
            .setControllerAdvice(new CustomExceptionHandler())
            .build();
  }

  @Test
  public void shouldPostAccountAndReturnResponseWith400DueToBlankAccountId() throws Exception {
    final var createAccountRequest = new CreateAccountRequest("");
    final String requestBody = JsonUtils.convertObjectToJSON(createAccountRequest);

    final MvcResult mvcResult =
        mvc.perform(
                MockMvcRequestBuilders.post(BASE_PATH)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(requestBody))
            .andExpect(status().isBadRequest())
            .andReturn();

    assertErrorMsg(singletonList(FIELD_EMPTY_ERROR_MSG), mvcResult);
  }

  @Test
  public void shouldPostAccountAndReturnResponseWith400DueToNullAccountId() throws Exception {
    final var createAccountRequest = new CreateAccountRequest(null);
    final String requestBody = JsonUtils.convertObjectToJSON(createAccountRequest);

    final MvcResult mvcResult =
        mvc.perform(
                MockMvcRequestBuilders.post(BASE_PATH)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(requestBody))
            .andExpect(status().isBadRequest())
            .andReturn();

    assertErrorMsg(Arrays.asList(FIELD_EMPTY_ERROR_MSG, FIELD_NULL_ERROR_MSG), mvcResult);
  }

  @Test
  public void shouldPostAccountAndReturnResponseWith400DueToAccountIdToLong() throws Exception {
    final var createAccountRequest = new CreateAccountRequest("123456789112");
    final String requestBody = JsonUtils.convertObjectToJSON(createAccountRequest);

    mvc.perform(
            MockMvcRequestBuilders.post(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody))
        .andExpect(status().isBadRequest())
        .andReturn();
  }

  @Test
  public void shouldPostAccountAndReturnResponseWith409DueToInvalidDocumentId() throws Exception {
    final var createAccountRequest = new CreateAccountRequest(DOCUMENT_NUMBER);
    final String requestBody = JsonUtils.convertObjectToJSON(createAccountRequest);
    final var conflictErrorMsg = "ConflictErrorMessage";

    when(createAccount.execute(DOCUMENT_NUMBER)).thenThrow(new ConflictException(conflictErrorMsg));

    final MvcResult mvcResult =
        mvc.perform(
                MockMvcRequestBuilders.post(BASE_PATH)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(requestBody))
            .andExpect(status().isConflict())
            .andReturn();

    assertErrorMsg(singletonList(conflictErrorMsg), mvcResult);
  }

  @Test
  public void shouldPostAccountAndReturnResponseWith201() throws Exception {
    final var account = createObject(Account.class, FixtureTemplate.VALID);
    final var createAccountRequest = new CreateAccountRequest(DOCUMENT_NUMBER);

    when(createAccount.execute(DOCUMENT_NUMBER)).thenReturn(account);
    final Link link =
        linkTo(methodOn(AccountController.class).requestAccount(account.getId())).withSelfRel();
    when(accountResponseLinkBuilder.build(new AccountResponse(account)))
        .thenReturn(singletonList(link));

    final String json = JsonUtils.convertObjectToJSON(createAccountRequest);

    final MvcResult mvcResult =
        mvc.perform(
                MockMvcRequestBuilders.post(BASE_PATH)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(json))
            .andExpect(status().isCreated())
            .andReturn();

    assertSuccessPayload(account, mvcResult);
  }

  @Test
  public void shouldGetAccountAndReturnResponseWith200() throws Exception {
    final var account = createObject(Account.class, FixtureTemplate.VALID);
    when(findAccount.execute(Long.valueOf(ACCOUNT_ID))).thenReturn(account);
    final Link link =
        linkTo(methodOn(AccountController.class).requestAccount(account.getId())).withSelfRel();
    when(accountResponseLinkBuilder.build(new AccountResponse(account)))
        .thenReturn(singletonList(link));

    final MvcResult mvcResult =
        mvc.perform(
                MockMvcRequestBuilders.get(BASE_PATH + "/" + ACCOUNT_ID)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .param("account_id", ACCOUNT_ID))
            .andExpect(status().isOk())
            .andReturn();

    assertSuccessPayload(account, mvcResult);
  }

  @Test
  public void shouldGetAccountAndReturnResponseWith404StatusCode() throws Exception {
    final var resourceNotFoundExceptionErrorMsg = "ResourceNotFoundExceptionErrorMsg";
    when(findAccount.execute(Long.valueOf(ACCOUNT_ID)))
        .thenThrow(new ResourceNotFoundException(resourceNotFoundExceptionErrorMsg));

    final MvcResult mvcResult =
        mvc.perform(
                MockMvcRequestBuilders.get(BASE_PATH + "/" + ACCOUNT_ID)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .param("account_id", ACCOUNT_ID))
            .andExpect(status().isNotFound())
            .andReturn();

    assertErrorMsg(singletonList(resourceNotFoundExceptionErrorMsg), mvcResult);
  }

  private void assertErrorMsg(final List<String> errorMessages, final MvcResult mvcResult)
      throws Exception {
    final String content = mvcResult.getResponse().getContentAsString();
    errorMessages.forEach(errorMessage -> assertTrue(StringUtils.contains(content, errorMessage)));
  }

  private void assertSuccessPayload(final Account account, final MvcResult mvcResult)
      throws Exception {
    final String content = mvcResult.getResponse().getContentAsString();
    final var createResponse = JsonUtils.fromJson(content, AccountResponse.class);
    assertAll(
        () -> assertEquals(account.getId(), createResponse.getAccount_id()),
        () -> assertEquals(account.getDocumentNumber(), createResponse.getDocument_number()));
  }
}
