package com.challengers.accounts.gateways.input.api.v1;

import com.challengers.accounts.domains.OperationType;
import com.challengers.accounts.domains.Transaction;
import com.challengers.accounts.domains.exceptions.ResourceNotFoundException;
import com.challengers.accounts.gateways.input.api.linkbuilders.BuildLinks;
import com.challengers.accounts.gateways.input.api.v1.exceptions.CustomExceptionHandler;
import com.challengers.accounts.gateways.input.api.v1.resources.AccountResponse;
import com.challengers.accounts.gateways.input.api.v1.resources.CreateTransactionRequest;
import com.challengers.accounts.gateways.input.api.v1.resources.TransactionResponse;
import com.challengers.accounts.support.ControllerTestSupport;
import com.challengers.accounts.support.JsonUtils;
import com.challengers.accounts.templates.FixtureTemplate;
import com.challengers.accounts.usecases.CreateTransaction;
import com.challengers.accounts.usecases.FindTransaction;
import org.apache.commons.lang.StringUtils;

import org.junit.jupiter.api.Assertions;
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TransactionControllerTest extends ControllerTestSupport {

  private static final String BASE_PATH = "/api/v1/transactions";
  private static final String FIELD_NULL_ERROR_MSG = "Field can't be null.";
  private static final Long ACCOUNT_ID = 1234L;
  private static final Long TRANSACTION_ID = 12345L;
  private static final Integer OPERATION_TYPE_ID = OperationType.PAYMENT.getId();
  private static final BigDecimal AMOUNT = BigDecimal.valueOf(12.34);

  @InjectMocks private TransactionController provider;

  @Mock private CreateTransaction createTransaction;
  @Mock private FindTransaction findTransaction;
  @Mock private BuildLinks<TransactionResponse> transactionResponseLinkBuilder;

  @BeforeEach
  @Override
  public void init() {
    mvc =
        MockMvcBuilders.standaloneSetup(provider)
            .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
            .setControllerAdvice(new CustomExceptionHandler())
            .build();
  }

  @Test
  public void shouldPostAccountAndReturnResponseWith400DueToNullFields() throws Exception {
    final var createTransactionRequest = new CreateTransactionRequest(null, null, null);
    final var requestBody = JsonUtils.convertObjectToJSON(createTransactionRequest);

    final MvcResult mvcResult =
        mvc.perform(
                MockMvcRequestBuilders.post(BASE_PATH)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(requestBody))
            .andExpect(status().isBadRequest())
            .andReturn();

    assertErrorMsg(singletonList(FIELD_NULL_ERROR_MSG), mvcResult);
  }

  @Test
  public void shouldPostAccountAndReturnResponseWith400DueToNegativeOperationTypeId()
      throws Exception {
    final var createTransactionRequest = new CreateTransactionRequest(ACCOUNT_ID, 0, AMOUNT);
    final var requestBody = JsonUtils.convertObjectToJSON(createTransactionRequest);

    mvc.perform(
            MockMvcRequestBuilders.post(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody))
        .andExpect(status().isBadRequest())
        .andReturn();
  }

  @Test
  public void shouldPostAccountAndReturnResponseWith400DueToNegativeAmountValue() throws Exception {
    final var createTransactionRequest =
        new CreateTransactionRequest(
            ACCOUNT_ID, OPERATION_TYPE_ID, BigDecimal.TEN.multiply(BigDecimal.valueOf(-1)));
    final var requestBody = JsonUtils.convertObjectToJSON(createTransactionRequest);

    mvc.perform(
            MockMvcRequestBuilders.post(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody))
        .andExpect(status().isBadRequest())
        .andReturn();
  }

  @Test
  public void shouldPostAccountAndReturnResponseWith400DueToImproperAmountValue() throws Exception {
    final var createTransactionRequest =
        new CreateTransactionRequest(
            ACCOUNT_ID, OPERATION_TYPE_ID, BigDecimal.TEN.multiply(BigDecimal.valueOf(10.345)));
    final var requestBody = JsonUtils.convertObjectToJSON(createTransactionRequest);

    mvc.perform(
            MockMvcRequestBuilders.post(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody))
        .andExpect(status().isBadRequest())
        .andReturn();
  }

  @Test
  public void shouldPostAccountAndReturnResponseWith400DueToZeroAmountValue() throws Exception {
    final var createTransactionRequest =
        new CreateTransactionRequest(ACCOUNT_ID, OPERATION_TYPE_ID, BigDecimal.ZERO);
    final var requestBody = JsonUtils.convertObjectToJSON(createTransactionRequest);

    mvc.perform(
            MockMvcRequestBuilders.post(BASE_PATH)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody))
        .andExpect(status().isBadRequest())
        .andReturn();
  }

  @Test
  public void shouldPostAccountAndReturnResponseWith404DueToResourceNotFoundException()
      throws Exception {
    final var createTransactionRequest =
        new CreateTransactionRequest(ACCOUNT_ID, OPERATION_TYPE_ID, AMOUNT);
    final var requestBody = JsonUtils.convertObjectToJSON(createTransactionRequest);
    final var resourceNotFoundExceptionErrorMsg = "ResourceNotFoundExceptionErrorMsg";
    when(createTransaction.execute(any(), any(), any()))
        .thenThrow(new ResourceNotFoundException(resourceNotFoundExceptionErrorMsg));

    final MvcResult mvcResult =
        mvc.perform(
                MockMvcRequestBuilders.post(BASE_PATH)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(requestBody))
            .andExpect(status().isNotFound())
            .andReturn();

    assertErrorMsg(singletonList(resourceNotFoundExceptionErrorMsg), mvcResult);
  }

  @Test
  public void shouldPostAccountAndReturnResponseWith201() throws Exception {
    final var createTransactionRequest =
        new CreateTransactionRequest(ACCOUNT_ID, OPERATION_TYPE_ID, AMOUNT);
    final var requestBody = JsonUtils.convertObjectToJSON(createTransactionRequest);
    final var transaction = createObject(Transaction.class, FixtureTemplate.VALID);
    when(createTransaction.execute(any(), any(), any())).thenReturn(transaction);
    final Link link =
        linkTo(methodOn(AccountController.class).requestAccount(transaction.getId())).withSelfRel();
    when(transactionResponseLinkBuilder.build(new TransactionResponse(transaction)))
        .thenReturn(singletonList(link));

    final MvcResult mvcResult =
        mvc.perform(
                MockMvcRequestBuilders.post(BASE_PATH)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .content(requestBody))
            .andExpect(status().isCreated())
            .andReturn();

    assertSuccessPayload(transaction, mvcResult);
  }

  @Test
  public void shouldFindAccountAndReturnResponseWith404DueToNotFound() throws Exception {
    final var resourceNotFoundExceptionErrorMsg = "ResourceNotFoundExceptionErrorMsg";
    when(findTransaction.execute(any()))
        .thenThrow(new ResourceNotFoundException(resourceNotFoundExceptionErrorMsg));

    final MvcResult mvcResult =
        mvc.perform(
                MockMvcRequestBuilders.get(BASE_PATH + "/" + TRANSACTION_ID)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .param("transaction_id", TRANSACTION_ID.toString()))
            .andExpect(status().isNotFound())
            .andReturn();

    assertErrorMsg(singletonList(resourceNotFoundExceptionErrorMsg), mvcResult);
  }

  @Test
  public void shouldFindAccountAndReturnResponseWith200() throws Exception {
    final var transaction = createObject(Transaction.class, FixtureTemplate.VALID);
    when(findTransaction.execute(any())).thenReturn(transaction);

    final MvcResult mvcResult =
        mvc.perform(
                MockMvcRequestBuilders.get(BASE_PATH + "/" + TRANSACTION_ID)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .param("transaction_id", TRANSACTION_ID.toString()))
            .andExpect(status().isOk())
            .andReturn();

    assertSuccessPayload(transaction, mvcResult);
  }

  private void assertErrorMsg(final List<String> errorMessages, final MvcResult mvcResult)
      throws Exception {
    final String content = mvcResult.getResponse().getContentAsString();

    errorMessages.forEach(
        errorMessage -> Assertions.assertTrue(StringUtils.contains(content, errorMessage)));
  }

  private void assertSuccessPayload(final Transaction transaction, final MvcResult mvcResult)
      throws Exception {
    final String content = mvcResult.getResponse().getContentAsString();
    final var transactionResponse = JsonUtils.fromJson(content, TransactionResponse.class);
    assertAll(
        () -> assertEquals(transaction.getAccountId(), transactionResponse.getAccount_id()),
        () ->
            assertEquals(
                transaction.getOperationTypeId(), transactionResponse.getOperation_type_id()),
        () ->
            assertEquals(
                transaction.getAmount().setScale(2, RoundingMode.HALF_EVEN),
                transactionResponse.getAmount()));
  }
}
