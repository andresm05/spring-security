package com.udea.spring_security.config;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.udea.spring_security.dto.auth.AuthDtoResponse;
import com.udea.spring_security.service.auth.Oauth2Service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class Oauth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final Oauth2Service oauth2UserService;
    private final ApplicationaProperties applicationProperties;

    @Override
    @Transactional
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException {
        //The path to sign in with oauth2 is /oauth2/authorization/{provider}
        OAuth2AuthenticationToken oauth2Token = (OAuth2AuthenticationToken) authentication;
        String email = oauth2Token.getPrincipal().getAttribute("email");
        String provider = oauth2Token.getAuthorizedClientRegistrationId();
        String name = oauth2Token.getPrincipal().getAttribute("name");

        System.out.println(oauth2Token.getPrincipal().getAttributes());

        //sign in oauth2
        AuthDtoResponse responseData = oauth2UserService.signInOauth2(email, provider, name);

        //add token to cookie
        Cookie tokenCookie = new Cookie("token", responseData.getToken());
        //tokenCookie.setHttpOnly(true); // evita acceso desde JS
        //tokenCookie.setSecure(true); // solo sobre HTTPS
        tokenCookie.setPath("/");
        System.out.println("token cookie " + tokenCookie.getValue());
        response.addCookie(tokenCookie);
        response.sendRedirect(applicationProperties.getLoginSuccessUrl());


    }

}
