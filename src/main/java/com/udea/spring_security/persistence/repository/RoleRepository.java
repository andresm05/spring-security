package com.udea.spring_security.persistence.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udea.spring_security.persistence.entity.RoleEntity;
import com.udea.spring_security.persistence.entity.RoleEnum;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByName(RoleEnum name);

    Optional<Set<RoleEntity>> findByNameIn(Set<RoleEnum> names);
    
}
