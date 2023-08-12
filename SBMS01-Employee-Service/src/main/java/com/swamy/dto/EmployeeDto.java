package com.swamy.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {

	private Integer employeeId;

	@NotEmpty(message = "Employee Name Should Not be Null or Empty")
	@Size(min = 2, message = "Employee Name Should have Atleast 2 Charecters")
	private String employeeName;

	@NotNull(message = "Employee Salary Should Not be Null")
	private Double employeeSalary;

	@NotEmpty(message = "Employee Address Should Not be Null or Empty")
	@Size(min = 2, message = "Employee Address Should have Atleast 2 Charecters")
	private String employeeAddress;
	
	@NotEmpty(message = "Department Code Should Not be Null or Empty")
	@Size(min = 2, message = "Department Code Should have Atleast 2 Charecters")
	private String departmentCode;
	
	@NotEmpty(message = "Organization Code Should Not be Null or Empty")
	@Size(min = 2, message = "Organization Code Should have Atleast 2 Charecters")
	private String organizationCode;
}
