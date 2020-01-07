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
  
  private Exam find(HealthcareInstitution healthcareInstitution, Integer id) {
    List<Exam> exams = this.examRepository.findByIdAndHealthcareInstitution(id, healthcareInstitution);
    
    if (exams.isEmpty()) {
      throw new ResourceNotFoundException("Exam", "id \"" + id + "\" not found.");
    }
    
    return exams.get(0);
  }
  
  public Exam find(Integer healthcareInstitutionId, Integer id) {
    HealthcareInstitution healthcareInstitution = this.healthcareInstitutionService.findById(healthcareInstitutionId);
    Exam exam = this.find(healthcareInstitution, id);
    
    if (!exam.isCharged()) {
      this.healthcareInstitutionService.charge(healthcareInstitution);
      exam.setCharged(true);
      exam = this.examRepository.save(exam);
    }
    
    return exam;
  }
  
  public Exam create(Exam exam, Integer healthcareInstitutionId) {
    HealthcareInstitution healthcareInstitution = this.healthcareInstitutionService.charge(healthcareInstitutionId);
    
    exam.setHealthcareInstitution(healthcareInstitution);
    
    return this.examRepository.save(exam);
  }
  
  public Exam update(Exam examDetails, Integer healthcareInstitutionId, Integer id) {
    HealthcareInstitution healthcareInstitution = this.healthcareInstitutionService.findById(healthcareInstitutionId);
    Exam exam = this.find(healthcareInstitution, id);
    
    exam.setPatientName(examDetails.getPatientName());
    exam.setPatientAge(examDetails.getPatientAge());
    exam.setPatientGender(examDetails.getPatientGender());
    exam.setPhysicianName(examDetails.getPhysicianName());
    exam.setPhysicianCRM(examDetails.getPhysicianCRM());
    exam.setProcedureName(examDetails.getProcedureName());
    
    return this.examRepository.save(exam);
  }
  
  public Boolean delete(Integer healthcareInstitutionId, Integer id) {
    HealthcareInstitution healthcareInstitution = this.healthcareInstitutionService.findById(healthcareInstitutionId);
    Exam exam = this.find(healthcareInstitution, id);
    
    this.examRepository.delete(exam);
    
    return true;
    
  }
}
