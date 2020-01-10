package org.lucasbernardo.healthcareinstitution.exception;

import io.jsonwebtoken.MalformedJwtException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Lucas<sorackb@gmail.com>
 */
@ControllerAdvice
@RestController
public class GlobalErrorHandler implements ErrorController {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });

    return errors;
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(BusinessRuleException.class)
  protected Map<String, String> handleBusinessRuleExceptions(BusinessRuleException ex) {
    return ex.toMap();
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(ResourceNotFoundException.class)
  protected Map<String, String> handleResourceNotFoundExceptions(ResourceNotFoundException ex) {
    return ex.toMap();
  }

  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(MalformedJwtException.class)
  protected Map<String, String> handleMalformedJwtExceptionExceptions(MalformedJwtException ex) {
    Map<String, String> errors = new HashMap<>();
    
    errors.put("error", ex.getMessage());

    return errors;
  }

  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ExceptionHandler(UnauthorizedException.class)
  protected Map<String, String> handleResourceUnauthorizedExceptions(UnauthorizedException ex) {
    return ex.toMap();
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(DataIntegrityViolationException.class)
  protected Map<String, String> handleDataIntegrityViolationExceptions(DataIntegrityViolationException ex) {
    Map<String, String> errors = new HashMap<>();

    errors.put("cnpj", ex.getRootCause().getMessage().replace("'UC_HEALTHCARE_INSTITUTION_CNPJ'", "'CNPJ'"));

    return errors;
  }

  @Override
  public String getErrorPath() {
    return null;
  }

}
