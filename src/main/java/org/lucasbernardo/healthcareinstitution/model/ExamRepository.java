package org.lucasbernardo.healthcareinstitution.model;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Lucas<sorackb@gmail.com>
 */
public interface ExamRepository extends JpaRepository<Exam, Integer> {
  List<Exam> findByIdAndHealthcareInstitution(Integer id, HealthcareInstitution healthcareInstitution);
}
