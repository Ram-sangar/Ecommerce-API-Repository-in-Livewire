package com.SpringBoot.EcommerceSiteProject.Configuration;

import com.SpringBoot.EcommerceSiteProject.User.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class JwtUtils {

    private static final String SECRET_KEY = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    public String extractEmail(String token) {
        return extractClaim(token).get("email").toString();
    }

    public Long extractUserId(String token) {
        return Long.valueOf(extractClaim(token).get("userId").toString());
    }

    public Collection<? extends GrantedAuthority> extractRole(String token) {
        Object roleClaim = extractClaim(token).get("role");
        System.out.println("test2");

        if (roleClaim instanceof List<?>) {
            @SuppressWarnings("unchecked")
            List<HashMap<String,String>> roles = (List<HashMap<String,String>>) roleClaim;
            return roles.stream()
                    .map(x-> new SimpleGrantedAuthority(x.get("authority")))
                    .collect(Collectors.toList());
        } else {
            return List.of(new SimpleGrantedAuthority((String) roleClaim));
        }
    }


    public String generateToken(User userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String generateToken(
            Map<String, Object> extraClaims,
            User userDetails
    ) {
        extraClaims.put("email", userDetails.getEmail());
        extraClaims.put("userId", userDetails.getId());
        extraClaims.put("role",userDetails.getAuthorities());
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 10000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) throws Exception {
        final String username = extractEmail(token);
        boolean isTokenExpired = isTokenExpired(token);
        if(isTokenExpired){
            throw new Exception("Token is Expired");
        }
        return (username.equals(userDetails.getUsername())) && !isTokenExpired;
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return  extractClaim(token).getExpiration();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public Claims extractClaim(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build()
                .parseClaimsJws(token).getBody();
    }



}
