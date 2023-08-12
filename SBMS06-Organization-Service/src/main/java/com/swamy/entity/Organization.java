package com.swamy.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "ORGANIZATION_TBL")
public class Organization {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ORG_ID")
	private Integer organizationId;
	
	@Column(name = "ORG_NAME")
	private String organizationName;
	
	@Column(name = "ORG_DESC")
	private String organizationDescription;

	@Column(name = "ORG_CODE")
	private String organizationCode;
}
