package com.example.product_crud_api.application.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.product_crud_api.api.dto.ProductRequestDto;
import com.example.product_crud_api.api.dto.ProductResponseDto;
import com.example.product_crud_api.application.mapper.ProductMapper;
import com.example.product_crud_api.domain.entity.Product;
import com.example.product_crud_api.infrastructure.persistence.ProductRepository;
import com.example.product_crud_api.shared.dto.PageResponseDto;
import com.example.product_crud_api.shared.exception.ProductNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

	private final ProductRepository productRepository;
	private final ProductMapper productMapper;

	@Override
	public ProductResponseDto create(ProductRequestDto requestDto) {
		Product product = productMapper.toEntity(requestDto);
		Product savedProduct = productRepository.save(product);
		return productMapper.toResponseDto(savedProduct);
	}

	@Override
	public PageResponseDto<ProductResponseDto> findAll(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Product> productPage = productRepository.findAll(pageable);
		List<ProductResponseDto> content = productPage.getContent()
			.stream()
			.map(productMapper::toResponseDto)
			.toList();

		return PageResponseDto.<ProductResponseDto>builder()
			.page(productPage.getNumber())
			.size(productPage.getSize())
			.totalElements(productPage.getTotalElements())
			.totalPages(productPage.getTotalPages())
			.first(productPage.isFirst())
			.last(productPage.isLast())
			.hasNext(productPage.hasNext())
			.hasPrevious(productPage.hasPrevious())
			.empty(productPage.isEmpty())
			.content(content)
			.build();
	}

	@Override
	public ProductResponseDto findById(Long id) {
		Product product = productRepository.findById(id)
			.orElseThrow(() -> new ProductNotFoundException(id));
		return productMapper.toResponseDto(product);
	}

	@Override
	public ProductResponseDto update(Long id, ProductRequestDto requestDto) {
		Product product = productRepository.findById(id)
			.orElseThrow(() -> new ProductNotFoundException(id));
		productMapper.updateEntityFromRequest(requestDto, product);
		Product updatedProduct = productRepository.save(product);
		return productMapper.toResponseDto(updatedProduct);
	}

	@Override
	public void delete(Long id) {
		Product product = productRepository.findById(id)
			.orElseThrow(() -> new ProductNotFoundException(id));
		productRepository.delete(product);
	}
}
