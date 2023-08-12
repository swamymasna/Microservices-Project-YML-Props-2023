package com.swamy.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swamy.client.DepartmentClient;
import com.swamy.client.OrganizationClient;
import com.swamy.dto.ApiResponse;
import com.swamy.dto.DepartmentDto;
import com.swamy.dto.EmployeeDto;
import com.swamy.dto.EmployeeResponse;
import com.swamy.dto.OrganizationDto;
import com.swamy.entity.Employee;
import com.swamy.exception.ResourceNotFoundException;
import com.swamy.repository.EmployeeRepository;
import com.swamy.service.EmployeeService;
import com.swamy.utils.AppConstants;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private DepartmentClient departmentClient;

	@Autowired
	private OrganizationClient organizationClient;

	@Override
	public EmployeeDto saveEmployee(EmployeeDto employeeDto) {

		Employee employee = modelMapper.map(employeeDto, Employee.class);

		Employee savedEmployee = employeeRepository.save(employee);

		EmployeeDto employeeResponse = modelMapper.map(savedEmployee, EmployeeDto.class);

		return employeeResponse;
	}

	@Override
	public List<EmployeeDto> getAllEmployees() {

		List<Employee> employeesList = employeeRepository.findAll();

		return employeesList.stream().map(employee -> modelMapper.map(employee, EmployeeDto.class))
				.collect(Collectors.toList());

	}

	@CircuitBreaker(name = "${app.emp.service}", fallbackMethod = "${app.fallback.method}")
	@Override
	public ApiResponse getEmployeeById(Integer employeeId) {

		Employee employee = employeeRepository.findById(employeeId).orElseThrow(
				() -> new ResourceNotFoundException(String.format(AppConstants.EMPLOYEE_NOT_FOUND, employeeId)));

		EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);

		DepartmentDto departmentDto = departmentClient.getDepartmentByCode(employee.getDepartmentCode());

		OrganizationDto organizationDto = organizationClient.getOrganizationByCode(employee.getOrganizationCode());

		ApiResponse apiResponse = new ApiResponse();

		apiResponse.setEmployeeDto(employeeDto);
		apiResponse.setDepartmentDto(departmentDto);
		apiResponse.setOrganizationDto(organizationDto);

		return apiResponse;
	}

	public ApiResponse defaultGetEmployeeById(Integer employeeId, Exception exception) {

		Employee employee = employeeRepository.findById(employeeId).orElseThrow(
				() -> new ResourceNotFoundException(String.format(AppConstants.EMPLOYEE_NOT_FOUND, employeeId)));

		EmployeeDto employeeDto = modelMapper.map(employee, EmployeeDto.class);

		DepartmentDto departmentDto = new DepartmentDto();
		departmentDto.setDepartmentId(0);
		departmentDto.setDepartmentName("DEFAULT-DEPT-NAME");
		departmentDto.setDepartmentDescription("DEFAULT-DEPT-DESC");
		departmentDto.setDepartmentCode("DEFAULT-DEPT-001");

		OrganizationDto organizationDto = new OrganizationDto();
		organizationDto.setOrganizationId(0);
		organizationDto.setOrganizationName("DEFAULT-ORG-NAME");
		organizationDto.setOrganizationDescription("DEFAULT-ORG-DESC");
		organizationDto.setOrganizationCode("DEFAULT-ORG-001");

		ApiResponse apiResponse = new ApiResponse();

		apiResponse.setEmployeeDto(employeeDto);
		apiResponse.setDepartmentDto(departmentDto);
		apiResponse.setOrganizationDto(organizationDto);

		return apiResponse;
	}

	@Override
	public EmployeeDto updateEmployeeById(Integer employeeId, EmployeeDto employeeDto) {

		Employee employee = employeeRepository.findById(employeeId).orElseThrow(
				() -> new ResourceNotFoundException(String.format(AppConstants.EMPLOYEE_NOT_FOUND, employeeId)));

		employee.setEmployeeName(employeeDto.getEmployeeName());
		employee.setEmployeeSalary(employeeDto.getEmployeeSalary());
		employee.setEmployeeAddress(employeeDto.getEmployeeAddress());

		Employee updatedEmployee = employeeRepository.save(employee);

		EmployeeDto updatedEmpResponse = modelMapper.map(updatedEmployee, EmployeeDto.class);

		return updatedEmpResponse;
	}

	@Override
	public void deleteEmployeeById(Integer employeeId) {

		Employee employee = employeeRepository.findById(employeeId).orElseThrow(
				() -> new ResourceNotFoundException(String.format(AppConstants.EMPLOYEE_NOT_FOUND, employeeId)));

		employee.setEmployeeId(employeeId);

		employeeRepository.deleteById(employeeId);

	}

	@Override
	public EmployeeResponse getAllEmployees(Integer pageNo, Integer pageSize, String sortBy) {

		Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

		Page<Employee> pages = employeeRepository.findAll(pageable);

		List<Employee> list = pages.getContent();

		List<EmployeeDto> content = list.stream().map(emp -> modelMapper.map(emp, EmployeeDto.class))
				.collect(Collectors.toList());

		EmployeeResponse employeeResponse = new EmployeeResponse();

		employeeResponse.setContent(content);

		employeeResponse.setPageNo(pageNo);

		employeeResponse.setPageSize(pageSize);

		employeeResponse.setSortBy(sortBy);

		employeeResponse.setTotalElements(pages.getTotalElements());

		employeeResponse.setTotalPages(pages.getTotalPages());

		employeeResponse.setFirst(pages.isFirst());

		employeeResponse.setLast(pages.isLast());

		return employeeResponse;
	}

	@Override
	public EmployeeDto getOneEmployee(Integer employeeId) {

		Employee employee = employeeRepository.findById(employeeId).orElseThrow(
				() -> new ResourceNotFoundException(String.format(AppConstants.EMPLOYEE_NOT_FOUND, employeeId)));

		return modelMapper.map(employee, EmployeeDto.class);
	}

	@Override
	public void generateExcel(HttpServletResponse response) throws Exception {

		List<EmployeeDto> employees = getAllEmployees();

		HSSFWorkbook workbook = new HSSFWorkbook();

		HSSFSheet sheet = workbook.createSheet("Employee Details");

		HSSFRow row = sheet.createRow(0);

		row.createCell(0).setCellValue("ID");
		row.createCell(1).setCellValue("EMPLOYEE NAME");
		row.createCell(2).setCellValue("EMPLOYEE SALARY");
		row.createCell(3).setCellValue("EMPLOYEE ADDRESS");
		row.createCell(4).setCellValue("DEPARTMENT CODE");
		row.createCell(5).setCellValue("ORGANIZATION CODE");

		int dataRowNum = 1;
		for (EmployeeDto employee : employees) {

			HSSFRow dataRow = sheet.createRow(dataRowNum);
			dataRow.createCell(0).setCellValue(employee.getEmployeeId());
			dataRow.createCell(1).setCellValue(employee.getEmployeeName());
			dataRow.createCell(2).setCellValue(employee.getEmployeeSalary());
			dataRow.createCell(3).setCellValue(employee.getEmployeeAddress());
			dataRow.createCell(4).setCellValue(employee.getDepartmentCode());
			dataRow.createCell(5).setCellValue(employee.getOrganizationCode());
			dataRowNum++;
		}
		ServletOutputStream ops = response.getOutputStream();
		workbook.write(ops);
		workbook.close();
		ops.close();
	}
}
