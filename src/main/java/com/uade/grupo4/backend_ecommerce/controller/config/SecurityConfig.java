package com.uade.grupo4.backend_ecommerce.controller.config;

import com.uade.grupo4.backend_ecommerce.repository.Enum.RoleEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final JwtAuthenticationFilter jwtAuthFilter;
        private final AuthenticationProvider authenticationProvider;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                .csrf(AbstractHttpConfigurer::disable)
                                .authorizeHttpRequests(req -> req.requestMatchers("/api/v1/auth/**").permitAll()
                                                .requestMatchers("/api/v1/user/register").permitAll()
                                        .requestMatchers("/api/v1/user/checkEmail").permitAll()
                                        .requestMatchers("/api/v1/user/admin/**").hasAnyAuthority(RoleEnum.ADMIN.name())
                                        //.requestMatchers("/api/v1/product/admin/**").hasAnyAuthority(RoleEnum.ADMIN.name())
                                        //.requestMatchers("/api/v1/product/user/**").hasAnyAuthority(RoleEnum.USER.name())
                                        .requestMatchers("/api/v1/product/**").permitAll()
                                        .requestMatchers("/api/v1/category/**").permitAll()
                                                .anyRequest()
                                                .authenticated())
                                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

}
