package com.udea.spring_security.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udea.spring_security.dto.role.RoleDtoRequest;
import com.udea.spring_security.dto.user.SearchByRoleDtoRequest;
import com.udea.spring_security.persistence.entity.RoleEntity;
import com.udea.spring_security.service.role.RoleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/roles")
@PreAuthorize("denyAll()")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<RoleEntity>> getRoles() {

        return new ResponseEntity<>(roleService.findAll(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<RoleEntity> createRole(@RequestBody RoleDtoRequest role) {

        return new ResponseEntity<>(roleService.saveRole(role), HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable Long id) {

        roleService.deleteRole(id);

        return new ResponseEntity<>("Role deleted successfully", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<RoleEntity> updateRole(@RequestBody @Valid RoleDtoRequest role, @PathVariable Long id) {

        return new ResponseEntity<>(roleService.update(role, id), HttpStatus.OK);
    }

    @PreAuthorize("permitAll()")
    @GetMapping("/by-names")
    public ResponseEntity<Set<RoleEntity>> getRolesByName(@RequestBody @Valid SearchByRoleDtoRequest names) {

        return new ResponseEntity<>(roleService.findByNameIn(names), HttpStatus.OK);
    }
}
