package com.example.product_crud_api.shared.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.product_crud_api.shared.dto.ErrorResponseDto;
import com.example.product_crud_api.shared.dto.FieldErrorDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<ErrorResponseDto> handleProductNotFound(
		ProductNotFoundException exception,
		HttpServletRequest request
	) {
		HttpStatus status = HttpStatus.NOT_FOUND;
		ErrorResponseDto response = buildErrorResponse(
			status,
			ErrorCode.NOT_FOUND,
			exception.getMessage(),
			request.getRequestURI(),
			null
		);
		return ResponseEntity.status(status).body(response);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValid(
		MethodArgumentNotValidException exception,
		HttpServletRequest request
	) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		List<FieldErrorDto> fieldErrors = exception.getBindingResult()
			.getFieldErrors()
			.stream()
			.map(fieldError -> FieldErrorDto.builder()
				.field(fieldError.getField())
				.message(fieldError.getDefaultMessage())
				.build())
			.toList();

		ErrorResponseDto response = buildErrorResponse(
			status,
			ErrorCode.VALIDATION_ERROR,
			ErrorConstants.VALIDATION_FAILED,
			request.getRequestURI(),
			fieldErrors
		);
		return ResponseEntity.status(status).body(response);
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorResponseDto> handleConstraintViolation(
		ConstraintViolationException exception,
		HttpServletRequest request
	) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		List<FieldErrorDto> fieldErrors = exception.getConstraintViolations()
			.stream()
			.map(violation -> FieldErrorDto.builder()
				.field(violation.getPropertyPath().toString())
				.message(violation.getMessage())
				.build())
			.toList();

		ErrorResponseDto response = buildErrorResponse(
			status,
			ErrorCode.INVALID_REQUEST_PARAMETERS,
			ErrorConstants.INVALID_REQUEST_PARAMETERS,
			request.getRequestURI(),
			fieldErrors
		);
		return ResponseEntity.status(status).body(response);
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorResponseDto> handleHttpMessageNotReadable(
		HttpMessageNotReadableException exception,
		HttpServletRequest request
	) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		ErrorResponseDto response = buildErrorResponse(
			status,
			ErrorCode.INVALID_REQUEST_BODY,
			ErrorConstants.INVALID_REQUEST_BODY,
			request.getRequestURI(),
			null
		);
		return ResponseEntity.status(status).body(response);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponseDto> handleGenericException(
		Exception exception,
		HttpServletRequest request
	) {
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		ErrorResponseDto response = buildErrorResponse(
			status,
			ErrorCode.INTERNAL_ERROR,
			ErrorConstants.UNEXPECTED_ERROR,
			request.getRequestURI(),
			null
		);
		return ResponseEntity.status(status).body(response);
	}

	private ErrorResponseDto buildErrorResponse(
		HttpStatus status,
		ErrorCode code,
		String message,
		String path,
		List<FieldErrorDto> fieldErrors
	) {
		return ErrorResponseDto.builder()
			.status(status.value())
			.code(code)
			.error(status.getReasonPhrase())
			.message(message)
			.path(path)
			.timestamp(LocalDateTime.now())
			.fieldErrors(fieldErrors)
			.build();
	}
}
