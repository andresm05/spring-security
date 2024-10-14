package com.udea.spring_security.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.udea.spring_security.persistence.entity.UserEntity;
import java.util.List;
import com.udea.spring_security.persistence.entity.RoleEntity;
import java.util.Set;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    List<UserEntity> findByRolesIn(Set<RoleEntity> roles);
    
}
