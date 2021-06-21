package ru.n5y.homework.wk.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.n5y.homework.wk.service.NoSuchPlayerException;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
  private static final Logger log = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

  @ExceptionHandler(NoSuchPlayerException.class)
  protected ResponseEntity<Object> handleNoSuchPlayerException(NoSuchPlayerException ex, WebRequest request) {
    return handleExceptionInternal(ex, ex.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

  @Override
  protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
    if (status.is4xxClientError()) {
      log.warn("Wrong request received.", ex);
    } else if (status.is5xxServerError()) {
      log.error("Server error.", ex);
    }
    return super.handleExceptionInternal(ex, body, headers, status, request);
  }
}
