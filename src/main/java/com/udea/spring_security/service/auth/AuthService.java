package com.udea.spring_security.service.auth;

import com.udea.spring_security.dto.auth.AuthDtoLoginRequest;
import com.udea.spring_security.dto.auth.AuthDtoResponse;
import com.udea.spring_security.dto.auth.AuthDtoSignInRequest;

public interface AuthService {
    
    AuthDtoResponse signUp( AuthDtoSignInRequest userDtoRequest);

    AuthDtoResponse logIn ( AuthDtoLoginRequest userDtoRequest);

}
