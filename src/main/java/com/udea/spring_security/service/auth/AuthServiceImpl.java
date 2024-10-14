package com.udea.spring_security.service.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.udea.spring_security.dto.auth.AuthDtoSignInRequest;
import com.udea.spring_security.dto.auth.AuthDtoLoginRequest;
import com.udea.spring_security.dto.auth.AuthDtoResponse;
import com.udea.spring_security.persistence.entity.RoleEnum;
import com.udea.spring_security.persistence.entity.UserEntity;
import com.udea.spring_security.persistence.repository.RoleRepository;
import com.udea.spring_security.persistence.repository.UserRepository;
import com.udea.spring_security.service.UserDetailsServiceImpl;
import com.udea.spring_security.util.JwtUtils;

@Service
@Transactional
public class AuthServiceImpl implements AuthService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AuthDtoResponse signUp(AuthDtoSignInRequest authDtoRequest) {

        String username = authDtoRequest.getUsername();
        String password = authDtoRequest.getPassword();
        String name = authDtoRequest.getName();
        String phone = authDtoRequest.getPhone();
        String document = authDtoRequest.getDocument();

        // verify user does not exist
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("User already exists");
        }

        // encrypt password
        authDtoRequest.setPassword(passwordEncoder.encode(password));

        UserEntity newUser = UserEntity.builder()
                .username(username)
                .password(authDtoRequest.getPassword())
                .name(name)
                .phone(phone)
                .document(document)
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .roles(Set.of(roleRepository.findByName(RoleEnum.GUEST).get()))
                .build();

        // save user
        userRepository.save(newUser);

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        newUser.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_".concat(role.getName().name())));
        });

        newUser.getRoles().stream().flatMap(role -> role.getPermissions().stream())
                .forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission.getName())));

        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password, authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.createToken(authentication, document);

        return AuthDtoResponse.builder()
                .username(username)
                .name(name)
                .phone(phone)
                .token(accessToken)
                .enabled(true)
                .build();
    }

    @Override
    public AuthDtoResponse logIn(AuthDtoLoginRequest authDtoRequest) {

        Authentication authentication = this.authenticate(authDtoRequest.getUsername(), authDtoRequest.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserEntity user = userRepository.findByUsername(authDtoRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String accessToken = jwtUtils.createToken(authentication, user.getDocument());

        return AuthDtoResponse.builder()
                .username(authDtoRequest.getUsername())
                .name(user.getName())
                .phone(user.getPhone())
                .token(accessToken)
                .enabled(true)
                .build();
    }

    public Authentication authenticate(String username, String password) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("User not found");
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }

        return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
    }

}
