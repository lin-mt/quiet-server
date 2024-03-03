package cn.linmt.quiet.service;

import cn.linmt.quiet.config.properties.ServerProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureAlgorithm;
import java.security.KeyPair;
import java.util.Date;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtService {

  private final ServerProperties serverProperties;

  private final SignatureAlgorithm alg = Jwts.SIG.RS256;

  private final KeyPair pair = alg.keyPair().build();

  public String generateToken(String username, Long userId) {
    JwtBuilder expiration =
        Jwts.builder()
            .subject(username)
            .id(userId.toString())
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 30));
    if (Boolean.FALSE.equals(serverProperties.getDev())) {
      // 开发环境不进行签名
      expiration.signWith(pair.getPrivate(), alg);
    }
    return expiration.compact();
  }

  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    JwtParserBuilder parser = Jwts.parser();
    boolean isDev = Boolean.TRUE.equals(serverProperties.getDev());
    if (isDev) {
      parser.unsecured();
    } else {
      parser.verifyWith(pair.getPublic());
    }
    JwtParser build = parser.build();
    if (isDev) {
      return build.parseUnsecuredClaims(token).getPayload();
    } else {
      return build.parseSignedClaims(token).getPayload();
    }
  }

  private Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  public Boolean validateToken(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }
}
