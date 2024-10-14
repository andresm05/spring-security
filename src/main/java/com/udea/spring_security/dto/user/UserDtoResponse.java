package com.udea.spring_security.dto.user;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class UserDtoResponse {
    
    Long id;

    String name;

    String phone;

    Boolean enabled;

    Set<String> roles;

}
