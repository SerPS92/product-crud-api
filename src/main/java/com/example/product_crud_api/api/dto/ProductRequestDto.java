package com.example.product_crud_api.api.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDto {

	@NotBlank(message = "Product name is required")
	@Size(max = 150, message = "Product name must not exceed 150 characters")
	private String name;

	private String description;

	@NotNull(message = "Product price is required")
	@Positive(message = "Product price must be greater than zero")
	private BigDecimal price;

	@NotNull(message = "Product stock is required")
	@PositiveOrZero(message = "Product stock must be zero or greater")
	private Integer stock;

}
