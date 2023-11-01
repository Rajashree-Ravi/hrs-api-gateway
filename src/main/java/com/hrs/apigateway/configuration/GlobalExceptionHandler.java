package com.hrs.apigateway.configuration;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hrs.apigateway.models.ApiErrorResponse;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalExceptionHandler {

	@ExceptionHandler(AuthenticationException.class)
	public final ResponseEntity<ApiErrorResponse> handleAuthenticationException(final Exception ex,
			final HttpServletRequest request) {

		List<String> errors = Collections.singletonList(ex.getMessage());

		ApiErrorResponse response = new ApiErrorResponse(HttpStatus.UNAUTHORIZED.value(),
				HttpStatus.UNAUTHORIZED.name(), request.getRequestURI(), request.getMethod(), LocalDateTime.now(),
				"authorization-error", errors);

		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ApiErrorResponse> handleGeneralExceptions(final Exception ex,
			final HttpServletRequest request) {

		List<String> errors = Collections.singletonList(ex.getMessage());

		ApiErrorResponse response = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.name(), request.getRequestURI(), request.getMethod(),
				LocalDateTime.now(), "internal-server-error", errors);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(RuntimeException.class)
	public final ResponseEntity<ApiErrorResponse> handleRuntimeExceptions(final RuntimeException ex,
			final HttpServletRequest request) {

		List<String> errors = Collections.singletonList(ex.getMessage());

		ApiErrorResponse response = new ApiErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.name(), request.getRequestURI(), request.getMethod(),
				LocalDateTime.now(), "internal-server-error", errors);

		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
