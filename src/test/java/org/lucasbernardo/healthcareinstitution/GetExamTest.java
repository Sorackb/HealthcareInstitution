package org.lucasbernardo.HealthcareInstitution;

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
import org.springframework.http.HttpHeaders;
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
public class GetExamTest {

  private static final HttpHeaders HEADERS = new HttpHeaders();

  @LocalServerPort
  private int port;
  @Autowired
  private TestRestTemplate restTemplate;

  @BeforeAll
  static void runBeforeAllTestMethods() {
    HEADERS.setContentType(MediaType.APPLICATION_JSON);
  }

  @Test
  @DataSet({"integration/exam.yml", "integration/healthcare_institution.yml", "integration/cleanup.yml"})
  void getExam_ValidExam_ShouldRetrieveExistentExam() throws JSONException {
    JSONObject exam = new JSONObject();
    ResponseEntity<String> response;
    
    exam.put("PatientName", "João");
    exam.put("PatientAge", 55);
    exam.put("PatientGender", "M");
    exam.put("PhysicianName", "Dr. José");
    exam.put("PhysicianCRM", "45465223");
    exam.put("ProcedureName", "MRI");

    response = this.restTemplate.getForEntity("http://localhost:" + port + "/healthcareinstitution/1/exam/1", String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    JSONAssert.assertEquals(exam.toString(), response.getBody(), false);
  }

  @Test
  @DataSet({"integration/cleanup.yml"})
  void getExam_InvalidHealthcareInstitution_ShouldShowErrorMessage() throws JSONException {
    JSONObject errors = new JSONObject();
    ResponseEntity<String> response;
    response = this.restTemplate.getForEntity("http://localhost:" + port + "/healthcareinstitution/1/exam/1", String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    errors.put("HealthcareInstitution", "id \"1\" not found.");
    JSONAssert.assertEquals(errors.toString(), response.getBody(), true);
  }

  @Test
  @DataSet({"integration/healthcare_institution.yml", "integration/cleanup.yml"})
  void getExam_InvalidExam_ShouldShowErrorMessage() throws JSONException {
    JSONObject errors = new JSONObject();
    ResponseEntity<String> response;
    response = this.restTemplate.getForEntity("http://localhost:" + port + "/healthcareinstitution/1/exam/1", String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    errors.put("Exam", "id \"1\" not found.");
    JSONAssert.assertEquals(errors.toString(), response.getBody(), true);
  }

  @Test
  @DataSet({"integration/exam.yml", "integration/healthcare_institution_uncharged.yml", "integration/cleanup.yml"})
  void getExam_HealthcareInstitutionWithoutPixeons_ShouldShowErrorMessage() throws JSONException {
    JSONObject errors = new JSONObject();
    ResponseEntity<String> response;

    response = this.restTemplate.getForEntity("http://localhost:" + port + "/healthcareinstitution/1/exam/1", String.class);
    
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    errors.put("HealthcareInstitution", "Out of budget.");
    JSONAssert.assertEquals(errors.toString(), response.getBody(), true);
  }
  
  @Test
  @DataSet({"integration/exam_charged.yml", "integration/healthcare_institution_uncharged.yml", "integration/cleanup.yml"})
  void getExam_AlreadyChargedExam_ShouldRetrieveExistentExam() throws JSONException {
    JSONObject exam = new JSONObject();
    ResponseEntity<String> response;
    
    exam.put("PatientName", "João");
    exam.put("PatientAge", 55);
    exam.put("PatientGender", "M");
    exam.put("PhysicianName", "Dr. José");
    exam.put("PhysicianCRM", "45465223");
    exam.put("ProcedureName", "MRI");

    response = this.restTemplate.getForEntity("http://localhost:" + port + "/healthcareinstitution/1/exam/1", String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    JSONAssert.assertEquals(exam.toString(), response.getBody(), false);
  }
}
