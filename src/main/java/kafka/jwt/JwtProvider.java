package kafka.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import kafka.member.dto.MemberDTO;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    private final Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private final long expirationMs = 1000 * 60 * 60; // 1h

    public String createToken(Long id, String name, String role) {
        return Jwts.builder()
                .setSubject(String.valueOf(id))
                .claim("name", name)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMs))
                .signWith(secretKey)
                .compact();
    }

    public MemberDTO getMemberDTO(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Long id = Long.parseLong(claims.getSubject());
        String name = claims.get("name", String.class);
        String role = claims.get("role", String.class);

        return new MemberDTO(id, name, role);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            System.out.println("[ERROR] 만료된 토큰");
            return false;
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("[ERROR] 유효하지 않은 토큰");
            return false;
        }
    }

}
