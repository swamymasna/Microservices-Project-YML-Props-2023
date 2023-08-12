package com.swamy.dto;

import lombok.Data;

@Data
public class ApiResponse {

	private EmployeeDto employeeDto;
	
	private DepartmentDto departmentDto;
	
	private OrganizationDto organizationDto;
}
