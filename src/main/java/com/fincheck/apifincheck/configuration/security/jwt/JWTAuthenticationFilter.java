package com.fincheck.apifincheck.configuration.security.jwt;

import com.fincheck.apifincheck.model.AuthenticatedUser;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {
  private final JWTService jwtService;
  private final UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain
  ) throws ServletException, IOException {
    final String authHeader = request.getHeader("Authorization");

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    final String jwt;
    final String username;

    try {
      jwt = authHeader.substring(7);
      username = jwtService.extractUsername(jwt);

      if (username != null  && SecurityContextHolder.getContext().getAuthentication() == null) {
        AuthenticatedUser userDetails =
          (AuthenticatedUser) userDetailsService.loadUserByUsername(username);

        if (jwtService.isTokenValid(jwt, userDetails)) {
          UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
            userDetails.getUsername(),
            null,
            Collections.emptyList()
          );

          authToken.setDetails(
            new WebAuthenticationDetailsSource().buildDetails(request)
          );

          SecurityContextHolder.getContext().setAuthentication(authToken);
        } else {
          response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access denied");
        }
      }
    } catch (ExpiredJwtException e) {
      response.setHeader("X-Token-Expired", "true");
    }

    filterChain.doFilter(request, response);
  }
}
