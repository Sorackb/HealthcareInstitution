package org.lucasbernardo.HealthcareInstitution;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
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
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles({"test"})
@DBRider
public class HealthcareInstitutionHttpRequestTest {

  private static final HttpHeaders HEADERS = new HttpHeaders();;

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
    JSONObject body = new JSONObject();
    HttpEntity<String> request;
    ResponseEntity<String> response;

    body.put("Name", "lucassouza.org");
    body.put("CNPJ", "82.854.545/0001-20");

    request = new HttpEntity<>(body.toString(), HEADERS);
    response = this.restTemplate.postForEntity("http://localhost:" + port + "/healthcareinstitution/", request, String.class);
    
    // CNPJ returned shouldn't have special characters
    body.put("CNPJ", "82854545000120");

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    JSONAssert.assertEquals(body.toString(), response.getBody(), false);
  }
}
