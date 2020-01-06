package org.lucasbernardo.healthcareinstitution.exception;

/**
 *
 * @author Lucas<sorackb@gmail.com>
 */
public class ResourceNotFoundException extends CustomException {

  public ResourceNotFoundException(String target, String message) {
    super(target, message);
  }

}
