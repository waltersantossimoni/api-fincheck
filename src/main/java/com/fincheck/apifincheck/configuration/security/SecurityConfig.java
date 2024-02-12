package com.fincheck.apifincheck.configuration.security;

import com.fincheck.apifincheck.configuration.security.jwt.JWTAuthenticationFilter;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  @Value("${fincheck.base-url}")
  private String[] BASE_URL;

  @Bean
  public SecurityFilterChain securityFilterChain(
    HttpSecurity http,
    JWTAuthenticationFilter jwtAuthFilter
  ) throws Exception {
    http
      .authorizeHttpRequests(authorize -> authorize
        .requestMatchers(
          "v1/auth/**"
        ).permitAll()
        .anyRequest().authenticated()
      )
      .csrf(AbstractHttpConfigurer::disable)
      .cors(customizeCors())
      .sessionManagement(sessionManagement -> sessionManagement
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      )
      .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public Customizer<CorsConfigurer<HttpSecurity>> customizeCors() {
    return cors -> cors
      .configurationSource(request -> {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(BASE_URL));
        configuration.setAllowedMethods(
          List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
        );
        configuration.setAllowedHeaders(List.of("*"));
        configuration.addExposedHeader("X-Token-Expired");
        configuration.setAllowCredentials(true);

        return configuration;
      });
  }
}
