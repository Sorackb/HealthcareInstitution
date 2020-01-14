package org.lucasbernardo.healthcareinstitution.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 *
 * @author Lucas<sorackb@gmail.com>
 */
@Service
public class TokenAuthenticationService {

  @Value("${jwt.secret}")
  private String secret;

  /**
   * Encode an CNPJ to JWT Token.
   *
   * @param cnpj CNPJ to be encoded
   * @return The JWT Token
   */
  public String encode(String cnpj) {
    Claims claims = Jwts.claims();

    claims.put("cnpj", cnpj);

    return Jwts.builder()
            .setClaims(claims)
            .signWith(SignatureAlgorithm.HS512, this.encodeSecret())
            .compact();
  }

  /**
   * Decode a token to a CNPJ
   *
   * @param token The token to be decoded
   * @return The CNPJ related with the token
   */
  public String decode(String token) {
    return (String) Jwts.parser()
            .setSigningKey(this.encodeSecret())
            .parseClaimsJws(token)
            .getBody()
            .get("cnpj");
  }

  /**
   * Convert the secret to a Base-64 text.
   *
   * @return The converted secret
   */
  private String encodeSecret() {
    return Base64.getEncoder().encodeToString(this.secret.getBytes());
  }
}
