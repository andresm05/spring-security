package com.udea.spring_security.service.auth;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.udea.spring_security.dto.auth.AuthDtoResponse;
import com.udea.spring_security.persistence.entity.RoleEnum;
import com.udea.spring_security.persistence.entity.UserEntity;
import com.udea.spring_security.persistence.repository.RoleRepository;
import com.udea.spring_security.persistence.repository.UserRepository;
import com.udea.spring_security.service.UserDetailsServiceImpl;
import com.udea.spring_security.util.JwtUtils;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class Oauth2ServiceImpl implements Oauth2Service {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtUtils jwtUtils;

    @Override
    public AuthDtoResponse signInOauth2(String email, String provider, String name) {

        // check if user already exists in the database
        if (userRepository.findByUsername(email).isPresent()) {
            UserEntity userFound = userRepository.findByUsername(email).get();

            Authentication authentication = authenticate(email);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String accessToken = jwtUtils.createToken(authentication, userFound.getDocument());

            return AuthDtoResponse.builder()
                    .username(email)
                    .name(userFound.getName())
                    .token(accessToken)
                    .enabled(true)
                    .build();

        }

        UserEntity newUser = UserEntity.builder()
                .username(email)
                .name(name)
                .password("Google")
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

        Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.createToken(authentication, newUser.getDocument());

        return AuthDtoResponse.builder()
                .username(email)
                .name(email)
                .token(accessToken)
                .enabled(true)
                .build();
    }

    private Authentication authenticate(String username) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("User not found");
        }
        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());

    }

}
