package org.lucasbernardo.healthcareinstitution.service;

import java.util.List;
import org.lucasbernardo.healthcareinstitution.exception.BusinessRuleException;
import org.lucasbernardo.healthcareinstitution.exception.UnauthorizedException;
import org.lucasbernardo.healthcareinstitution.model.HealthcareInstitution;
import org.lucasbernardo.healthcareinstitution.model.dto.HealthcareInstitutionDto;
import org.lucasbernardo.healthcareinstitution.model.repository.HealthcareInstitutionRepository;
import org.modelmapper.ModelMapper;
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

  @Autowired
  private ModelMapper modelMapper;

  /**
   * Check if the entered CNPJ has a entry on HealthcareInstitution
   *
   * @param cnpj The CNPJ of the Healthcare Institution
   * @return If the CNPJ é from a valid Healthcare Institution
   */
  public Boolean checkCnpj(String cnpj) {
    HealthcareInstitution result = this.findByCnpj(cnpj);

    if (result == null) {
      throw new UnauthorizedException("error", "The token entered is invalid.");
    }

    return true;
  }

  /**
   * Create a Healthcare Institution based on details provided.
   *
   * @param healthcareInstitutionDto Detail of the Healthcare Institution to be
   * created
   * @return the Healthcare Institution that was createad
   */
  public HealthcareInstitutionDto create(HealthcareInstitutionDto healthcareInstitutionDto) {
    HealthcareInstitution healthcareInstitution;
    HealthcareInstitutionDto result;    
    String token;

    healthcareInstitutionDto.setCnpj(healthcareInstitutionDto.getCnpj().replaceAll("\\D", ""));
    token = tokenAuthenticationService.encode(healthcareInstitutionDto.getCnpj());
    healthcareInstitution = this.modelMapper.map(healthcareInstitutionDto, HealthcareInstitution.class);
    healthcareInstitution.setToken(token);
    healthcareInstitution = this.healthcareInstitutionRepository.save(healthcareInstitution);
    result = this.modelMapper.map(healthcareInstitution, HealthcareInstitutionDto.class);
    result.setVisibleToken(token);

    return result;
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
