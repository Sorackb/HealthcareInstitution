package org.lucasbernardo.healthcareinstitution.service;

import java.util.List;
import org.lucasbernardo.healthcareinstitution.exception.ResourceNotFoundException;
import org.lucasbernardo.healthcareinstitution.model.Exam;
import org.lucasbernardo.healthcareinstitution.model.repository.ExamRepository;
import org.lucasbernardo.healthcareinstitution.model.HealthcareInstitution;
import org.lucasbernardo.healthcareinstitution.model.dto.ExamDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
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

  @Autowired
  private ModelMapper modelMapper;

  /**
   * Find a specific exam <span class="strong">without</span> charging the
   * Healthcare Institution.
   *
   * @param cnpj The Healthcare Institution's CNPJ who requested the exam
   * @param id The identifier of the Exam
   *
   * @return The Exam that was request
   */
  private Exam find(String cnpj, Integer id) {
    return this.find(cnpj, id, false);
  }

  /**
   * Find a specific exam.
   *
   * @param cnpj The Healthcare Institution's CNPJ who requested the exam
   * @param id The identifier of the Exam
   * @param charge Determine if the search will charge the Healthcare
   * Institution
   *
   * @return The Exam that was request
   */
  private Exam find(String cnpj, Integer id, Boolean charge) {
    HealthcareInstitution healthcareInstitution = this.healthcareInstitutionService.findByCnpj(cnpj);
    List<Exam> exams = this.examRepository.findByIdAndHealthcareInstitution(id, healthcareInstitution);
    Exam exam;

    if (exams.isEmpty()) {
      throw new ResourceNotFoundException("Exam", "id \"" + id + "\" not found.");
    }

    exam = exams.get(0);

    if (Boolean.TRUE.equals(charge) && Boolean.FALSE.equals(exam.isCharged())) {
      this.healthcareInstitutionService.charge(healthcareInstitution);
      exam.setCharged(true);
      exam = this.examRepository.save(exam);
    }

    return exam;
  }

  /**
   * Find a specific exam.
   *
   * @param cnpj The Healthcare Institution's CNPJ who requested the exam
   * @param id The identifier of the Exam
   *
   * @return The Exam that was request
   */
  public ExamDto paidFind(String cnpj, Integer id) {
    ExamDto result;
    Exam exam;

    exam = this.find(cnpj, id, true);
    result = this.modelMapper.map(exam, ExamDto.class);

    return result;
  }

  /**
   * Create an Exam based on details provided.
   *
   * @param examDto Detail of the Exam to be created
   * @param cnpj The Healthcare Institution's CNPJ who requested the exam
   *
   * @return The exam that was created
   */
  public ExamDto create(String cnpj, ExamDto examDto) {
    HealthcareInstitution healthcareInstitution = this.healthcareInstitutionService.findByCnpj(cnpj);
    healthcareInstitution = this.healthcareInstitutionService.charge(healthcareInstitution);
    ExamDto result;
    Exam exam;

    exam = this.modelMapper.map(examDto, Exam.class);
    exam.setHealthcareInstitution(healthcareInstitution);
    exam = this.examRepository.save(exam);
    result = this.modelMapper.map(exam, ExamDto.class);

    return result;
  }

  /**
   * Update an existent Exam based on details provided.
   *
   * @param examDetails Detail to update the Exam
   * @param cnpj The Healthcare Institution's CNPJ who requested the exam
   * @param id The exam identifier
   *
   * @return The exam that was updated
   */
  public ExamDto update(String cnpj, ExamDto examDetails, Integer id) {
    Exam exam = this.find(cnpj, id);
    ExamDto result;

    exam.setPatientName(examDetails.getPatientName());
    exam.setPatientAge(examDetails.getPatientAge());
    exam.setPatientGender(examDetails.getPatientGender());
    exam.setPhysicianName(examDetails.getPhysicianName());
    exam.setPhysicianCRM(examDetails.getPhysicianCRM());
    exam.setProcedureName(examDetails.getProcedureName());
    exam = this.examRepository.save(exam);
    result = this.modelMapper.map(exam, ExamDto.class);

    return result;
  }

  /**
   * Delete an existent Exam.
   *
   * @param cnpj The Healthcare Institution's CNPJ who requested the exam
   * @param id The exam identifier
   *
   * @return The exam that was requested
   */
  public Boolean delete(String cnpj, Integer id) {
    Exam exam = this.find(cnpj, id);

    this.examRepository.delete(exam);

    return true;

  }
}
