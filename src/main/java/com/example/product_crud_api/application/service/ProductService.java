package com.example.product_crud_api.application.service;

import com.example.product_crud_api.api.dto.ProductRequestDto;
import com.example.product_crud_api.api.dto.ProductResponseDto;
import com.example.product_crud_api.shared.dto.PageResponseDto;

public interface ProductService {

	ProductResponseDto create(ProductRequestDto requestDto);

	PageResponseDto<ProductResponseDto> findAll(int page, int size);

	ProductResponseDto findById(Long id);

	ProductResponseDto update(Long id, ProductRequestDto requestDto);

	void delete(Long id);
}
