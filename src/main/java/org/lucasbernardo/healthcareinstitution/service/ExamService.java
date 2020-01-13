package org.lucasbernardo.healthcareinstitution.service;

import java.util.List;
import org.lucasbernardo.healthcareinstitution.exception.ResourceNotFoundException;
import org.lucasbernardo.healthcareinstitution.model.Exam;
import org.lucasbernardo.healthcareinstitution.model.repository.ExamRepository;
import org.lucasbernardo.healthcareinstitution.model.HealthcareInstitution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Lucas<sorackb@gmail.com>
 */
@Service
public class ExamService {

  @Autowired
  private HealthcareInstitutionService healthcareInstitutionService;
  @Autowired
  private ExamRepository examRepository;

  /**
   * Find a specific exam <span class="strong">without</span> charging the
   * Healthcare Institution.
   *
   * @param healthcareInstitution The owner of the Exam
   * @param id The identifier of the Exam
   * @return The Exam that was request
   */
  public Exam find(HealthcareInstitution healthcareInstitution, Integer id) {
    return this.find(healthcareInstitution, id, false);
  }

  /**
   * Find a specific exam.
   *
   * @param healthcareInstitution The owner of the Exam
   * @param id The identifier of the Exam
   * @param charge Determine if the search will charge the Healthcare
   * Institution
   * @return The Exam that was request
   */
  public Exam find(HealthcareInstitution healthcareInstitution, Integer id, Boolean charge) {
    List<Exam> exams = this.examRepository.findByIdAndHealthcareInstitution(id, healthcareInstitution);
    Exam exam;

    if (exams.isEmpty()) {
      throw new ResourceNotFoundException("Exam", "id \"" + id + "\" not found.");
    }

    exam = exams.get(0);

    if (Boolean.TRUE.equals(charge) && !exam.isCharged()) {
      this.healthcareInstitutionService.charge(healthcareInstitution);
      exam.setCharged(true);
      exam = this.examRepository.save(exam);
    }

    return exam;
  }

  /**
   * Create an Exam based on details provided.
   *
   * @param exam Detail of the Exam to be created
   * @param healthcareInstitution The Healthcare Institution who requested the
   * exam
   * @return The exam that was created
   */
  public Exam create(Exam exam, HealthcareInstitution healthcareInstitution) {
    healthcareInstitution = this.healthcareInstitutionService.charge(healthcareInstitution);
    exam.setHealthcareInstitution(healthcareInstitution);

    return this.examRepository.save(exam);
  }

  /**
   * Update an existent Exam based on details provided.
   *
   * @param examDetails Detail to update the Exam
   * @param healthcareInstitution The Healthcare Institution who requested the
   * exam
   * @param id The exam identifier
   * @return The exam that was updated
   */
  public Exam update(Exam examDetails, HealthcareInstitution healthcareInstitution, Integer id) {
    Exam exam = this.find(healthcareInstitution, id);

    exam.setPatientName(examDetails.getPatientName());
    exam.setPatientAge(examDetails.getPatientAge());
    exam.setPatientGender(examDetails.getPatientGender());
    exam.setPhysicianName(examDetails.getPhysicianName());
    exam.setPhysicianCRM(examDetails.getPhysicianCRM());
    exam.setProcedureName(examDetails.getProcedureName());

    return this.examRepository.save(exam);
  }

  /**
   * Delete an existent Exam.
   *
   * @param healthcareInstitution The Healthcare Institution who requested the exam
   * @param id The exam identifier
   * @return The exam that was requested
   */
  public Boolean delete(HealthcareInstitution healthcareInstitution, Integer id) {
    Exam exam = this.find(healthcareInstitution, id);

    this.examRepository.delete(exam);

    return true;

  }
}
