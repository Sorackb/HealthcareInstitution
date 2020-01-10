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

  @PostMapping("/")
  public Exam createExam(
      @ApiIgnore @RequestAttribute HealthcareInstitution owner,
      @Valid @RequestBody Exam exam) {
    return this.examService.create(exam, owner);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Exam> updateExam(
      @ApiIgnore  @RequestAttribute HealthcareInstitution owner,
      @PathVariable(value = "id") Integer examId,
      @Valid @RequestBody Exam exam) {
    return ResponseEntity.ok(this.examService.update(exam, owner, examId));
  }

  @DeleteMapping("/{id}")
  public Map<String, Boolean> deleteExam(
      @ApiIgnore @RequestAttribute HealthcareInstitution owner,
      @PathVariable(value = "id") Integer examId) {
    Map<String, Boolean> response = new HashMap<>();

    response.put("deleted", this.examService.delete(owner, examId));

    return response;
  }

  @GetMapping("/{id}")
  public ResponseEntity<Exam> getExam(
      @ApiIgnore @RequestAttribute HealthcareInstitution owner,
      @PathVariable(value = "id") Integer examId) {
    return ResponseEntity.ok().body(this.examService.find(owner, examId, true));
  }
}
