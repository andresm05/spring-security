package com.udea.spring_security.persistence.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.udea.spring_security.persistence.entity.PermissionEntity;

public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {

    Optional<Set<PermissionEntity>> findByNameIn(Set<String> permissions);
    
}
