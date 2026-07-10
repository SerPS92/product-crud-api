package com.example.product_crud_api.api;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.product_crud_api.api.dto.ProductRequestDto;
import com.example.product_crud_api.api.dto.ProductResponseDto;
import com.example.product_crud_api.shared.dto.ErrorResponseDto;
import com.example.product_crud_api.shared.dto.PageResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

@RequestMapping("/api/products")
@Tag(name = "Products", description = "Operations for managing products")
public interface ProductApi {

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Create product", description = "Creates a new product in the catalog.")
	@ApiResponse(responseCode = "201", description = "Product created successfully",
		content = @Content(schema = @Schema(implementation = ProductResponseDto.class)))
	@ApiResponse(responseCode = "400", description = "Invalid product data",
		content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
	@ApiResponse(responseCode = "500", description = "Internal server error",
		content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
	ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody ProductRequestDto requestDto);

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Get products", description = "Retrieves a paginated list of products.")
	@ApiResponse(responseCode = "200", description = "Products retrieved successfully",
		content = @Content(schema = @Schema(implementation = PageResponseDto.class)))
	@ApiResponse(responseCode = "400", description = "Invalid pagination parameters",
		content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
	@ApiResponse(responseCode = "500", description = "Internal server error",
		content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
	ResponseEntity<PageResponseDto<ProductResponseDto>> getProducts(
		@RequestParam(defaultValue = "0") @Min(value = 0, message = "Page must be zero or greater") int page,
		@RequestParam(defaultValue = "20") @Min(value = 1, message = "Size must be at least one") int size
	);

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Get product by id", description = "Retrieves a product by its identifier.")
	@ApiResponse(responseCode = "200", description = "Product found",
		content = @Content(schema = @Schema(implementation = ProductResponseDto.class)))
	@ApiResponse(responseCode = "404", description = "Product not found",
		content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
	@ApiResponse(responseCode = "500", description = "Internal server error",
		content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
	ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id);

	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@Operation(summary = "Update product", description = "Updates an existing product in the catalog.")
	@ApiResponse(responseCode = "200", description = "Product updated successfully",
		content = @Content(schema = @Schema(implementation = ProductResponseDto.class)))
	@ApiResponse(responseCode = "400", description = "Invalid product data",
		content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
	@ApiResponse(responseCode = "404", description = "Product not found",
		content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
	@ApiResponse(responseCode = "500", description = "Internal server error",
		content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
	ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequestDto requestDto);

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete product", description = "Deletes a product from the catalog.")
	@ApiResponse(responseCode = "204", description = "Product deleted successfully")
	@ApiResponse(responseCode = "404", description = "Product not found",
		content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
	@ApiResponse(responseCode = "500", description = "Internal server error",
		content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
	ResponseEntity<Void> deleteProduct(@PathVariable Long id);
}
