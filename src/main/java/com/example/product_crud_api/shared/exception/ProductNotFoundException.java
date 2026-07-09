package com.example.product_crud_api.shared.exception;

public class ProductNotFoundException extends RuntimeException {

	public ProductNotFoundException(Long id) {
		super(String.format(ErrorConstants.PRODUCT_NOT_FOUND_WITH_ID, id));
	}
}
