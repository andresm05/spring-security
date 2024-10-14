package com.udea.spring_security.dto.role;

import java.util.Set;

import com.udea.spring_security.persistence.entity.RoleEnum;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
public class RoleDtoRequest {

    @NotNull
    RoleEnum name;

    @NotNull
    Set<String> permissions;
    
}
