package com.udea.spring_security.service.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udea.spring_security.dto.user.SearchByRoleDtoRequest;
import com.udea.spring_security.dto.user.UserDtoResponse;
import com.udea.spring_security.persistence.entity.RoleEntity;
import com.udea.spring_security.persistence.entity.UserEntity;
import com.udea.spring_security.persistence.repository.RoleRepository;
import com.udea.spring_security.persistence.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserEntityServiceImpl implements UserEntityService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Override
    public List<UserDtoResponse> findByRoles(SearchByRoleDtoRequest searchByRoleDtoRequest) {

        // search roles
        Set<RoleEntity> roles = roleRepository.findByNameIn(searchByRoleDtoRequest.getRoles()).orElseThrow(
                () -> new RuntimeException("Roles not found"));

        List<UserDtoResponse> users = new ArrayList<>();
        List<UserEntity> usersFound = userRepository.findByRolesIn(roles);

        usersFound.forEach(user -> {
            users.add(UserDtoResponse.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .phone(user.getPhone())
                    .roles(user.getRoles().stream().map(role -> role.getName().name()).collect(Collectors.toSet()))
                    .enabled(user.getEnabled())
                    .build());
        });

        return users;
    }

}
