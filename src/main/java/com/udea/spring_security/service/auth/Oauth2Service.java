package com.udea.spring_security.service.auth;

import com.udea.spring_security.dto.auth.AuthDtoResponse;

public interface  Oauth2Service {
    AuthDtoResponse signInOauth2 ( String email, String provider, String name);

}
