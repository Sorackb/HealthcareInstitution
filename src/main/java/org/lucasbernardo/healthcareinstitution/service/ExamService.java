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

  public Exam find(HealthcareInstitution healthcareInstitution, Integer id) {
    return this.find(healthcareInstitution, id, false);
  }

  public Exam find(HealthcareInstitution healthcareInstitution, Integer id, Boolean charge) {
    List<Exam> exams = this.examRepository.findByIdAndHealthcareInstitution(id, healthcareInstitution);
    Exam exam;

    if (exams.isEmpty()) {
      throw new ResourceNotFoundException("Exam", "id \"" + id + "\" not found.");
    }

    exam = exams.get(0);

    if (charge && !exam.isCharged()) {
      this.healthcareInstitutionService.charge(healthcareInstitution);
      exam.setCharged(true);
      exam = this.examRepository.save(exam);
    }

    return exam;
  }

  public Exam create(Exam exam, HealthcareInstitution healthcareInstitution) {
    healthcareInstitution = this.healthcareInstitutionService.charge(healthcareInstitution);
    exam.setHealthcareInstitution(healthcareInstitution);

    return this.examRepository.save(exam);
  }

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

  public Boolean delete(HealthcareInstitution healthcareInstitution, Integer id) {
    Exam exam = this.find(healthcareInstitution, id);

    this.examRepository.delete(exam);

    return true;

  }
}
