package org.lucasbernardo.healthcareinstitution.tool;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author Lucas<sorackb@gmail.com>
 */
public class AuthenticationToken {

  public static String generateToken(String data) {
    return new BCryptPasswordEncoder().encode(data);
  }
}
