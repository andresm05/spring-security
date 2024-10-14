package com.udea.spring_security.dto.user;

import java.util.Set;

import com.udea.spring_security.persistence.entity.RoleEnum;

import jakarta.validation.constraints.NotNull;
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
public class SearchByRoleDtoRequest {

    @NotNull
    Set<RoleEnum> roles;
    
}
