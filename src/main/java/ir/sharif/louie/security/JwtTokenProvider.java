package ir.sharif.louie.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import ir.sharif.louie.models.UserPrincipal;
import ir.sharif.louie.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {
    private final CustomUserDetailsService userDetailsService;
    private final Key key;
    private final long validityInMs;


    public JwtTokenProvider(
            CustomUserDetailsService userDetailsService,
            @Value("${jwt.secret}") String secretKey,
            @Value("${jwt.expiration-ms}") long validityInMs
    ) {
        this.userDetailsService = userDetailsService;
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.validityInMs = validityInMs;
    }

    public String generateToken(Authentication authentication) {
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        Date now = new Date();
        Date expiry = new Date(now.getTime() + validityInMs);

        return Jwts.builder()
                .setSubject(userPrincipal.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            // invalid or expired
            return false;
        }
    }

    public String getUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public UserDetails getUserDetailsFromToken(String token) {
        String username = getUsername(token);
        return userDetailsService.loadUserByUsername(username);
    }
}
