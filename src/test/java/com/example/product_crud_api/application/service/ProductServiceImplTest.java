package com.example.product_crud_api.application.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.example.product_crud_api.api.dto.ProductRequestDto;
import com.example.product_crud_api.api.dto.ProductResponseDto;
import com.example.product_crud_api.application.mapper.ProductMapper;
import com.example.product_crud_api.domain.entity.Product;
import com.example.product_crud_api.infrastructure.persistence.ProductRepository;
import com.example.product_crud_api.shared.dto.PageResponseDto;
import com.example.product_crud_api.shared.exception.ProductNotFoundException;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

	private static final Long PRODUCT_ID = 1L;
	private static final String PRODUCT_NAME = "Laptop";
	private static final String PRODUCT_DESCRIPTION = "Test product";
	private static final BigDecimal PRODUCT_PRICE = BigDecimal.valueOf(999.99);
	private static final Integer PRODUCT_STOCK = 10;
	private static final LocalDateTime FIXED_DATE_TIME = LocalDateTime.of(2025, 1, 15, 10, 30);

	@Mock
	private ProductRepository productRepository;

	@Mock
	private ProductMapper productMapper;

	@InjectMocks
	private ProductServiceImpl productService;

	@Test
	void create_shouldCreateProductSuccessfully() {
		ProductRequestDto requestDto = createProductRequestDto();
		Product product = createProduct();
		Product savedProduct = createProduct();
		ProductResponseDto responseDto = createProductResponseDto();

		when(productMapper.toEntity(requestDto)).thenReturn(product);
		when(productRepository.save(product)).thenReturn(savedProduct);
		when(productMapper.toResponseDto(savedProduct)).thenReturn(responseDto);

		ProductResponseDto result = productService.create(requestDto);

		assertProductResponse(result);
	}

	@Test
	void findById_shouldReturnProductWhenExists() {
		Product product = createProduct();
		ProductResponseDto responseDto = createProductResponseDto();

		when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(product));
		when(productMapper.toResponseDto(product)).thenReturn(responseDto);

		ProductResponseDto result = productService.findById(PRODUCT_ID);

		assertProductResponse(result);
	}

	@Test
	void findById_shouldThrowProductNotFoundExceptionWhenProductDoesNotExist() {
		when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.empty());

		ProductNotFoundException exception = assertThrows(
			ProductNotFoundException.class,
			() -> productService.findById(PRODUCT_ID)
		);

		assertEquals("Product not found with id: 1", exception.getMessage());
	}

	@Test
	void findAll_shouldReturnPagedProducts() {
		Product product = createProduct();
		ProductResponseDto responseDto = createProductResponseDto();
		Page<Product> productPage = new PageImpl<>(List.of(product), org.springframework.data.domain.PageRequest.of(0, 20), 1);

		when(productRepository.findAll(any(Pageable.class))).thenReturn(productPage);
		when(productMapper.toResponseDto(product)).thenReturn(responseDto);

		PageResponseDto<ProductResponseDto> result = productService.findAll(0, 20);

		assertEquals(0, result.getPage());
		assertEquals(20, result.getSize());
		assertEquals(1, result.getTotalElements());
		assertEquals(1, result.getTotalPages());
		assertTrue(result.isFirst());
		assertTrue(result.isLast());
		assertFalse(result.isHasNext());
		assertFalse(result.isHasPrevious());
		assertFalse(result.isEmpty());
		assertEquals(1, result.getContent().size());
		assertProductResponse(result.getContent().get(0));
	}

	@Test
	void update_shouldUpdateProductSuccessfully() {
		ProductRequestDto requestDto = createProductRequestDto();
		Product existingProduct = createProduct();
		Product updatedProduct = createProduct();
		ProductResponseDto responseDto = createProductResponseDto();

		when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(existingProduct));
		when(productRepository.save(existingProduct)).thenReturn(updatedProduct);
		when(productMapper.toResponseDto(updatedProduct)).thenReturn(responseDto);

		ProductResponseDto result = productService.update(PRODUCT_ID, requestDto);

		assertProductResponse(result);
		verify(productMapper).updateEntityFromRequest(requestDto, existingProduct);
	}

	@Test
	void update_shouldThrowProductNotFoundExceptionWhenProductDoesNotExist() {
		ProductRequestDto requestDto = createProductRequestDto();

		when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.empty());

		ProductNotFoundException exception = assertThrows(
			ProductNotFoundException.class,
			() -> productService.update(PRODUCT_ID, requestDto)
		);

		assertEquals("Product not found with id: 1", exception.getMessage());
		verify(productRepository, never()).save(any(Product.class));
	}

	@Test
	void delete_shouldDeleteProductSuccessfully() {
		Product product = createProduct();

		when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.of(product));

		productService.delete(PRODUCT_ID);

		verify(productRepository).delete(product);
	}

	@Test
	void delete_shouldThrowProductNotFoundExceptionWhenProductDoesNotExist() {
		when(productRepository.findById(PRODUCT_ID)).thenReturn(Optional.empty());

		ProductNotFoundException exception = assertThrows(
			ProductNotFoundException.class,
			() -> productService.delete(PRODUCT_ID)
		);

		assertEquals("Product not found with id: 1", exception.getMessage());
		verify(productRepository, never()).delete(any(Product.class));
	}

	private void assertProductResponse(ProductResponseDto responseDto) {
		assertEquals(PRODUCT_ID, responseDto.getId());
		assertEquals(PRODUCT_NAME, responseDto.getName());
		assertEquals(PRODUCT_DESCRIPTION, responseDto.getDescription());
		assertEquals(PRODUCT_PRICE, responseDto.getPrice());
		assertEquals(PRODUCT_STOCK, responseDto.getStock());
		assertEquals(FIXED_DATE_TIME, responseDto.getCreatedAt());
		assertEquals(FIXED_DATE_TIME, responseDto.getUpdatedAt());
	}

	private Product createProduct() {
		return Product.builder()
			.id(PRODUCT_ID)
			.name(PRODUCT_NAME)
			.description(PRODUCT_DESCRIPTION)
			.price(PRODUCT_PRICE)
			.stock(PRODUCT_STOCK)
			.createdAt(FIXED_DATE_TIME)
			.updatedAt(FIXED_DATE_TIME)
			.build();
	}

	private ProductRequestDto createProductRequestDto() {
		return ProductRequestDto.builder()
			.name(PRODUCT_NAME)
			.description(PRODUCT_DESCRIPTION)
			.price(PRODUCT_PRICE)
			.stock(PRODUCT_STOCK)
			.build();
	}

	private ProductResponseDto createProductResponseDto() {
		return ProductResponseDto.builder()
			.id(PRODUCT_ID)
			.name(PRODUCT_NAME)
			.description(PRODUCT_DESCRIPTION)
			.price(PRODUCT_PRICE)
			.stock(PRODUCT_STOCK)
			.createdAt(FIXED_DATE_TIME)
			.updatedAt(FIXED_DATE_TIME)
			.build();
	}
}
