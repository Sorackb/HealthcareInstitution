package org.lucasbernardo.healthcareinstitution.model.validator;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author Lucas<sorackb@gmail.com>
 */
public class ValueOfEnumValidator implements ConstraintValidator<ValueOfEnum, CharSequence> {

  private List<String> acceptedValues;

  @Override
  public void initialize(ValueOfEnum annotation) {
    this.acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
        .map(Enum::name)
        .collect(Collectors.toList());
  }

  @Override
  public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
    if (value == null) {
      return true;
    }

    return this.acceptedValues.contains(value.toString());
  }
}
