package com.udea.spring_security.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.udea.spring_security.config.filter.JwtTokenValidator;
import com.udea.spring_security.service.UserDetailsServiceImpl;
import com.udea.spring_security.util.JwtUtils;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtUtils jwtUtils;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
        .csrf(csrf -> csrf.disable()) // deshabilita la protección contra CSRF
        .httpBasic(Customizer.withDefaults()) // autenticación básica
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // no se almacena la sesión en el servidor. No se crea el objeto en memoria.
        //el tiempo de vida de la sesión es el tiempo de vida del token
        // .authorizeHttpRequests(http ->
        // {
        //     http.requestMatchers(HttpMethod.GET, "/test/hello").permitAll();

        //     http.requestMatchers(HttpMethod.GET, "/test/hello-security").hasAnyAuthority("CREATE", "DELETE");

        //     http.anyRequest().authenticated();
        // })
        .addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class)
        .build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    AuthenticationProvider authenticationProvider(UserDetailsServiceImpl userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }


    // @Bean
    // UserDetailsService userDetailsService(){
        
    //     List<UserDetails> users = new ArrayList<>();

    //     users.add(
    //         User.withUsername("user")
    //         .password(passwordEncoder().encode("1234"))
    //         .roles("USER")
    //         .authorities("READ")
    //         .build()
    //     );

    //     return new InMemoryUserDetailsManager(users);
    // }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    
}
