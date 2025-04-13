package com.udea.spring_security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.udea.spring_security.dto.auth.AuthDtoLoginRequest;
import com.udea.spring_security.dto.auth.AuthDtoResponse;
import com.udea.spring_security.dto.auth.AuthDtoSignInRequest;
import com.udea.spring_security.service.auth.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@PreAuthorize("permitAll()")
public class AuthenticationController {

    @Autowired
    private AuthService authService;

    @PostMapping("/log-in")
    public ResponseEntity<AuthDtoResponse> logIn(@RequestBody @Valid AuthDtoLoginRequest authDtoRequest) {

        //save the token in the cookies
        AuthDtoResponse authDtoResponse = authService.logIn(authDtoRequest);
        Cookie cookie = new Cookie("token", authDtoResponse.getToken());
        cookie.setPath("/");

        return new ResponseEntity<>(authDtoResponse, HttpStatus.OK);

    }

    @PostMapping("/sign-up")
    public ResponseEntity<AuthDtoResponse> signUp(@RequestBody @Valid AuthDtoSignInRequest authDtoRequest) {

        //save the token in the cookies
        AuthDtoResponse authDtoResponse = authService.signUp(authDtoRequest);
        Cookie cookie = new Cookie("token", authDtoResponse.getToken());
        cookie.setPath("/");

        return new ResponseEntity<>(authDtoResponse, HttpStatus.CREATED);

    }

}
