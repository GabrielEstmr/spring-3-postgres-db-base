package com.challengers.accounts.usecases;

import com.challengers.accounts.domains.OperationType;
import com.challengers.accounts.domains.exceptions.ConflictException;
import com.challengers.accounts.support.TestSupport;
import com.challengers.accounts.utils.MessageUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GetOperationTypeByIdOrThrowsStandardErrorTest extends TestSupport {

  private static final String MSG_INVALID_OPERATION_TYPE_ID =
      "there.is.no.operation.type.for.given.id";
  private static final String MSG_LOCALE = "msg locale";

  @InjectMocks private GetOperationTypeByIdOrThrowsStandardError provider;
  @Mock private MessageUtils messageUtils;

  @Test
  public void shouldThrowsStandardErrorDueToInvalidOperationTypeId() {
    assertThrows(
        ConflictException.class,
        () -> {
          when(messageUtils.getMessageDefaultLocale(MSG_INVALID_OPERATION_TYPE_ID))
              .thenReturn(MSG_LOCALE);
          try {
            final var result = provider.execute(-1);
          } catch (final Exception e) {
            verify(messageUtils).getMessageDefaultLocale(MSG_INVALID_OPERATION_TYPE_ID);
            throw e;
          }
        });
  }

  @Test
  public void shouldGetOperationTypeById() {
    final var result = provider.execute(OperationType.PAYMENT.getId());
    assertEquals(OperationType.PAYMENT, result);
    verifyNoInteractions(messageUtils);
  }
}
