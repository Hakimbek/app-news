package uz.pdp.appnews.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import uz.pdp.appnews.entity.Role;
import uz.pdp.appnews.entity.enums.Permission;

import java.util.Date;
import java.util.Set;

@Component
public class JwtProvider {
    private final long expiredTime = 1_000_000;
    private final String secret = "secretKey";

    // generate jwt token
    public String generateToken(String username, Role role) {
        return Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiredTime))
                .signWith(SignatureAlgorithm.HS512, secret)
                .claim("role", role.getName())
                .compact();
    }

    // get username from token
    public String getUsernameFromToken(String token) {
        try {
            return Jwts
                    .parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            return null;
        }
    }
}
