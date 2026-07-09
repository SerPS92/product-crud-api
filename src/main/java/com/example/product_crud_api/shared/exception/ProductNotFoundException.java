package com.example.product_crud_api.shared.exception;

public class ProductNotFoundException extends RuntimeException {

	public ProductNotFoundException(Long id) {
		super("Product not found with id: " + id);
	}
}
