package com.example.EvaluationManagementSystem.config;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/user/login","/viewTrainers","/**")
                .permitAll()
//                .requestMatchers("/batch/create","/delete/{batchId}","/{batchId}","/CEOEvaluation/upload",
//                        "/trainee/assign/batch/{batchId}","/register/admin","/register/trainee","register/trainer","/viewAdmin","/user/{userId}","/admin/{adminId}","/admin/delete/{adminId}",
//                        "/task/create","/task/delete/{taskId}","/final/create/{batchId}","/managerEvaluation/marks/upload","/tasks/submit/{taskId}",
//                        "/tasks/{taskId}/submissions/{submissionsId}","/taskEvaluation/marks/upload","/trainee/delete/{traineeId}","/trainer/delete/{trainerId}").hasAnyAuthority("Admin","Trainer")
//                .requestMatchers("/viewUsers","/CEOEvaluation/allMarks","/batch/allBatches","/batch/allBatchName","/task/batch/{batchId}","/final/marks/{batchId}","/managerEvaluation/allMarks",
//                        "/taskEvaluation/allMarks","/trainees/batch/{batchId}","/trainee/{traineeId}","/viewTrainees","/allTraineeName","/trainer/{trainerId}").hasAnyAuthority("Admin","Trainer","Trainee")
//                .requestMatchers("/tasks/submit/{taskId}").hasAnyAuthority("trainee")
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    }
