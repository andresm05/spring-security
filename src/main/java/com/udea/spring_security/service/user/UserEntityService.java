package com.udea.spring_security.service.user;

import java.util.List;

import com.udea.spring_security.dto.user.SearchByRoleDtoRequest;
import com.udea.spring_security.dto.user.UserDtoResponse;
import com.udea.spring_security.persistence.entity.UserEntity;

public interface UserEntityService {

    List<UserEntity> findAll();

    List<UserDtoResponse> findByRoles(SearchByRoleDtoRequest searchByRoleDtoRequest);
    
}
