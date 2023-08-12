package com.swamy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.swamy.entity.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Integer> {

	Optional<Organization> findByOrganizationCode(String organizationCode);
}
