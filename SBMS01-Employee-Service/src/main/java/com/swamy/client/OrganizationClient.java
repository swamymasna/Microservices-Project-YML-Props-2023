package com.swamy.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.swamy.dto.OrganizationDto;

@FeignClient(name = "ORGANIZATION-SERVICE")
public interface OrganizationClient {
	
	@GetMapping("/api/organizations/org/{org-code}")
	public OrganizationDto getOrganizationByCode(@PathVariable(value = "org-code") String orgCode);

}
