package com.challengers.accounts.gateways.api.v1;

import com.challengers.accounts.gateways.api.constants.StatusCodeConstants;
import com.challengers.accounts.gateways.api.linkbuilders.BuildLinks;
import com.challengers.accounts.gateways.api.v1.resources.AccountResponse;
import com.challengers.accounts.gateways.api.v1.resources.CreateAccountRequest;
import com.challengers.accounts.usecases.CreateAccount;
import com.challengers.accounts.usecases.FindAccount;
import com.challengers.accounts.usecases.TestVirtualThreadsCallExplicit;
import com.challengers.accounts.usecases.TestVirtualThreadsUsingAsyncAnnotation;
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
@RequestMapping("/api/v1/accounts")
@Tag(name = "Accounts v1")
@RequiredArgsConstructor
public class AccountController {

  private static final String GET_BY_ID_PATH = "/{account_id}";

  private final CreateAccount createAccount;
  private final FindAccount findAccount;
  private final BuildLinks<AccountResponse> accountResponseLinkBuilder;
  private final TestVirtualThreadsCallExplicit testVirtualThreadsCallExplicit;
  private final TestVirtualThreadsUsingAsyncAnnotation testVirtualThreadsUsingAsyncAnnotation;

  @Operation(summary = "Creates a new account")
  @ApiResponse(responseCode = "201", description = StatusCodeConstants.HTTP_201_MSG)
  @ApiResponse(responseCode = "400", description = StatusCodeConstants.HTTP_400_MSG)
  @ApiResponse(responseCode = "409", description = StatusCodeConstants.HTTP_409_MSG)
  @ApiResponse(responseCode = "500", description = StatusCodeConstants.HTTP_500_MSG)
  @PostMapping(
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<AccountResponse> createAccount(
      @Valid @RequestBody final CreateAccountRequest createAccountRequest)
      throws URISyntaxException {
    final var accountResponse =
        new AccountResponse(createAccount.execute(createAccountRequest.getDocument_number()));
    accountResponse.add(accountResponseLinkBuilder.build(accountResponse));
    return ResponseEntity.created(
            new URI(accountResponse.getRequiredLink(IanaLinkRelations.SELF).getHref()))
        .body(accountResponse);
  }

  @Operation(summary = "Requests an account by its id")
  @ApiResponse(responseCode = "200", description = StatusCodeConstants.HTTP_200_MSG)
  @ApiResponse(responseCode = "404", description = StatusCodeConstants.HTTP_404_MSG)
  @ApiResponse(responseCode = "500", description = StatusCodeConstants.HTTP_500_MSG)
  @GetMapping(value = GET_BY_ID_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  public ResponseEntity<AccountResponse> requestAccount(@PathVariable final Long account_id) {
    final var accountResponse = new AccountResponse(findAccount.execute(account_id));
    accountResponse.add(accountResponseLinkBuilder.build(accountResponse));
    return ResponseEntity.ok().body(accountResponse);
  }
}
