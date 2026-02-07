package com.subiks.securefiletracker.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.subiks.securefiletracker.model.User;
import com.subiks.securefiletracker.service.UserService;
import com.subiks.securefiletracker.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String email = null;

        // 1️⃣ Token extract
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            email = jwtUtil.extractEmail(token);
        }

        // 2️⃣ Authenticate user
        if (email != null &&
            SecurityContextHolder.getContext().getAuthentication() == null) {

            User user = userService.findByEmail(email);

            if (user != null && jwtUtil.validateToken(token, email)) {

                // 3️⃣ Role set pannrom (Java 8 safe)
                List<SimpleGrantedAuthority> authorities =
                        new ArrayList<>();

                authorities.add(
                        new SimpleGrantedAuthority(
                                "ROLE_" + user.getRole()
                        )
                );

                // 4️⃣ Authentication token create
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                authorities
                        );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                // 5️⃣ Set authentication
                SecurityContextHolder.getContext()
                        .setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
