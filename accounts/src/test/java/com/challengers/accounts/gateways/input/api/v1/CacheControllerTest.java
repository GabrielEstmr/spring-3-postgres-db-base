package com.challengers.accounts.gateways.input.api.v1;

import com.challengers.accounts.gateways.api.v1.CacheController;
import com.challengers.accounts.gateways.api.v1.exceptions.CustomExceptionHandler;
import com.challengers.accounts.support.ControllerTestSupport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CacheControllerTest extends ControllerTestSupport {

  private static final String BASE_PATH = "/api/v1/caches";
  private static final String POST_BASE_PATH = "/invalidate-gateway-cache";

  @InjectMocks private CacheController provider;

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
    mvc.perform(MockMvcRequestBuilders.post(BASE_PATH + POST_BASE_PATH))
        .andExpect(status().isNoContent())
        .andReturn();
  }
}
