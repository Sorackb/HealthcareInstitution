package org.lucasbernardo.healthcareinstitution.controller;

import io.swagger.annotations.Api;
import javax.validation.Valid;
import org.lucasbernardo.healthcareinstitution.model.HealthcareInstitution;
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
@Api(tags = "healthcareinstitutions", description = "An API that will take control over the healthcare institution registration.")
@RestController
@RequestMapping("/healthcareinstitutions")
public class HealthcareInstitutionController {

  @Autowired
  private HealthcareInstitutionService healthcareInstitutionService;

  @PostMapping
  public HealthcareInstitution createHealthcareInstitution(@Valid @RequestBody HealthcareInstitution healthcareInstitution) {
    return this.healthcareInstitutionService.create(healthcareInstitution);
  }
}
