package org.lucasbernardo.healthcareinstitution;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author Lucas<sorackb@gmail.com>
 */
@Configuration
public class HealthcareInstitutionConfig {

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }
}
