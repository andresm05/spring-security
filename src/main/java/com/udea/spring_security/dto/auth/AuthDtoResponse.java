package com.udea.spring_security.dto.auth;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

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
@JsonPropertyOrder({"username", "enabled", "token"})
public class AuthDtoResponse {

    String username;

    String name;

    String phone;

    String token;

    Boolean enabled;


    
}
