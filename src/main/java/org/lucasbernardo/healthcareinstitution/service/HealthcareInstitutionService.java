package org.lucasbernardo.healthcareinstitution.service;

import java.util.List;
import org.lucasbernardo.healthcareinstitution.exception.BusinessRuleException;
import org.lucasbernardo.healthcareinstitution.exception.ResourceNotFoundException;
import org.lucasbernardo.healthcareinstitution.exception.UnauthorizedException;
import org.lucasbernardo.healthcareinstitution.model.HealthcareInstitution;
import org.lucasbernardo.healthcareinstitution.model.repository.HealthcareInstitutionRepository;
import org.lucasbernardo.healthcareinstitution.tool.AuthenticationToken;
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
    String token;

    healthcareInstitution.setCnpj(healthcareInstitution.getCnpj().replaceAll("\\D", ""));
    token = AuthenticationToken.generateToken(healthcareInstitution.getCnpj());
    healthcareInstitution.setToken(token);
    healthcareInstitution.setVisibleToken(token);

    return this.healthcareInstitutionRepository.save(healthcareInstitution);
  }

  public HealthcareInstitution findById(Integer id) {
    return this.healthcareInstitutionRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("HealthcareInstitution", "id \"" + id + "\" not found."));
  }

  public HealthcareInstitution findByToken(String token) {
    List<HealthcareInstitution> healthcareInstitutions = this.healthcareInstitutionRepository
        .findByToken(token);

    if (healthcareInstitutions.isEmpty()) {
      throw new UnauthorizedException("error", "token \"" + token + "\" not found.");
    }

    return healthcareInstitutions.get(0);
  }

  public HealthcareInstitution charge(HealthcareInstitution healthcareInstitution) {
    if (healthcareInstitution.getBalance() == 0) {
      throw new BusinessRuleException("HealthcareInstitution", "Out of budget.");
    }

    healthcareInstitution.charge();

    return this.healthcareInstitutionRepository.save(healthcareInstitution);
  }
}
