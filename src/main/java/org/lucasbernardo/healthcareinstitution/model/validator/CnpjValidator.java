package org.lucasbernardo.healthcareinstitution.model.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import java.util.regex.Pattern;

/**
 *
 * @author Lucas Bernardo<sorackb@gmail.com>
 */
public class CnpjValidator implements ConstraintValidator<Cnpj, String> {
  
  public static final Pattern UNFORMATED = Pattern.compile("\\d{14}");

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }

    try {
      CNPJValidator cnpjValidator = new CNPJValidator(Boolean.FALSE.equals(UNFORMATED.matcher(value).matches()));

      cnpjValidator.assertValid(value);
      return true;
    } catch (InvalidStateException ex) {
      return false;
    }
  }
}
