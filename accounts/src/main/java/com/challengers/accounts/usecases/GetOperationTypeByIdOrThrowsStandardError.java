package com.challengers.accounts.usecases;

import com.challengers.accounts.domains.OperationType;
import com.challengers.accounts.domains.exceptions.ConflictException;
import com.challengers.accounts.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class GetOperationTypeByIdOrThrowsStandardError {

  private static final String MSG_INVALID_OPERATION_TYPE_ID =
      "there.is.no.operation.type.for.given.id";

  private final MessageUtils messageUtils;

  public OperationType execute(final Integer id) {
    return Arrays.stream(OperationType.values())
        .filter(type -> type.getId().equals(id))
        .findFirst()
        .orElseThrow(
            () ->
                new ConflictException(
                    messageUtils.getMessageDefaultLocale(MSG_INVALID_OPERATION_TYPE_ID)));
  }
}
