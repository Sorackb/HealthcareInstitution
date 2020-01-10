package org.lucasbernardo.healthcareinstitution.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Base64;
import org.lucasbernardo.healthcareinstitution.exception.UnauthorizedException;
import org.lucasbernardo.healthcareinstitution.model.HealthcareInstitution;
import org.springframework.beans.factory.annotation.Autowired;
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

  @Autowired
  private HealthcareInstitutionService healthcareInstitutionService;

  public String encode(String cnpj) {
    Claims claims = Jwts.claims();

    claims.put("cnpj", cnpj);

    return Jwts.builder()
        .setClaims(claims)
        .signWith(SignatureAlgorithm.HS512, this.encodeSecret())
        .compact();
  }

  public HealthcareInstitution getOwner(String token) {
    HealthcareInstitution result = this.healthcareInstitutionService.findByCnpj(this.decode(token));

    if (result == null) {
      throw new UnauthorizedException("error", "token \"" + token + "\" not found.");
    }

    return result;
  }

  private String decode(String token) {
    return (String) Jwts.parser()
        .setSigningKey(this.encodeSecret())
        .parseClaimsJws(token)
        .getBody()
        .get("cnpj");
  }

  private String encodeSecret() {
    return Base64.getEncoder().encodeToString(this.secret.getBytes());
  }
}
