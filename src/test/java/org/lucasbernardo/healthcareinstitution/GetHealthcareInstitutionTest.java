package org.lucasbernardo.healthcareinstitution;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.json.JSONException;
import org.json.JSONObject;
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
import org.springframework.http.ResponseEntity;
import static org.springframework.security.config.Elements.HEADERS;
import org.springframework.test.context.ActiveProfiles;

/**
 *
 * @author Lucas<sorackb@gmail.com>
 */
@SpringBootTest(classes = HealthcareInstitutionApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"test"})
@DBRider
public class GetHealthcareInstitutionTest {

  @LocalServerPort
  private int port;
  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  @DataSet({"integration/healthcare_institution.yml", "integration/cleanup.yml"})
  void getHealthcareInstitution_ValidExam_ShouldRetrieveExistentExam() throws JSONException {
    JSONObject healthcareInstitution = new JSONObject();
    ResponseEntity<String> response;
    HttpEntity<String> request;

    request = new HttpEntity<>(new HttpHeaders());
    healthcareInstitution.put("Name", "lucasbernardo.org");
    healthcareInstitution.put("CNPJ", "04088578000100");

    response = this.restTemplate.exchange("http://localhost:" + port + "/healthcareinstitutions/04088578000100", HttpMethod.GET, request, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    JSONAssert.assertEquals(healthcareInstitution.toString(), response.getBody(), false);
  }

  @Test
  @DataSet({"integration/cleanup.yml"})
  void getHealthcareInstitution_InvalidExam_ShouldShowErrorMessage() throws JSONException {
    JSONObject errors = new JSONObject();
    ResponseEntity<String> response;
    HttpEntity<String> request;

    request = new HttpEntity<>(HEADERS);
    response = this.restTemplate.exchange("http://localhost:" + port + "/healthcareinstitutions/04088578000100", HttpMethod.GET, request, String.class);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    errors.put("HealthcareInstitution", "CNPJ \"04088578000100\" not found.");
    JSONAssert.assertEquals(errors.toString(), response.getBody(), true);
  }
}
