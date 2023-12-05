package com.challengers.accounts.gateways.input.api.v1.exceptions;

import com.challengers.accounts.domains.exceptions.ConflictException;
import com.challengers.accounts.domains.exceptions.InternalServerErrorException;
import com.challengers.accounts.domains.exceptions.ResourceNotFoundException;
import io.undertow.util.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static java.lang.String.format;
import static java.util.stream.Collectors.toList;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionHandler {

  public static final String FIELD_ERROR_PATTERN = "%s:%s";

  @ExceptionHandler({BadRequestException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorMessage handleBadRequestException(final BadRequestException ex) {
    log.error(ex.getLocalizedMessage(), ex);
    return new ErrorMessage(HttpStatus.BAD_REQUEST.getReasonPhrase(), ex.getMessage());
  }

  @ExceptionHandler({ResourceNotFoundException.class})
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ResponseBody
  public ErrorMessage handleResourceNotFoundException(final ResourceNotFoundException ex) {
    log.error(ex.getLocalizedMessage(), ex);
    return new ErrorMessage(HttpStatus.NOT_FOUND.getReasonPhrase(), ex.getMessage());
  }

  @ExceptionHandler({ConflictException.class})
  @ResponseStatus(HttpStatus.CONFLICT)
  @ResponseBody
  public ErrorMessage handleConflictException(final ConflictException ex) {
    log.error(ex.getLocalizedMessage(), ex);
    return new ErrorMessage(HttpStatus.CONFLICT.getReasonPhrase(), ex.getMessage());
  }

  @ExceptionHandler({InternalServerErrorException.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ResponseBody
  public ErrorMessage handleInternalServerErrorException(final InternalServerErrorException ex) {
    log.error(ex.getLocalizedMessage(), ex);
    return new ErrorMessage(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), ex.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ErrorMessage handleMethodArgumentNotValidException(
      final MethodArgumentNotValidException ex) {
    log.error(ex.getLocalizedMessage(), ex);
    final BindingResult bindingResult = ex.getBindingResult();
    final List<FieldError> fieldErrors = bindingResult.getFieldErrors();
    final List<String> messages = processFieldErrors(fieldErrors);
    return new ErrorMessage(HttpStatus.BAD_GATEWAY.getReasonPhrase(), messages);
  }

  private List<String> processFieldErrors(final List<FieldError> fieldErrors) {
    return fieldErrors.stream()
        .map(
            errorByField ->
                format(
                    FIELD_ERROR_PATTERN, errorByField.getField(), errorByField.getDefaultMessage()))
        .collect(toList());
  }
}
