package com.example.EvaluationManagementSystem.config;

import com.example.EvaluationManagementSystem.entity.UserEntity;
import com.example.EvaluationManagementSystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig implements UserDetailsService{

    private final UserRepository repository;

    @Transactional(readOnly=true)
    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<UserEntity> user = repository.findByEmail(username);
        if(user.isPresent()){
            UserEntity requiredUser = user.get();
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority(requiredUser.getRole()));

            return new org.springframework.security.core.userdetails.User(requiredUser.getEmail(), requiredUser.getPassword(),authorities);
        }
        else{
            throw new RuntimeException("USER IS NOT FOUND");
        }
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(this::loadUserByUsername);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;

    }
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}