package org.lucasbernardo.healthcareinstitution.exception;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Lucas<sorackb@gmail.com>
 */
public abstract class CustomException extends RuntimeException {

  private final String target;
  private final String message;

  public CustomException(String target, String message) {
    this.target = target;
    this.message = message;
  }

  public String getTarget() {
    return this.target;
  }

  @Override
  public String getMessage() {
    return this.message;
  }

  public Map<String, String> toMap() {
    Map<String, String> errors = new HashMap<>();

    errors.put(this.getTarget(), this.getMessage());

    return errors;
  }
}
