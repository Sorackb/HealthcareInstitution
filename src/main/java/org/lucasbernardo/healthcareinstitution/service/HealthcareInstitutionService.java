package org.lucasbernardo.healthcareinstitution.service;

import org.lucasbernardo.healthcareinstitution.exception.BusinessRuleException;
import org.lucasbernardo.healthcareinstitution.exception.ResourceNotFoundException;
import org.lucasbernardo.healthcareinstitution.model.HealthcareInstitution;
import org.lucasbernardo.healthcareinstitution.model.HealthcareInstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Lucas<sorackb@gmail.com>
 */
@Service
public class HealthcareInstitutionService {

  @Autowired
  private HealthcareInstitutionRepository healthcareInstitutionRepository;

  public HealthcareInstitution create(HealthcareInstitution healthcareInstitution) {
    return this.healthcareInstitutionRepository.save(healthcareInstitution);
  }

  public HealthcareInstitution findById(Integer id) {
    return this.healthcareInstitutionRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("HealthcareInstitution", id + " not found."));
  }

  public HealthcareInstitution charge(Integer id) {
    return this.charge(this.findById(id));
  }

  public HealthcareInstitution charge(HealthcareInstitution healthcareInstitution) {
    if (healthcareInstitution.getBalance() == 0) {
      throw new BusinessRuleException("HealthcareInstitution", "Out of budget");
    }

    healthcareInstitution.charge();

    return this.healthcareInstitutionRepository.save(healthcareInstitution);
  }
}
