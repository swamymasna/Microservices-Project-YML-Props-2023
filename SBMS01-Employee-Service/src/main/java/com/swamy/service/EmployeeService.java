package com.swamy.service;

import java.util.List;

import com.swamy.dto.ApiResponse;
import com.swamy.dto.EmployeeDto;
import com.swamy.dto.EmployeeResponse;

import jakarta.servlet.http.HttpServletResponse;

public interface EmployeeService {

	public EmployeeDto saveEmployee(EmployeeDto employeeDto);

	public List<EmployeeDto> getAllEmployees();
	
	public EmployeeResponse getAllEmployees(Integer pageNo, Integer pageSize, String sortBy);

	public ApiResponse getEmployeeById(Integer employeeId);

	public EmployeeDto getOneEmployee(Integer employeeId);

	public EmployeeDto updateEmployeeById(Integer employeeId, EmployeeDto employeeDto);

	public void deleteEmployeeById(Integer employeeId);
	
	public void generateExcel(HttpServletResponse response) throws Exception;
}
