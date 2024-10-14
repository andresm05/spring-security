package com.udea.spring_security.service.role;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udea.spring_security.dto.role.RoleDtoRequest;
import com.udea.spring_security.dto.user.SearchByRoleDtoRequest;
import com.udea.spring_security.persistence.entity.PermissionEntity;
import com.udea.spring_security.persistence.entity.RoleEntity;
import com.udea.spring_security.persistence.repository.PermissionRepository;
import com.udea.spring_security.persistence.repository.RoleRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public RoleEntity saveRole(RoleDtoRequest role) {

        Set<PermissionEntity> permissions = permissionRepository.findByNameIn(role.getPermissions())
                .orElseThrow(() -> new RuntimeException("Error: Permission is not found."));
        
        if(roleRepository.findByName(role.getName()).isPresent()) {
            throw new RuntimeException("Error: Role is already taken!");
        }

        RoleEntity newRole = RoleEntity.builder()
                .name(role.getName())
                .permissions(permissions)
                .build();

        return roleRepository.save(newRole);

    }

    @Override
    public void deleteRole(Long id) {

        RoleEntity role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        roleRepository.delete(role);
    }

    @Override
    public List<RoleEntity> findAll() {

        return roleRepository.findAll();
    }

    @Override
    public RoleEntity update(RoleDtoRequest role, Long id) {

        // Find the role by id
        RoleEntity roleEntity = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        // Find the permissions by name
        Set<PermissionEntity> permissions = permissionRepository.findByNameIn(role.getPermissions()).get();

        // Update the role
        roleEntity.setName(role.getName());
        roleEntity.setPermissions(permissions);

        return roleRepository.save(roleEntity);
    }

    @Override
    public Set<RoleEntity> findByNameIn(SearchByRoleDtoRequest roles) {

        return roleRepository.findByNameIn(roles.getRoles())
                .orElseThrow(() -> new RuntimeException("Error: Roles are not found."));
    }
    
}
