package com.udea.spring_security.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class AuthDtoSignInRequest {

    @NotBlank
    String username;
    
    @NotBlank
    String password;

    @NotBlank
    String name;

    String phone;

    @NotBlank
    String document;
    
}
