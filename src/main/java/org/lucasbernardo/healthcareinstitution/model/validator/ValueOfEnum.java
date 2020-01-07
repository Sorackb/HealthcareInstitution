package org.lucasbernardo.healthcareinstitution.model.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 *
 * @author Lucas<sorackb@gmail.com>
 */
@Target({FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = ValueOfEnumValidator.class)
public @interface ValueOfEnum {

  Class<? extends Enum<?>> enumClass();

  String message() default "{uk.dds.ideskos.validator.ValidateString.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
