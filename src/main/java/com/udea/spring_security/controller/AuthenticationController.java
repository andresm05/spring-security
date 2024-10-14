package com.udea.spring_security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.udea.spring_security.service.auth.AuthService;

import jakarta.validation.Valid;

import com.udea.spring_security.dto.auth.AuthDtoSignInRequest;
import com.udea.spring_security.dto.auth.AuthDtoLoginRequest;
import com.udea.spring_security.dto.auth.AuthDtoResponse;

@RestController
@RequestMapping("/auth")
@PreAuthorize("permitAll()")
public class AuthenticationController {

    @Autowired
    private AuthService authService;

    @PostMapping("/log-in")
    public ResponseEntity<AuthDtoResponse> logIn(@RequestBody @Valid AuthDtoLoginRequest authDtoRequest) {

        return new ResponseEntity<>(authService.logIn(authDtoRequest), HttpStatus.OK);

    }

    @PostMapping("/sign-up")
    public ResponseEntity<AuthDtoResponse> signUp(@RequestBody @Valid AuthDtoSignInRequest authDtoRequest) {

        return new ResponseEntity<>(authService.signUp(authDtoRequest), HttpStatus.CREATED);

    }

}
