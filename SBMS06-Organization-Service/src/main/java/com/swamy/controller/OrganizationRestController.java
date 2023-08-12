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

import com.swamy.dto.OrganizationDto;
import com.swamy.service.OrganizationService;
import com.swamy.utils.AppConstants;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/organizations")
public class OrganizationRestController {

	@Autowired
	private OrganizationService organizationService;

	@PostMapping("/save")
	public ResponseEntity<OrganizationDto> saveOrganization(@RequestBody OrganizationDto organizationDto) {

		return new ResponseEntity<OrganizationDto>(organizationService.saveOrganization(organizationDto),
				HttpStatus.CREATED);
	}

	@GetMapping("/org/{org-code}")
	public ResponseEntity<OrganizationDto> getOrganizationByCode(@PathVariable(value = "org-code") String orgCode) {

		return new ResponseEntity<OrganizationDto>(organizationService.getOrganizationByCode(orgCode), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<OrganizationDto> getOrganizationById(@PathVariable(value = "id") Integer organizationId) {

		return new ResponseEntity<OrganizationDto>(organizationService.getOrganizationById(organizationId),
				HttpStatus.OK);
	}

	@GetMapping("/list")
	public ResponseEntity<List<OrganizationDto>> getAllOrganizations() {

		return ResponseEntity.ok(organizationService.getAllOrganizations());
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<OrganizationDto> updateOrganization(@PathVariable("id") Integer organizationId,
			@RequestBody OrganizationDto organizationDto) {

		return new ResponseEntity<OrganizationDto>(
				organizationService.updateOrganization(organizationId, organizationDto), HttpStatus.OK);
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> deleteOrganizationById(@PathVariable(value = "id") Integer organizationId) {
		organizationService.deleteOrganization(organizationId);
		return new ResponseEntity<>(AppConstants.ORGANIZATION_DELETION_SUCCESS + organizationId, HttpStatus.OK);
	}
}
