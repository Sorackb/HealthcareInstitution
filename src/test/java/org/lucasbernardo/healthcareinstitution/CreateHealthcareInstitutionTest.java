package org.lucasbernardo.healthcareinstitution;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

/**
 *
 * @author Lucas<sorackb@gmail.com>
 */
@SpringBootTest(classes = HealthcareInstitutionApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"test"})
@DBRider
public class CreateHealthcareInstitutionTest {

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
  @DataSet({"integration/cleanup.yml"})
  void createHealthcareInstitution_ValidHealthcareInstitution_ShouldCreateNewHealthcareInstitution() throws JSONException {
    JSONObject institution = new JSONObject();
    ResponseEntity<String> response;
    HttpEntity<String> request;

    institution.put("Name", "lucassouza.org");
    institution.put("CNPJ", "04.088.578/0001-00");

    request = new HttpEntity<>(institution.toString(), HEADERS);
    response = this.restTemplate.postForEntity("http://localhost:" + port + "/healthcareinstitutions/", request, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    // CNPJ returned shouldn't have special characters
    institution.put("CNPJ", "04088578000100");
    institution.put("token", new JSONObject(response.getBody()).get("token"));
    JSONAssert.assertEquals(institution.toString(), response.getBody(), true);
  }

  @Test
  @DataSet({"integration/cleanup.yml"})
  void createHealthcareInstitution_IncompleteHealthcareInstitution_ShouldShowErrorMessage() throws JSONException {
    JSONObject errors = new JSONObject();
    ResponseEntity<String> response;
    HttpEntity<String> request;

    request = new HttpEntity<>("{}", HEADERS);
    response = this.restTemplate.postForEntity("http://localhost:" + port + "/healthcareinstitutions/", request, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    errors.put("name", "Name is mandatory.");
    errors.put("cnpj", "CNPJ is mandatory.");

    JSONAssert.assertEquals(errors.toString(), response.getBody(), true);
  }

  @Test
  @DataSet({"integration/cleanup.yml"})
  void createHealthcareInstitution_IncorrectHealthcareInstitutionCnpj_ShouldShowErrorMessage() throws JSONException {
    JSONObject institution = new JSONObject();
    JSONObject errors = new JSONObject();
    ResponseEntity<String> response;
    HttpEntity<String> request;

    institution.put("Name", "lucassouza.org");
    institution.put("CNPJ", "82.854.545/0001-21");

    request = new HttpEntity<>(institution.toString(), HEADERS);
    response = this.restTemplate.postForEntity("http://localhost:" + port + "/healthcareinstitutions/", request, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    errors.put("cnpj", "Invalid CNPJ.");

    JSONAssert.assertEquals(errors.toString(), response.getBody(), true);
  }

  @Test
  @DataSet({"integration/healthcare_institution.yml", "integration/cleanup.yml"})
  void createHealthcareInstitution_DuplicateHealthcareInstitutionCnpj_ShouldShowErrorMessage() throws JSONException {
    JSONObject institution = new JSONObject();
    JSONObject errors = new JSONObject();
    ResponseEntity<String> response;
    HttpEntity<String> request;

    institution.put("Name", "lucassouza.org");
    institution.put("CNPJ", "04.088.578/0001-00");

    request = new HttpEntity<>(institution.toString(), HEADERS);
    response = this.restTemplate.postForEntity("http://localhost:" + port + "/healthcareinstitutions/", request, String.class);

    // TODO Check unique at H2
    // assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    errors.put("cnpj", "\"Duplicate entry '04088578000100' for key 'CNPJ'\".");
    // JSONAssert.assertEquals(errors.toString(), response.getBody(), true);
  }
}
