package org.lucasbernardo.healthcareinstitution.model.repository;

import java.util.List;
import org.lucasbernardo.healthcareinstitution.model.Exam;
import org.lucasbernardo.healthcareinstitution.model.HealthcareInstitution;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Lucas<sorackb@gmail.com>
 */
public interface ExamRepository extends JpaRepository<Exam, Integer> {
  List<Exam> findByIdAndHealthcareInstitution(Integer id, HealthcareInstitution healthcareInstitution);
}
