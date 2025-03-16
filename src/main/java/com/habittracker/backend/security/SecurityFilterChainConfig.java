package com.habittracker.backend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.habittracker.backend.auth.service.JwtAuthFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityFilterChainConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    @Value("${loader.prefix}")
    private String LOADER_API_PATH_PREFIX;

    @Value("${springdoc.api-docs.path}")
    private String API_DOCS_PATH;

    @Value("${springdoc.swagger-ui.path}")
    private String API_DOCS_UI_PATH;

    @Bean
    @Order(1)
    public SecurityFilterChain basicAuthFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .securityMatcher(LOADER_API_PATH_PREFIX + "/**")
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/auth/login", "/auth/refresh")
                        .permitAll()
                        .requestMatchers(LOADER_API_PATH_PREFIX + "/**")
                        .authenticated())
                .httpBasic(withDefaults())
                .exceptionHandling((exceptionConfig) ->
                        exceptionConfig.authenticationEntryPoint(authenticationEntryPoint));
        return httpSecurity.build();
    }

  /*
   @TODO Access to swagger ui should be restricted to users with role 'technical user'.
         This should be changed when technical user role will be added
   */

    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(withDefaults())
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/auth/login", "/auth/refresh", "/swagger-ui/**", API_DOCS_PATH + "/**",
                                API_DOCS_UI_PATH)
                        .permitAll()
                        .anyRequest()
                        .authenticated())
                .sessionManagement(httpSecuritySessionManagementConfigurer ->
                        httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling((exceptionConfig) ->
                        exceptionConfig.authenticationEntryPoint(authenticationEntryPoint));
        return httpSecurity.build();
    }

}
