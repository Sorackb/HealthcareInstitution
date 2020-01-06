package org.lucasbernardo.healthcareinstitution.exception;

/**
 *
 * @author Lucas<sorackb@gmail.com>
 */
public class BusinessRuleException extends CustomException {

  public BusinessRuleException(String target, String message) {
    super(target, message);
  }

}
