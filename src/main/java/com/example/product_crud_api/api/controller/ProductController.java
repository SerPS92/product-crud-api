package com.example.product_crud_api.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import com.example.product_crud_api.api.ProductApi;
import com.example.product_crud_api.api.dto.ProductRequestDto;
import com.example.product_crud_api.api.dto.ProductResponseDto;
import com.example.product_crud_api.application.service.ProductService;
import com.example.product_crud_api.shared.dto.PageResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@Validated
@RequiredArgsConstructor
public class ProductController implements ProductApi {

	private final ProductService productService;

	@Override
	public ResponseEntity<ProductResponseDto> createProduct(ProductRequestDto requestDto) {
		ProductResponseDto createdProduct = productService.create(requestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
	}

	@Override
	public ResponseEntity<PageResponseDto<ProductResponseDto>> getProducts(int page, int size) {
		return ResponseEntity.ok(productService.findAll(page, size));
	}

	@Override
	public ResponseEntity<ProductResponseDto> getProductById(Long id) {
		return ResponseEntity.ok(productService.findById(id));
	}

	@Override
	public ResponseEntity<ProductResponseDto> updateProduct(Long id, ProductRequestDto requestDto) {
		return ResponseEntity.ok(productService.update(id, requestDto));
	}

	@Override
	public ResponseEntity<Void> deleteProduct(Long id) {
		productService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
