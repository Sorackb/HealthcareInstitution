package org.lucasbernardo.healthcareinstitution.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import javax.validation.Valid;
import org.lucasbernardo.healthcareinstitution.model.HealthcareInstitution;
import org.lucasbernardo.healthcareinstitution.model.dto.HealthcareInstitutionDto;
import org.lucasbernardo.healthcareinstitution.service.HealthcareInstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Lucas<sorackb@gmail.com>
 */
@Api(tags = {"healthcareinstitutions"})
@SwaggerDefinition(tags = {
  @Tag(name = "healthcareinstitutions", description = "An API that will take control over the healthcare institution registration.")
})
@RestController
@RequestMapping("/healthcareinstitutions")
public class HealthcareInstitutionController {

  @Autowired
  private HealthcareInstitutionService healthcareInstitutionService;

  /**
   * Create a Healthcare Institution based on details provided.
   *
   * @param healthcareInstitution Detail of the Healthcare Institution to be
   * created
   * @return the Healthcare Institution that was createad
   */
  @PostMapping
  public HealthcareInstitutionDto createHealthcareInstitution(@Valid @RequestBody HealthcareInstitutionDto healthcareInstitution) {
    return this.healthcareInstitutionService.create(healthcareInstitution);
  }
}
