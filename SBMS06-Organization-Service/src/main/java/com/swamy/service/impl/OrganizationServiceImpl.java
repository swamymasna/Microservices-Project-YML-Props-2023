package com.swamy.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swamy.dto.OrganizationDto;
import com.swamy.entity.Organization;
import com.swamy.exception.ResourceNotFoundException;
import com.swamy.repository.OrganizationRepository;
import com.swamy.service.OrganizationService;
import com.swamy.utils.AppConstants;

@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

	@Autowired
	private OrganizationRepository organizationRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public OrganizationDto saveOrganization(OrganizationDto organizationDto) {

		Organization organization = modelMapper.map(organizationDto, Organization.class);

		Organization savedOrganization = organizationRepository.save(organization);

		OrganizationDto organizationResponse = modelMapper.map(savedOrganization, OrganizationDto.class);

		return organizationResponse;
	}

	@Override
	public OrganizationDto getOrganizationByCode(String organizationCode) {

		Organization organization = organizationRepository.findByOrganizationCode(organizationCode)
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format(AppConstants.ORGANIZATION_NOT_FOUND, organizationCode)));

		return modelMapper.map(organization, OrganizationDto.class);
	}

	@Override
	public OrganizationDto getOrganizationById(Integer organizationId) {

		Organization organization = organizationRepository.findById(organizationId)
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format(AppConstants.ORGANIZATION_NOT_FOUND_BY_ID, organizationId)));

		return modelMapper.map(organization, OrganizationDto.class);
	}

	@Override
	public List<OrganizationDto> getAllOrganizations() {

		List<Organization> list = organizationRepository.findAll();

		return list.stream().map(organization -> modelMapper.map(organization, OrganizationDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public OrganizationDto updateOrganization(Integer organizationId, OrganizationDto organizationDto) {

		Organization organization = organizationRepository.findById(organizationId)
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format(AppConstants.ORGANIZATION_NOT_FOUND_BY_ID, organizationId)));

		organization.setOrganizationName(organizationDto.getOrganizationName());
		organization.setOrganizationCode(organizationDto.getOrganizationCode());
		organization.setOrganizationDescription(organizationDto.getOrganizationDescription());

		Organization updatedOrganization = organizationRepository.save(organization);
		return modelMapper.map(updatedOrganization, OrganizationDto.class);
	}

	@Override
	public void deleteOrganization(Integer organizationId) {

		Organization organization = organizationRepository.findById(organizationId)
				.orElseThrow(() -> new ResourceNotFoundException(
						String.format(AppConstants.ORGANIZATION_NOT_FOUND_BY_ID, organizationId)));

		organizationRepository.deleteById(organization.getOrganizationId());

	}
}
