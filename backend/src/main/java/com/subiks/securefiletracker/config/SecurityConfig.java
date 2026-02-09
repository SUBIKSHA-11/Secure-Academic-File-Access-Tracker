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
        
    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
    .requestMatchers("/auth/**").permitAll()
    .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")

    .requestMatchers(HttpMethod.POST, "/files/upload")
        .hasAuthority("ROLE_FACULTY")

    .requestMatchers(HttpMethod.DELETE, "/files/**")
        .hasAuthority("ROLE_FACULTY")

    .requestMatchers(HttpMethod.PUT, "/files/**")
        .hasAuthority("ROLE_FACULTY")
.requestMatchers(HttpMethod.POST, "/lessons/**")
    .hasAuthority("ROLE_FACULTY")
    .requestMatchers(HttpMethod.DELETE, "/lessons/**")
        .hasAuthority("ROLE_FACULTY")

    .requestMatchers(HttpMethod.PUT, "/lessons/**")
        .hasAuthority("ROLE_FACULTY")

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
