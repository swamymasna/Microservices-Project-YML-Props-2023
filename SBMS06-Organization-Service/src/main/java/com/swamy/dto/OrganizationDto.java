package com.swamy.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OrganizationDto {

	private Integer organizationId;

	@NotEmpty(message = "Organization Name Should Not be Null or Empty")
	@Size(min = 2, message = "Organization Name Must have Atleast 2 charecters")
	private String organizationName;

	@NotEmpty(message = "Organization Description Should Not be Null or Empty")
	@Size(min = 2, message = "Organization Description Must have Atleast 2 charecters")
	private String organizationDescription;

	@NotEmpty(message = "Organization Code Should Not be Null or Empty")
	@Size(min = 2, message = "Organization Code Must have Atleast 2 charecters")
	private String organizationCode;
}
