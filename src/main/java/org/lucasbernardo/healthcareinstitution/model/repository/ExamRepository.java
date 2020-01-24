package org.lucasbernardo.healthcareinstitution.model.repository;

import org.lucasbernardo.healthcareinstitution.model.Exam;
import org.lucasbernardo.healthcareinstitution.model.HealthcareInstitution;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Lucas<sorackb@gmail.com>
 */
public interface ExamRepository extends JpaRepository<Exam, Integer> {

  Exam findOneByIdAndHealthcareInstitution(Integer id, HealthcareInstitution healthcareInstitution);
}
