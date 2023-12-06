package com.challengers.accounts.gateways.api.v1;

import com.challengers.accounts.gateways.api.constants.StatusCodeConstants;
import com.challengers.accounts.usecases.InvalidAllFeaturesCache;
import com.challengers.accounts.usecases.InvalidAllGatewayCaches;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

  private final InvalidAllGatewayCaches invalidAllGatewayCaches;
  private final InvalidAllFeaturesCache invalidAllFeaturesCache;

  @Operation(summary = "Invalidates all gateways cache")
  @ApiResponse(responseCode = "204", description = StatusCodeConstants.HTTP_204_MSG)
  @ApiResponse(responseCode = "500", description = StatusCodeConstants.HTTP_500_MSG)
  @PostMapping("/invalidate-gateway-cache")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void invalidAllGatewaysCache() {
    log.info("Start: Cleaning all gateways cache");
    invalidAllGatewayCaches.execute();
  }

  @Operation(summary = "Invalidates all features cache")
  @ApiResponse(responseCode = "204", description = StatusCodeConstants.HTTP_204_MSG)
  @ApiResponse(responseCode = "500", description = StatusCodeConstants.HTTP_500_MSG)
  @PostMapping("/invalidate-features-cache")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void invalidAllFeaturesCache() {
    log.info("Start: Cleaning all features cache");
    invalidAllFeaturesCache.execute();
  }
}
