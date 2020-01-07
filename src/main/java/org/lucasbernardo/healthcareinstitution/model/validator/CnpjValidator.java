package org.lucasbernardo.healthcareinstitution.model.validator;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author Prof. Omero Francisco Bertol<http://paginapessoal.utfpr.edu.br/omero>
 */
public class CnpjValidator implements ConstraintValidator<Cnpj, String> {
  
  private final List<String> invalid = Arrays.asList("00000000000000", "11111111111111",
          "22222222222222", "33333333333333",
          "44444444444444", "55555555555555",
          "66666666666666", "77777777777777",
          "88888888888888", "99999999999999");

  public boolean isCnpj(String cnpj) {
    cnpj = cnpj.replaceAll("\\D", "");
    
    // considera-se erro Cnpj's formados por uma sequencia de numeros iguais
    if (this.invalid.contains(cnpj) || (cnpj.length() != 14)) {
      return false;
    }

    char dig13, dig14;
    int sm, i, r, num, weight;

    // "try" - protege o código para eventuais erros de conversao de tipo (int)
    try {
      // Calculo do 1o. Digito Verificador
      sm = 0;
      weight = 2;
      // converte o i-ésimo caractere do Cnpj em um número:
      // por exemplo, transforma o caractere '0' no inteiro 0
      // (48 eh a posição de '0' na tabela ASCII)
      for (i = 11; i >= 0; i--) {
        num = (int) (cnpj.charAt(i) - 48);
        sm = sm + (num * weight);
        weight = weight + 1;
        if (weight == 10) {
          weight = 2;
        }
      }

      r = sm % 11;
      if ((r == 0) || (r == 1)) {
        dig13 = '0';
      } else {
        dig13 = (char) ((11 - r) + 48);
      }

      // Calculo do 2o. Digito Verificador
      sm = 0;
      weight = 2;

      for (i = 12; i >= 0; i--) {
        num = (int) (cnpj.charAt(i) - 48);
        sm = sm + (num * weight);
        weight = weight + 1;
        if (weight == 10) {
          weight = 2;
        }
      }

      r = sm % 11;
      if ((r == 0) || (r == 1)) {
        dig14 = '0';
      } else {
        dig14 = (char) ((11 - r) + 48);
      }

      // Verifica se os dígitos calculados conferem com os dígitos informados.
      if ((dig13 == cnpj.charAt(12)) && (dig14 == cnpj.charAt(13))) {
        return true;
      } else {
        return false;
      }
    } catch (InputMismatchException ex) {
      return false;
    }
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return this.isCnpj(value);
  }
}
