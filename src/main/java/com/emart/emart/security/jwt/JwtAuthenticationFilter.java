package com.emart.emart.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Configuration @RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String jwt;
        final String username;

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            try {
                filterChain.doFilter(request, response);
            } catch (JwtException e) {
                e.printStackTrace();
                setErrorResponse(HttpStatus.BAD_REQUEST, response, e);
            } catch (RuntimeException e) {
                e.printStackTrace();
                setErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, response, e);
            }
            return;
        }

        jwt = authHeader.substring("Bearer ".length());
        username = jwtService.extractUsername(jwt);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtService.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } else {
                setErrorResponse(HttpStatus.UNAUTHORIZED, response, new Exception("Token invalid or expired"));
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private void setErrorResponse(HttpStatus status, HttpServletResponse response, Throwable exception) {
        response.setStatus(status.value());
        response.setContentType(APPLICATION_JSON_VALUE);

        try {
            String jsonOutput = new ObjectMapper().writeValueAsString(
                    Map.of(
                            "error", status.getReasonPhrase(),
                            "message", exception.getMessage()
                    )
            );
            response.getWriter().write(jsonOutput);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
