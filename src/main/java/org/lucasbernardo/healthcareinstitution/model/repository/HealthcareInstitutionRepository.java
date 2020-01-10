package org.lucasbernardo.healthcareinstitution.model.repository;

import java.util.List;
import org.lucasbernardo.healthcareinstitution.model.HealthcareInstitution;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Lucas<sorackb@gmail.com>
 */
public interface HealthcareInstitutionRepository extends JpaRepository<HealthcareInstitution, Integer> {

  List<HealthcareInstitution> findByCnpj(String cnpj);
}
