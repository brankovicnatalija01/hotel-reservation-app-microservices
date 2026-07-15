package com.hotelapp.reviewservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Key;

// review-service has no user database.
// Role is extracted directly from the JWT claim set by user-service at login time.
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Value("${hotel.app.jwtSecret}")
    private String jwtSecret;

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(
                java.util.Base64.getEncoder().encodeToString(jwtSecret.getBytes())));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return new UserDetailsImpl(0L, email, "", "ROLE_USER");
    }

    public UserDetails loadUserFromToken(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key()).build()
                .parseClaimsJws(token).getBody();
        String email = claims.getSubject();
        String role = claims.get("role", String.class);
        if (role == null) role = "ROLE_USER";
        return new UserDetailsImpl(0L, email, "", role);
    }
}