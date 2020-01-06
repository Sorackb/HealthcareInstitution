package org.lucasbernardo.healthcareinstitution.controller;

import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.lucasbernardo.healthcareinstitution.exception.BusinessRuleException;
import org.lucasbernardo.healthcareinstitution.exception.ResourceNotFoundException;
import org.lucasbernardo.healthcareinstitution.model.Exam;
import org.lucasbernardo.healthcareinstitution.model.HealthcareInstitution;
import org.lucasbernardo.healthcareinstitution.service.ExamService;
import org.lucasbernardo.healthcareinstitution.service.HealthcareInstitutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Lucas<sorackb@gmail.com>
 */
@RestController
@RequestMapping("/healthcareinstitution")
public class HealthcareInstitutionController {

  @Autowired
  private HealthcareInstitutionService healthcareInstitutionService;
  @Autowired
  private ExamService examService;

  @PostMapping("/")
  public HealthcareInstitution createHealthcareInstitution(@Valid @RequestBody HealthcareInstitution healthcareInstitution) {
    return this.healthcareInstitutionService.create(healthcareInstitution);
  }

  @PostMapping("/{id}/exam")
  public Exam createExam(
          @PathVariable(value = "id") Integer healthcareInstitutionId,
          @Valid @RequestBody Exam exam) {
    return this.examService.create(exam, healthcareInstitutionId);
  }

  @PutMapping("/{healthcare_institution_id}/exam/{id}")
  public ResponseEntity<Exam> updateExam(
          @PathVariable(value = "healthcare_institution_id") Integer healthcareInstitutionId,
          @PathVariable(value = "id") Integer examId,
          @Valid @RequestBody Exam examDetails) {
    return ResponseEntity.ok(this.examService.update(examDetails, healthcareInstitutionId, examId));
  }

  @DeleteMapping("/{healthcare_institution_id}/exam/{id}")
  public Map<String, Boolean> deleteExam(
          @PathVariable(value = "healthcare_institution_id") Integer healthcareInstitutionId,
          @PathVariable(value = "id") Integer examId) {
    Map<String, Boolean> response = new HashMap<>();

    response.put("deleted", this.examService.delete(healthcareInstitutionId, examId));

    return response;
  }

  @GetMapping("/{healthcare_institution_id}/exam/{id}")
  public ResponseEntity<Exam> getExam(
          @PathVariable(value = "healthcare_institution_id") Integer healthcareInstitutionId,
          @PathVariable(value = "id") Integer examId) {
    return ResponseEntity.ok().body(this.examService.find(healthcareInstitutionId, examId));
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();

    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });

    return errors;
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(BusinessRuleException.class)
  protected Map<String, String> handleBusinessRuleExceptions(BusinessRuleException ex) {
    return ex.toMap();
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(ResourceNotFoundException.class)
  protected Map<String, String> handleResourceNotFoundExceptions(ResourceNotFoundException ex) {
    return ex.toMap();
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(DataIntegrityViolationException.class)
  protected Map<String, String> handleDataIntegrityViolationExceptions(DataIntegrityViolationException ex) {
    Map<String, String> errors = new HashMap<>();

    errors.put("cnpj", ex.getRootCause().getMessage().replace("'UC_HEALTHCARE_INSTITUTION_CNPJ'", "'CNPJ'"));

    return errors;
  }
}
