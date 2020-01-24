package org.lucasbernardo.healthcareinstitution.model.repository;

import org.lucasbernardo.healthcareinstitution.model.HealthcareInstitution;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Lucas<sorackb@gmail.com>
 */
public interface HealthcareInstitutionRepository extends JpaRepository<HealthcareInstitution, Integer> {

  HealthcareInstitution findOneByCnpj(String cnpj);
}
