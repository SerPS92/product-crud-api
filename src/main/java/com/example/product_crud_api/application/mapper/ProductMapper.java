package com.example.product_crud_api.application.mapper;

import org.springframework.stereotype.Component;

import com.example.product_crud_api.api.dto.ProductRequestDto;
import com.example.product_crud_api.api.dto.ProductResponseDto;
import com.example.product_crud_api.domain.entity.Product;

@Component
public class ProductMapper {

	public Product toEntity(ProductRequestDto requestDto) {
		return Product.builder()
			.name(requestDto.getName())
			.description(requestDto.getDescription())
			.price(requestDto.getPrice())
			.stock(requestDto.getStock())
			.build();
	}

	public ProductResponseDto toResponseDto(Product product) {
		return ProductResponseDto.builder()
			.id(product.getId())
			.name(product.getName())
			.description(product.getDescription())
			.price(product.getPrice())
			.stock(product.getStock())
			.createdAt(product.getCreatedAt())
			.updatedAt(product.getUpdatedAt())
			.build();
	}

	public void updateEntityFromRequest(ProductRequestDto requestDto, Product product) {
		product.setName(requestDto.getName());
		product.setDescription(requestDto.getDescription());
		product.setPrice(requestDto.getPrice());
		product.setStock(requestDto.getStock());
	}
}
