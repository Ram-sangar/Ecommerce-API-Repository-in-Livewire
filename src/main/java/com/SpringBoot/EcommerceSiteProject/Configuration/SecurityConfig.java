package com.SpringBoot.EcommerceSiteProject.Configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthFilter jwtAuthFilter;
    private final AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                                 auth.requestMatchers("/home/**").hasAuthority("CUSTOMER")
                                    .requestMatchers("/api/user/**").permitAll()
                                    .requestMatchers( "/category/**").hasAuthority("ADMIN")
                                    .requestMatchers("/product/**").hasAuthority("SELLER")
                                    .requestMatchers("/address/**").hasAnyAuthority("SELLER","CUSTOMER")
                                    .requestMatchers("/cart/**","/order/**","/payment/**").hasAuthority("CUSTOMER")
                                    .requestMatchers("/swagger-ui/**").permitAll()
                                    .requestMatchers("/v3/**").permitAll()
                                    .anyRequest().authenticated()
                );

        http.authenticationProvider(authenticationProvider);

        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
