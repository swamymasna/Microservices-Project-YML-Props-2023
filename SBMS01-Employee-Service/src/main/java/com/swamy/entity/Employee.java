package com.swamy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "EMPLOYEE_TBL")
@Entity
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EMP_ID")
	private Integer employeeId;
	
	@Column(name = "EMP_NAME")
	private String employeeName;
	
	@Column(name = "EMP_SAL")
	private Double employeeSalary;
	
	@Column(name = "EMP_ADDR")
	private String employeeAddress;
	
	@Column(name = "DEPT_CODE")
	private String departmentCode;
	
	@Column(name = "ORG_CODE")
	private String organizationCode;
}
