package com.example.product_crud_api.shared.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.example.product_crud_api.shared.exception.ErrorCode;

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
public class ErrorResponseDto {

	private int status;
	private ErrorCode code;
	private String error;
	private String message;
	private String path;
	private LocalDateTime timestamp;
	private List<FieldErrorDto> fieldErrors;

}
