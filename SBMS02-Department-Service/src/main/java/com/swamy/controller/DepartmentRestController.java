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
import org.springframework.web.bind.annotation.RestController;

import com.swamy.dto.DepartmentDto;
import com.swamy.service.DepartmentService;
import com.swamy.utils.AppConstants;

import jakarta.validation.Valid;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/departments")
public class DepartmentRestController {

	@Autowired
	private DepartmentService departmentService;

	@PostMapping("/save")
	public ResponseEntity<DepartmentDto> saveDepartment(@RequestBody @Valid DepartmentDto departmentDto) {

		return new ResponseEntity<>(departmentService.saveDepartment(departmentDto), HttpStatus.CREATED);

	}

	@GetMapping("/dept/{dept_code}")
	public ResponseEntity<DepartmentDto> getDepartmentByCode(@PathVariable(value = "dept_code") String departmentCode) {

		return new ResponseEntity<>(departmentService.getDepartmentByCode(departmentCode), HttpStatus.OK);

	}

	@GetMapping("/{id}")
	public ResponseEntity<DepartmentDto> getDepartmentById(@PathVariable(value = "id") Integer departmentId) {

		return new ResponseEntity<>(departmentService.getDepartmentById(departmentId), HttpStatus.OK);

	}

	@GetMapping("/list")
	public ResponseEntity<List<DepartmentDto>> getAllDepartments() {

		return new ResponseEntity<>(departmentService.getAllDepartments(), HttpStatus.OK);

	}

	@PutMapping("/update/{id}")
	public ResponseEntity<DepartmentDto> updateDepartment(@PathVariable(value = "id") Integer departmentId,
			@RequestBody @Valid DepartmentDto departmentDto) {

		return new ResponseEntity<>(departmentService.updateDepartment(departmentId, departmentDto), HttpStatus.OK);

	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteDepartment(@PathVariable(value = "id") Integer departmentId) {

		departmentService.deleteDepartment(departmentId);
		return new ResponseEntity<>(AppConstants.DEPARTMENT_DELETION_SUCCESS + departmentId, HttpStatus.OK);

	}

}
