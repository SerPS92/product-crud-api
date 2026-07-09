package com.example.product_crud_api.shared.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorConstants {

	public static final String PRODUCT_NOT_FOUND_WITH_ID = "Product not found with id: %d";
	public static final String VALIDATION_FAILED = "Validation failed";
	public static final String INVALID_REQUEST_PARAMETERS = "Invalid request parameters";
	public static final String INVALID_REQUEST_BODY = "Invalid or missing request body";
	public static final String UNEXPECTED_ERROR = "An unexpected error occurred";
}
