package com.challengers.accounts.gateways.input.api.v1;

import com.challengers.accounts.gateways.input.api.constants.StatusCodeConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Caches v1")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/caches")
public class CacheController {

  @Operation(summary = "Invalidates all gateways cache")
  @ApiResponse(responseCode = "204", description = StatusCodeConstants.HTTP_204_MSG)
  @ApiResponse(responseCode = "500", description = StatusCodeConstants.HTTP_500_MSG)
  @PostMapping("/invalidate-gateway-cache")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  @CacheEvict(
      value = {
        "app-account-challenger:AccountDatabaseGatewayImpl:findById",
        "app-account-challenger:AccountDatabaseGatewayImpl:findByDocumentNumber",
        "app-account-challenger:TransactionDatabaseGatewayImpl:findById",
      },
      allEntries = true)
  public void invalidGatewayCache() {
    log.info("Cleaning all gateways cache");
  }
}
