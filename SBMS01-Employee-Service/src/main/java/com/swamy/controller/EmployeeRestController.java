package com.swamy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.swamy.dto.ApiResponse;
import com.swamy.dto.EmployeeDto;
import com.swamy.dto.EmployeeResponse;
import com.swamy.service.EmployeeService;
import com.swamy.utils.AppConstants;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/employees")
public class EmployeeRestController {

	@Autowired
	private EmployeeService employeeService;

	@PostMapping("/save")
	public ResponseEntity<EmployeeDto> saveEmployee(@RequestBody @Valid EmployeeDto employeeDto) {
		return new ResponseEntity<EmployeeDto>(employeeService.saveEmployee(employeeDto), HttpStatus.CREATED);
	}

	@GetMapping("/list")
	public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
		return new ResponseEntity<>(employeeService.getAllEmployees(), HttpStatus.OK);
	}

	@GetMapping("/employee/{emp-id}")
	public ResponseEntity<ApiResponse> getEmployeeById(@PathVariable(value = "emp-id") Integer empId) {
		return new ResponseEntity<>(employeeService.getEmployeeById(empId), HttpStatus.OK);
	}

	@PutMapping("/update/{emp-id}")
	public ResponseEntity<EmployeeDto> updateEmployeeById(@PathVariable(value = "emp-id") Integer empId,
			@RequestBody @Valid EmployeeDto employeeDto) {
		return new ResponseEntity<>(employeeService.updateEmployeeById(empId, employeeDto), HttpStatus.OK);
	}

	@DeleteMapping("/delete/{emp-id}")
	public ResponseEntity<String> deleteEmployeeById(@PathVariable(value = "emp-id") Integer empId) {
		employeeService.deleteEmployeeById(empId);
		return new ResponseEntity<>(String.format(AppConstants.EMPLOYEE_DELETION_SUCCESS, empId), HttpStatus.OK);
	}

	@GetMapping("/all")
	public ResponseEntity<EmployeeResponse> getAllEmployeesDetails(

			@RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = true) Integer pageNo,
			@RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = true) Integer pageSize,
			@RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = true) String sortBy) {

		return ResponseEntity.ok(employeeService.getAllEmployees(pageNo, pageSize, sortBy));
	}

	@GetMapping("/emp/{id}")
	public ResponseEntity<EmployeeDto> getOneEmployee(@PathVariable(value = "id") Integer empId) {
		return new ResponseEntity<>(employeeService.getOneEmployee(empId), HttpStatus.OK);
	}

	@GetMapping("/excel")
	public void generateExcel(HttpServletResponse response) throws Exception {

		response.setContentType("application/octet-stream");
		String headerKey = "Content-Disposition";
		String headerValue = "attachment;filename=employees.xls";
		response.addHeader(headerKey, headerValue);
		employeeService.generateExcel(response);
	}
}
