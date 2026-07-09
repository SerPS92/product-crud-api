package com.example.product_crud_api.shared.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponseDto<T> {

	private int page;
	private int size;
	private long totalElements;
	private int totalPages;
	private boolean first;
	private boolean last;
	private boolean hasNext;
	private boolean hasPrevious;
	private boolean empty;
	private List<T> content;

}
