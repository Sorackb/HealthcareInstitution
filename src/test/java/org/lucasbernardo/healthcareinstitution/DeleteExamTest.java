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
public class DeleteExamTest {

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
  void deleteExam_ValidExam_ShouldUpdateExistentExam() throws JSONException {
    JSONObject confirmation = new JSONObject();
    ResponseEntity<String> response;
    HttpEntity<String> request;

    request = new HttpEntity<>(HEADERS);
    response = this.restTemplate.exchange("http://localhost:" + port + "/exams/1", HttpMethod.DELETE, request, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    confirmation.put("deleted", true);
    JSONAssert.assertEquals(confirmation.toString(), response.getBody(), false);
  }

  @Test
  @DataSet({"integration/cleanup.yml"})
  void deleteExam_InvalidHealthcareInstitution_ShouldShowErrorMessage() throws JSONException {
    JSONObject errors = new JSONObject();
    ResponseEntity<String> response;
    HttpEntity<String> request;

    request = new HttpEntity<>(HEADERS);
    response = this.restTemplate.exchange("http://localhost:" + port + "/exams/1", HttpMethod.DELETE, request, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    errors.put("error", "The token entered is invalid.");
    JSONAssert.assertEquals(errors.toString(), response.getBody(), true);
  }

  @Test
  @DataSet({"integration/healthcare_institution.yml", "integration/cleanup.yml"})
  void deleteExam_InvalidExam_ShouldShowErrorMessage() throws JSONException {
    JSONObject errors = new JSONObject();
    ResponseEntity<String> response;
    HttpEntity<String> request;

    request = new HttpEntity<>(HEADERS);
    response = this.restTemplate.exchange("http://localhost:" + port + "/exams/1", HttpMethod.DELETE, request, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    errors.put("Exam", "id \"1\" not found.");
    JSONAssert.assertEquals(errors.toString(), response.getBody(), true);
  }

  @Test
  @DataSet({"integration/exam.yml", "integration/healthcare_institution_uncharged.yml", "integration/cleanup.yml"})
  void deleteExam_HealthcareInstitutionWithoutPixeons_ShouldUpdateExistentExam() throws JSONException {
    JSONObject confirmation = new JSONObject();
    ResponseEntity<String> response;
    HttpEntity<String> request;

    request = new HttpEntity<>(HEADERS);
    response = this.restTemplate.exchange("http://localhost:" + port + "/exams/1", HttpMethod.DELETE, request, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    confirmation.put("deleted", true);
    JSONAssert.assertEquals(confirmation.toString(), response.getBody(), false);
  }
}
