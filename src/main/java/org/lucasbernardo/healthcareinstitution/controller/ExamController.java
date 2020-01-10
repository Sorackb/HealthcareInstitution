package org.lucasbernardo.healthcareinstitution.controller;

import io.swagger.annotations.Api;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.lucasbernardo.healthcareinstitution.model.Exam;
import org.lucasbernardo.healthcareinstitution.model.HealthcareInstitution;
import org.lucasbernardo.healthcareinstitution.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 *
 * @author Lucas<sorackb@gmail.com>
 */
@Api(tags = "exams", description = "An API that will take control over the exam ingest.")
@RestController
@RequestMapping("/exams")
public class ExamController {

  @Autowired
  private ExamService examService;

  /**
   * Create an Exam based on details provided from the owner's Healthcare
   * Institution.
   *
   * @param owner The Healthcare Institution who requested the exam
   * @param exam Detail of the Exam to be created
   * @return The exam that was created
   */
  @PostMapping
  public Exam createExam(
          @ApiIgnore @RequestAttribute HealthcareInstitution owner,
          @Valid @RequestBody Exam exam) {
    return this.examService.create(exam, owner);
  }

  /**
   * Update an existent Exam based on details provided from the owner's
   * Healthcare Institution.
   *
   * @param owner The Healthcare Institution who requested the exam
   * @param examId The exam identifier
   * @param exam Detail to update the Exam
   * @return The exam that was updated
   */
  @PutMapping("/{id}")
  public ResponseEntity<Exam> updateExam(
          @ApiIgnore @RequestAttribute HealthcareInstitution owner,
          @PathVariable(value = "id") Integer examId,
          @Valid @RequestBody Exam exam) {
    return ResponseEntity.ok(this.examService.update(exam, owner, examId));
  }

  /**
   * Delete an existent Exam.
   *
   * @param owner The Healthcare Institution who requested the exam
   * @param examId The exam identifier
   * @return A message telling if the Exam was sucessfully deleted
   */
  @DeleteMapping("/{id}")
  public Map<String, Boolean> deleteExam(
          @ApiIgnore @RequestAttribute HealthcareInstitution owner,
          @PathVariable(value = "id") Integer examId) {
    Map<String, Boolean> response = new HashMap<>();

    response.put("deleted", this.examService.delete(owner, examId));

    return response;
  }

  /**
   * Retrieve an specific Exam previously created.
   *
   * @param owner The Healthcare Institution who requested the exam
   * @param examId The exam identifier
   * @return The exam that was requested
   */
  @GetMapping("/{id}")
  public ResponseEntity<Exam> getExam(
          @ApiIgnore @RequestAttribute HealthcareInstitution owner,
          @PathVariable(value = "id") Integer examId) {
    return ResponseEntity.ok().body(this.examService.find(owner, examId, true));
  }
}
