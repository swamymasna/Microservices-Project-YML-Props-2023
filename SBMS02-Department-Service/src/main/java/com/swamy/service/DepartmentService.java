package com.swamy.service;

import java.util.List;

import com.swamy.dto.DepartmentDto;

public interface DepartmentService {

	public DepartmentDto saveDepartment(DepartmentDto departmentDto);

	public DepartmentDto getDepartmentByCode(String departmentCode);

	public DepartmentDto getDepartmentById(Integer departmentId);

	public List<DepartmentDto> getAllDepartments();

	public DepartmentDto updateDepartment(Integer departmentId, DepartmentDto departmentDto);

	public void deleteDepartment(Integer departmentId);

}
