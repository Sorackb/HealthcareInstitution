package org.lucasbernardo.healthcareinstitution.exception;

/**
 *
 * @author Lucas<sorackb@gmail.com>
 */
public class UnauthorizedException extends CustomException {

  public UnauthorizedException(String target, String message) {
    super(target, message);
  }
}
