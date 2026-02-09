package com.subiks.securefiletracker.filter;

import java.io.IOException;
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

        final String authHeader = request.getHeader("Authorization");

        String email = null;
        String token = null;

        // 🔹 Extract token
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try {
                email = jwtUtil.extractEmail(token);
            } catch (Exception e) {
                // invalid token
                filterChain.doFilter(request, response);
                return;
            }
        }

        // 🔹 Authenticate only if not already authenticated
        if (email != null &&
            SecurityContextHolder.getContext().getAuthentication() == null) {

            User user = userService.findByEmail(email);

            if (user != null && jwtUtil.validateToken(token, email)) {

                // ⭐ THIS IS THE MOST IMPORTANT PART ⭐
                List<SimpleGrantedAuthority> authorities =
                        List.of(new SimpleGrantedAuthority(user.getRole().startsWith("ROLE_")
        ? user.getRole()
        : "ROLE_" + user.getRole())
);

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                user.getEmail(),
                                null,
                                authorities
                        );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authToken);

                // 🔍 TEMP DEBUG (REMOVE LATER)
                System.out.println(
                    "AUTH SET → " + authToken.getAuthorities()
                );
            }
        }

        filterChain.doFilter(request, response);
    }
}
