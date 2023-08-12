package com.swamy.dto;

import java.util.List;

import lombok.Data;

@Data
public class EmployeeResponse {

	private List<EmployeeDto> content;
	private Integer pageNo;
	private Integer pageSize;
	private String sortBy;
	private Long totalElements;
	private Integer totalPages;
	private boolean isFirst;
	private boolean isLast;
}
