package com.subiks.securefiletracker.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.subiks.securefiletracker.filter.JwtFilter;

@EnableMethodSecurity
@Configuration
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
    .authorizeHttpRequests(auth -> auth

    // ----- PUBLIC -----
    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
    .requestMatchers("/auth/**").permitAll()

    // ----- ADMIN -----
    .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")

    // ----- SEMESTERS / SUBJECTS / LESSONS (VIEW) -----
    // ----- READ ACCESS (STUDENT / FACULTY / ADMIN) -----
.requestMatchers(HttpMethod.GET,
    "/semesters/**",
    "/subjects/**",
    "/lessons/**",
    "/files/**"
).hasAnyAuthority("ROLE_STUDENT", "ROLE_FACULTY", "ROLE_ADMIN")

    // ----- FILE MODIFY -----
    .requestMatchers(HttpMethod.POST, "/files/upload")
        .hasAnyAuthority("ROLE_FACULTY", "ROLE_ADMIN")

    .requestMatchers(HttpMethod.DELETE, "/files/**")
        .hasAnyAuthority("ROLE_FACULTY", "ROLE_ADMIN")

    .requestMatchers(HttpMethod.PUT, "/files/**")
        .hasAnyAuthority("ROLE_FACULTY", "ROLE_ADMIN")

    // ----- LESSON MODIFY -----
    .requestMatchers(HttpMethod.POST, "/lessons/**")
        .hasAnyAuthority("ROLE_FACULTY", "ROLE_ADMIN")

    .requestMatchers(HttpMethod.PUT, "/lessons/**")
        .hasAnyAuthority("ROLE_FACULTY", "ROLE_ADMIN")

    .requestMatchers(HttpMethod.DELETE, "/lessons/**")
        .hasAnyAuthority("ROLE_FACULTY", "ROLE_ADMIN")

    // ----- EVERYTHING ELSE -----
    .anyRequest().authenticated()
)

            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

}
