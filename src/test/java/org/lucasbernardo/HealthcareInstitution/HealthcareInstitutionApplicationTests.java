package org.lucasbernardo.HealthcareInstitution;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.github.database.rider.spring.api.DBRider;
import org.junit.jupiter.api.Test;
import org.lucasbernardo.healthcareinstitution.controller.HealthcareInstitutionController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 *
 * @author Lucas<sorackb@gmail.com>
 */
@SpringBootTest
@ActiveProfiles({"test"})
@DBRider
class HealthcareInstitutionApplicationTests {

  @Autowired
  private HealthcareInstitutionController controller;

  @Test
  void contextLoads() {
    assertThat(this.controller).isNotNull();
  }

}
