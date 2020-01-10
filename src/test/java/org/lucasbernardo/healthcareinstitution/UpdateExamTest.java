package org.lucasbernardo.healthcareinstitution;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

/**
 *
 * @author Lucas<sorackb@gmail.com>
 */
@SpringBootTest(classes = HealthcareInstitutionApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"test"})
@DBRider
public class UpdateExamTest {

  private static final HttpHeaders HEADERS = new HttpHeaders();

  @LocalServerPort
  private int port;
  @Autowired
  private TestRestTemplate restTemplate;

  @BeforeAll
  static void runBeforeAllTestMethods() {
    HEADERS.setContentType(MediaType.APPLICATION_JSON);
    HEADERS.setBearerAuth("eyJhbGciOiJIUzUxMiJ9.eyJjbnBqIjoiMDQwODg1NzgwMDAxMDAifQ.cstUO0j5xo6sPKNB_BhmkHDPpHS7UNIvQitcUC45hejIdK23N84RMpxH0X00DpQ6uZQAqKxxCnLcu_rFOevAXw");
  }

  @Test
  @DataSet({"integration/exam.yml", "integration/healthcare_institution.yml", "integration/cleanup.yml"})
  void updateExam_ValidExam_ShouldUpdateExistentExam() throws JSONException {
    JSONObject exam = new JSONObject();
    ResponseEntity<String> response;
    HttpEntity<String> request;

    exam.put("PatientName", "João");
    exam.put("PatientAge", 55);
    exam.put("PatientGender", "M");
    exam.put("PhysicianName", "Dr. José");
    exam.put("PhysicianCRM", "45465223");
    exam.put("ProcedureName", "MRI");

    request = new HttpEntity<>(exam.toString(), HEADERS);
    response = this.restTemplate.exchange("http://localhost:" + port + "/exams/1", HttpMethod.PUT, request, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    JSONAssert.assertEquals(exam.toString(), response.getBody(), false);
  }

  @Test
  @DataSet({"integration/cleanup.yml"})
  void updateExam_InvalidHealthcareInstitution_ShouldShowErrorMessage() throws JSONException {
    JSONObject exam = new JSONObject();
    JSONObject errors = new JSONObject();
    ResponseEntity<String> response;
    HttpEntity<String> request;

    exam.put("PatientName", "João");
    exam.put("PatientAge", 55);
    exam.put("PatientGender", "M");
    exam.put("PhysicianName", "Dr. José");
    exam.put("PhysicianCRM", "45465223");
    exam.put("ProcedureName", "MRI");

    request = new HttpEntity<>(exam.toString(), HEADERS);
    response = this.restTemplate.exchange("http://localhost:" + port + "/exams/1", HttpMethod.PUT, request, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    errors.put("error", "token \"eyJhbGciOiJIUzUxMiJ9.eyJjbnBqIjoiMDQwODg1NzgwMDAxMDAifQ.cstUO0j5xo6sPKNB_BhmkHDPpHS7UNIvQitcUC45hejIdK23N84RMpxH0X00DpQ6uZQAqKxxCnLcu_rFOevAXw\" not found.");
    JSONAssert.assertEquals(errors.toString(), response.getBody(), true);
  }

  @Test
  @DataSet({"integration/healthcare_institution.yml", "integration/cleanup.yml"})
  void updateExam_InvalidExam_ShouldShowErrorMessage() throws JSONException {
    JSONObject exam = new JSONObject();
    JSONObject errors = new JSONObject();
    ResponseEntity<String> response;
    HttpEntity<String> request;

    exam.put("PatientName", "João");
    exam.put("PatientAge", 55);
    exam.put("PatientGender", "M");
    exam.put("PhysicianName", "Dr. José");
    exam.put("PhysicianCRM", "45465223");
    exam.put("ProcedureName", "MRI");

    request = new HttpEntity<>(exam.toString(), HEADERS);
    response = this.restTemplate.exchange("http://localhost:" + port + "/exams/1", HttpMethod.PUT, request, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    errors.put("Exam", "id \"1\" not found.");
    JSONAssert.assertEquals(errors.toString(), response.getBody(), true);
  }

  @Test
  @DataSet({"integration/exam.yml", "integration/healthcare_institution.yml", "integration/cleanup.yml"})
  void updateExam_IncompleteExam_ShouldShowErrorMessage() throws JSONException {
    JSONObject errors = new JSONObject();
    ResponseEntity<String> response;
    HttpEntity<String> request;

    request = new HttpEntity<>("{}", HEADERS);
    response = this.restTemplate.exchange("http://localhost:" + port + "/exams/1", HttpMethod.PUT, request, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    errors.put("patientName", "PatientName is mandatory.");
    errors.put("physicianName", "PhysicianName is mandatory.");
    errors.put("physicianCRM", "PhysicianCRM is mandatory.");
    errors.put("procedureName", "ProcedureName is mandatory.");
    errors.put("patientGender", "PatientGender is mandatory.");
    errors.put("patientAge", "PatientAge is mandatory.");

    JSONAssert.assertEquals(errors.toString(), response.getBody(), true);
  }

  @Test
  @DataSet({"integration/healthcare_institution.yml", "integration/cleanup.yml"})
  void updateExam_InvalidPatientAge_ShouldShowErrorMessage() throws JSONException {
    JSONObject exam = new JSONObject();
    JSONObject errors = new JSONObject();
    ResponseEntity<String> response;
    HttpEntity<String> request;

    exam.put("PatientName", "João");
    exam.put("PatientAge", -1);
    exam.put("PatientGender", "M");
    exam.put("PhysicianName", "Dr. José");
    exam.put("PhysicianCRM", "45465223");
    exam.put("ProcedureName", "MRI");

    request = new HttpEntity<>(exam.toString(), HEADERS);
    response = this.restTemplate.exchange("http://localhost:" + port + "/exams/1", HttpMethod.PUT, request, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    errors.put("patientAge", "PatientAge should be a positive integer.");

    JSONAssert.assertEquals(errors.toString(), response.getBody(), true);
  }

  @Test
  @DataSet({"integration/exam.yml", "integration/healthcare_institution.yml", "integration/cleanup.yml"})
  void updateExam_InvalidPatientGender_ShouldShowErrorMessage() throws JSONException {
    JSONObject exam = new JSONObject();
    JSONObject errors = new JSONObject();
    ResponseEntity<String> response;
    HttpEntity<String> request;

    exam.put("PatientName", "João");
    exam.put("PatientAge", 55);
    exam.put("PatientGender", "G");
    exam.put("PhysicianName", "Dr. José");
    exam.put("PhysicianCRM", "45465223");
    exam.put("ProcedureName", "MRI");

    request = new HttpEntity<>(exam.toString(), HEADERS);
    response = this.restTemplate.exchange("http://localhost:" + port + "/exams/1", HttpMethod.PUT, request, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    errors.put("patientGender", "PatientGender must be \"M\" or \"F\".");
    JSONAssert.assertEquals(errors.toString(), response.getBody(), true);
  }

  @Test
  @DataSet({"integration/exam.yml", "integration/healthcare_institution_uncharged.yml", "integration/cleanup.yml"})
  void updateExam_HealthcareInstitutionWithoutPixeons_ShouldUpdateExistentExam() throws JSONException {
    JSONObject exam = new JSONObject();
    ResponseEntity<String> response;
    HttpEntity<String> request;

    exam.put("PatientName", "João");
    exam.put("PatientAge", 55);
    exam.put("PatientGender", "M");
    exam.put("PhysicianName", "Dr. José");
    exam.put("PhysicianCRM", "45465223");
    exam.put("ProcedureName", "MRI");

    request = new HttpEntity<>(exam.toString(), HEADERS);
    response = this.restTemplate.exchange("http://localhost:" + port + "/exams/1", HttpMethod.PUT, request, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    JSONAssert.assertEquals(exam.toString(), response.getBody(), false);
  }
}
