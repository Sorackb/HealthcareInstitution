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
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.ActiveProfiles;

/**
 *
 * @author Lucas<sorackb@gmail.com>
 */
@SpringBootTest(classes = HealthcareInstitutionApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"test"})
@DBRider
public class CreateExamTest {

  private static final HttpHeaders HEADERS = new HttpHeaders();

  @LocalServerPort
  private int port;
  @Autowired
  private TestRestTemplate restTemplate;

  @BeforeAll
  static void runBeforeAllTestMethods() {
    HEADERS.setContentType(MediaType.APPLICATION_JSON);
    HEADERS.setBearerAuth("$2a$10$uCTB.oxLSGsER91Zq2ns7eo3XzSyGyiZfTrceEKtSrJEOID/773oW");
  }

  @Test
  @DataSet({"integration/healthcare_institution.yml", "integration/cleanup.yml"})
  void createExam_ValidExam_ShouldCreateNewExam() throws JSONException {
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
    response = this.restTemplate.postForEntity("http://localhost:" + port + "/exams/", request, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    JSONAssert.assertEquals(exam.toString(), response.getBody(), false);
  }

  @Test
  @DataSet({"integration/cleanup.yml"})
  void createExam_InvalidHealthcareInstitution_ShouldShowErrorMessage() throws JSONException {
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
    response = this.restTemplate.postForEntity("http://localhost:" + port + "/exams/", request, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    errors.put("error", "token \"$2a$10$uCTB.oxLSGsER91Zq2ns7eo3XzSyGyiZfTrceEKtSrJEOID/773oW\" not found.");
    JSONAssert.assertEquals(errors.toString(), response.getBody(), true);
  }

  @Test
  @DataSet({"integration/healthcare_institution.yml", "integration/cleanup.yml"})
  void createExam_IncompleteExam_ShouldShowErrorMessage() throws JSONException {
    JSONObject errors = new JSONObject();
    ResponseEntity<String> response;
    HttpEntity<String> request;

    request = new HttpEntity<>("{}", HEADERS);
    response = this.restTemplate.postForEntity("http://localhost:" + port + "/exams/", request, String.class);

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
  void createExam_InvalidPatientAge_ShouldShowErrorMessage() throws JSONException {
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
    response = this.restTemplate.postForEntity("http://localhost:" + port + "/exams/", request, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    errors.put("patientAge", "PatientAge should be a positive integer.");

    JSONAssert.assertEquals(errors.toString(), response.getBody(), true);
  }

  @Test
  @DataSet({"integration/healthcare_institution.yml", "integration/cleanup.yml"})
  void createExam_InvalidPatientGender_ShouldShowErrorMessage() throws JSONException {
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
    response = this.restTemplate.postForEntity("http://localhost:" + port + "/exams/", request, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    errors.put("patientGender", "PatientGender must be \"M\" or \"F\".");
    JSONAssert.assertEquals(errors.toString(), response.getBody(), true);
  }

  @Test
  @DataSet({"integration/healthcare_institution_uncharged.yml", "integration/cleanup.yml"})
  void createExam_HealthcareInstitutionWithoutPixeons_ShouldShowErrorMessage() throws JSONException {
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
    response = this.restTemplate.postForEntity("http://localhost:" + port + "/exams/", request, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    errors.put("HealthcareInstitution", "Out of budget.");
    JSONAssert.assertEquals(errors.toString(), response.getBody(), true);
  }
}
