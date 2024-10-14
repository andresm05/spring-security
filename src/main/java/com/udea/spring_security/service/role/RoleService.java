package com.udea.spring_security.service.role;

import java.util.List;
import java.util.Set;

import com.udea.spring_security.dto.role.RoleDtoRequest;
import com.udea.spring_security.dto.user.SearchByRoleDtoRequest;
import com.udea.spring_security.persistence.entity.RoleEntity;

public interface RoleService {

    RoleEntity saveRole(RoleDtoRequest role);

    void deleteRole(Long id);

    List<RoleEntity> findAll();

    RoleEntity update(RoleDtoRequest role, Long id);

    Set<RoleEntity> findByNameIn(SearchByRoleDtoRequest roles);


    
}
