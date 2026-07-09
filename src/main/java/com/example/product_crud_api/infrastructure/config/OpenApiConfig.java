package com.example.product_crud_api.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI productCrudOpenAPI() {
		return new OpenAPI()
			.info(new Info()
				.title("Product CRUD API")
				.description("REST API for product catalog management.")
				.version("1.0.0")
				.contact(new Contact()
					.name("Sergio")
					.url("https://github.com/SerPS92/product-crud-api")));
	}
}
