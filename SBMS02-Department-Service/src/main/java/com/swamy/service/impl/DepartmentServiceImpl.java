package com.swamy.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swamy.dto.DepartmentDto;
import com.swamy.entity.Department;
import com.swamy.exception.ResourceNotFoundException;
import com.swamy.repository.DepartmentRepository;
import com.swamy.service.DepartmentService;
import com.swamy.utils.AppConstants;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentRepository departmentRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public DepartmentDto saveDepartment(DepartmentDto departmentDto) {

		Department department = modelMapper.map(departmentDto, Department.class);

		Department savedDepartment = departmentRepository.save(department);

		DepartmentDto departmentResponse = modelMapper.map(savedDepartment, DepartmentDto.class);

		return departmentResponse;
	}

	@Override
	public DepartmentDto getDepartmentByCode(String departmentCode) {

		Department department = departmentRepository.findByDepartmentCode(departmentCode).orElseThrow(
				() -> new ResourceNotFoundException(String.format(AppConstants.DEPARTMENT_NOT_FOUND, departmentCode)));

		return modelMapper.map(department, DepartmentDto.class);
	}

	@Override
	public DepartmentDto getDepartmentById(Integer departmentId) {

		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format(AppConstants.DEPARTMENT_NOT_FOUND_BY_ID, departmentId)));

		return modelMapper.map(department, DepartmentDto.class);
	}

	@Override
	public List<DepartmentDto> getAllDepartments() {

		List<Department> list = departmentRepository.findAll();

		return list.stream().map(department -> modelMapper.map(department, DepartmentDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public DepartmentDto updateDepartment(Integer departmentId, DepartmentDto departmentDto) {

		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format(AppConstants.DEPARTMENT_NOT_FOUND_BY_ID, departmentId)));

		department.setDepartmentName(departmentDto.getDepartmentName());
		department.setDepartmentCode(departmentDto.getDepartmentCode());
		department.setDepartmentDescription(departmentDto.getDepartmentDescription());

		Department updatedDepartment = departmentRepository.save(department);
		return modelMapper.map(updatedDepartment, DepartmentDto.class);
	}

	@Override
	public void deleteDepartment(Integer departmentId) {

		Department department = departmentRepository.findById(departmentId)
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format(AppConstants.DEPARTMENT_NOT_FOUND_BY_ID, departmentId)));

		departmentRepository.deleteById(department.getDepartmentId());

	}
}
