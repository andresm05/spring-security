package com.udea.spring_security.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.udea.spring_security.dto.user.SearchByRoleDtoRequest;
import com.udea.spring_security.dto.user.UserDtoResponse;
import com.udea.spring_security.service.user.UserEntityService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@PreAuthorize("denyAll()")
public class UserController {

    @Autowired
    private UserEntityService userEntityService;

    @PostMapping
    @PreAuthorize("hasAuthority('CREATE')")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<UserDtoResponse>> findByRoles(
        @RequestBody @Valid SearchByRoleDtoRequest searchByRoleDtoRequest
    ) {
        return new ResponseEntity<>(userEntityService.findByRoles(searchByRoleDtoRequest), HttpStatus.OK);
        }
    
}
