package com.swamy.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DepartmentDto {

	private Integer departmentId;

	@NotEmpty(message = "Department Name should not be Null or Empty")
	@Size(min = 2, message = "Department Name must have atleast 2 charecters")
	private String departmentName;

	@NotEmpty(message = "Department Description should not be Null or Empty")
	@Size(min = 2, message = "Department Description must have atleast 2 charecters")
	private String departmentDescription;

	@NotEmpty(message = "Department Code should not be Null or Empty")
	@Size(min = 2, message = "Department Code must have atleast 2 charecters")
	private String departmentCode;
}
