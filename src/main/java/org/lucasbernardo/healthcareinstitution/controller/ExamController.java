package org.lucasbernardo.healthcareinstitution.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import java.net.URI;
import javax.validation.Valid;
import org.lucasbernardo.healthcareinstitution.model.dto.ExamDto;
import org.lucasbernardo.healthcareinstitution.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import springfox.documentation.annotations.ApiIgnore;

/**
 *
 * @author Lucas<sorackb@gmail.com>
 */
@Api(tags = {"exams"})
@SwaggerDefinition(tags = {
  @Tag(name = "exams", description = "An API that will take control over the exam ingest.")
})
@RestController
@RequestMapping("/exams")
public class ExamController {

  @Autowired
  private ExamService examService;

  /**
   * Create an Exam based on details provided from the owner's Healthcare
   * Institution.
   *
   * @param cnpj The Healthcare Institution's CNPJ who requested the exam
   * @param exam Detail of the Exam to be created
   * @return The exam that was created
   */
  @PostMapping
  public ResponseEntity<ExamDto> createExam(
      @ApiIgnore @RequestAttribute String cnpj,
      @Valid @RequestBody ExamDto exam) {
    ExamDto resource = this.examService.create(cnpj, exam);

    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(resource.getId())
        .toUri();

    return ResponseEntity
        .created(location)
        .body(resource);
  }

  /**
   * Update an existent Exam based on details provided from the owner's
   * Healthcare Institution.
   *
   * @param cnpj The Healthcare Institution's CNPJ who requested the exam
   * @param examId The exam identifier
   * @param exam Detail to update the Exam
   * @return The exam that was updated
   */
  @PutMapping("/{id}")
  public ResponseEntity<ExamDto> updateExam(
      @ApiIgnore @RequestAttribute String cnpj,
      @PathVariable(value = "id") Integer examId,
      @Valid @RequestBody ExamDto exam) {
    return ResponseEntity.ok(this.examService.update(cnpj, exam, examId));
  }

  /**
   * Delete an existent Exam.
   *
   * @param cnpj The Healthcare Institution's CNPJ who requested the exam
   * @param examId The exam identifier
   */
  @DeleteMapping("/{id}")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void deleteExam(
      @ApiIgnore @RequestAttribute String cnpj,
      @PathVariable(value = "id") Integer examId) {
    this.examService.delete(cnpj, examId);
  }

  /**
   * Retrieve an specific Exam previously created.
   *
   * @param cnpj The Healthcare Institution's CNPJ who requested the exam
   * @param examId The exam identifier
   * @return The exam that was requested
   */
  @GetMapping("/{id}")
  public ResponseEntity<ExamDto> getExam(
      @ApiIgnore @RequestAttribute String cnpj,
      @PathVariable(value = "id") Integer examId) {
    return ResponseEntity.ok().body(this.examService.paidFind(cnpj, examId));
  }
}
