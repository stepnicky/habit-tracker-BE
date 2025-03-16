package com.habittracker.backend.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.habittracker.backend.exception.model.ApiError;

import java.time.LocalDateTime;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

  private static final String ERROR_LOG_MESSAGE = "An exception occurred, {}";

  //Spring framework exceptions handling
  @ExceptionHandler(InsufficientAuthenticationException.class)
  public ResponseEntity<ApiError> handleException(InsufficientAuthenticationException e,
                                                  HttpServletRequest request) {
    HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
    ApiError apiError = new ApiError(
            request.getRequestURI(),
            e.getMessage(),
            httpStatus.value(),
            LocalDateTime.now()
    );
    log.error(ERROR_LOG_MESSAGE, e.getMessage(), e);
    return new ResponseEntity<>(apiError, httpStatus);
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<ApiError> handleException(BadCredentialsException e,
                                                  HttpServletRequest request) {
    HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
    ApiError apiError = new ApiError(
            request.getRequestURI(),
            e.getMessage(),
            httpStatus.value(),
            LocalDateTime.now()
    );
    log.error(ERROR_LOG_MESSAGE, e.getMessage(), e);
    return new ResponseEntity<>(apiError, httpStatus);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiError> handleException(MethodArgumentNotValidException e,
                                                  HttpServletRequest request) {
    HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
    ApiError apiError = new ApiError(
            request.getRequestURI(),
            e.getMessage(),
            httpStatus.value(),
            LocalDateTime.now()
    );
    log.error(ERROR_LOG_MESSAGE, e.getMessage(), e);
    return new ResponseEntity<>(apiError, httpStatus);
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<ApiError> handleException(MissingServletRequestParameterException e,
                                                  HttpServletRequest request) {
    HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
    ApiError apiError = new ApiError(
            request.getRequestURI(),
            e.getMessage(),
            httpStatus.value(),
            LocalDateTime.now()
    );
    log.error(ERROR_LOG_MESSAGE, e.getMessage(), e);
    return new ResponseEntity<>(apiError, httpStatus);
  }

  //Application exceptions handling
  @ExceptionHandler(ApplicationException.class)
  public ResponseEntity<ApiError> handleException(ApplicationException e, HttpServletRequest request) {
    HttpStatus httpStatus = e.getResponseStatus();
    ApiError apiError = new ApiError(
            request.getRequestURI(),
            e.getMessage(),
            httpStatus.value(),
            LocalDateTime.now()
    );
    log.error(ERROR_LOG_MESSAGE, e.getMessage(), e);
    return new ResponseEntity<>(apiError, httpStatus);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ApiError> handleException(Exception e,
                                                  HttpServletRequest request) {
    HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    ApiError apiError = new ApiError(
            request.getRequestURI(),
            e.getMessage(),
            httpStatus.value(),
            LocalDateTime.now()
    );
    log.error(ERROR_LOG_MESSAGE, e.getMessage(), e);
    return new ResponseEntity<>(apiError, httpStatus);
  }
}
