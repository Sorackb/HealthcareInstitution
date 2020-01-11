package org.lucasbernardo.healthcareinstitution.service;

import java.util.List;
import org.lucasbernardo.healthcareinstitution.exception.BusinessRuleException;
import org.lucasbernardo.healthcareinstitution.exception.ResourceNotFoundException;
import org.lucasbernardo.healthcareinstitution.model.HealthcareInstitution;
import org.lucasbernardo.healthcareinstitution.model.repository.HealthcareInstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Lucas<sorackb@gmail.com>
 */
@Service
public class HealthcareInstitutionService {

  @Autowired
  private TokenAuthenticationService tokenAuthenticationService;

  @Autowired
  private HealthcareInstitutionRepository healthcareInstitutionRepository;

  /**
   * Create a Healthcare Institution based on details provided.
   *
   * @param healthcareInstitution Detail of the Healthcare Institution to be
   * created
   * @return the Healthcare Institution that was createad
   */
  public HealthcareInstitution create(HealthcareInstitution healthcareInstitution) {
    String token;

    healthcareInstitution.setCnpj(healthcareInstitution.getCnpj().replaceAll("\\D", ""));
    token = tokenAuthenticationService.encode(healthcareInstitution.getCnpj());
    healthcareInstitution.setToken(token);
    healthcareInstitution.setVisibleToken(token);

    return this.healthcareInstitutionRepository.save(healthcareInstitution);
  }

  /**
   * Find a specific Healthcare Institution based on it's identifier.
   *
   * @param id The identifier of the Healthcare Institution
   * @return The Healthcare Institution finded
   */
  public HealthcareInstitution findById(Integer id) {
    return this.healthcareInstitutionRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("HealthcareInstitution", "id \"" + id + "\" not found."));
  }

  /**
   * Find a specific Healthcare Institution based on it's CNPJ.
   *
   * @param cnpj The CNPJ of the Healthcare Institution
   * @return The Healthcare Institution finded
   */
  public HealthcareInstitution findByCnpj(String cnpj) {
    List<HealthcareInstitution> healthcareInstitutions = this.healthcareInstitutionRepository
            .findByCnpj(cnpj);

    if (healthcareInstitutions.isEmpty()) {
      return null;
    }

    return healthcareInstitutions.get(0);
  }

  /**
   * Decrement the balance of the Healthcare Institution, checking if it has
   * budget.
   *
   * @param healthcareInstitution The Healthcare that will be charged
   * @return The updated Healthcare Institution
   */
  public HealthcareInstitution charge(HealthcareInstitution healthcareInstitution) {
    if (healthcareInstitution.getBalance() == 0) {
      throw new BusinessRuleException("HealthcareInstitution", "Out of budget.");
    }

    healthcareInstitution.charge();

    return this.healthcareInstitutionRepository.save(healthcareInstitution);
  }
}
