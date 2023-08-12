package com.swamy.service;

import java.util.List;

import com.swamy.dto.OrganizationDto;

public interface OrganizationService {

	public OrganizationDto saveOrganization(OrganizationDto organizationDto);

	public OrganizationDto getOrganizationByCode(String organizationCode);

	public OrganizationDto getOrganizationById(Integer organizationId);

	public List<OrganizationDto> getAllOrganizations();

	public OrganizationDto updateOrganization(Integer organizationId, OrganizationDto organizationDto);

	void deleteOrganization(Integer organizationId);
}
