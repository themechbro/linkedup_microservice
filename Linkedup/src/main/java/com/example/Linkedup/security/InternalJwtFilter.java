// package com.example.Linkedup.security;

// import io.jsonwebtoken.Claims;
// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.JwtException;
// import io.jsonwebtoken.security.Keys;
// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.http.HttpStatus;
// import org.springframework.stereotype.Component;
// import org.springframework.web.filter.OncePerRequestFilter;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;


// import java.io.IOException;

// @Component
// public class InternalJwtFilter extends OncePerRequestFilter {

//     @Value("${jwt.internal.secret}")
//     private String secret;

//     @Override
//     protected void doFilterInternal(
//         HttpServletRequest request,
//         HttpServletResponse response,
//         FilterChain filterChain
//     ) throws ServletException, IOException {

//         String auth = request.getHeader("Authorization");

//         if (auth == null || !auth.startsWith("Bearer ")) {
//             response.setStatus(HttpStatus.UNAUTHORIZED.value());
//             return;
//         }

//         String token = auth.substring(7);

//         try {
//             Claims claims = Jwts.parserBuilder()
//                 .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
//                 .build()
//                 .parseClaimsJws(token)
//                 .getBody();

//             if (!"auth-monolith".equals(claims.getIssuer()) ||
//                 !"internal".equals(claims.get("type"))) {
//                 response.setStatus(HttpStatus.FORBIDDEN.value());
//                 return;
//             }

//             // Store trusted user ID for controller
//             request.setAttribute("internalUserId", claims.getSubject());

//         } catch (JwtException e) {
//             response.setStatus(HttpStatus.UNAUTHORIZED.value());
//             return;
//         }

//         filterChain.doFilter(request, response);
//     }
//   @Override
// protected boolean shouldNotFilter(HttpServletRequest request) {

//     String path = request.getRequestURI();
//     String method = request.getMethod();

//     // Always skip preflight
//     if ("OPTIONS".equalsIgnoreCase(method)) {
//         return true;
//     }

//     // Only protect the write endpoint
//     return !(
//         "POST".equalsIgnoreCase(method)
//         && path.startsWith("/api/profile/update-industry")
//     );
// }

// }


package com.example.Linkedup.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;
import java.util.List;

@Component
public class InternalJwtFilter extends OncePerRequestFilter {

    @Value("${jwt.internal.secret}")
    private String secret;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        String token = authHeader.substring(7);

        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            // Validate issuer & token type
            if (!"auth-monolith".equals(claims.getIssuer()) ||
                !"internal".equals(claims.get("type"))) {
                response.setStatus(HttpStatus.FORBIDDEN.value());
                return;
            }

            // ✅ Tell Spring Security the request is authenticated
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            claims.getSubject(),               // principal (user_id)
                            null,
                            List.of(new SimpleGrantedAuthority("ROLE_INTERNAL"))
                    );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Optional: still useful for controllers
            request.setAttribute("internalUserId", claims.getSubject());

        } catch (JwtException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {

        String method = request.getMethod();
        String path = request.getRequestURI();

        // Skip CORS preflight
        if ("OPTIONS".equalsIgnoreCase(method)) {
            return true;
        }

        // Only protect the internal write endpoint
        return !(
                "POST".equalsIgnoreCase(method)
                && path.startsWith("/api/profile/update-industry")||
                path.startsWith("/api/profile/update-website")||
                path.startsWith("/api/profile/update-about")||
                path.startsWith("/api/profile/update-companyhq")
        );
    }
}
