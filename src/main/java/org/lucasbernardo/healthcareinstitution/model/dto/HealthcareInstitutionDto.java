package org.lucasbernardo.healthcareinstitution.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.lucasbernardo.healthcareinstitution.model.validator.Cnpj;

/**
 *
 * @author Lucas<sorackb@gmail.com>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "HealthcareInstitution")
public class HealthcareInstitutionDto {

  @JsonProperty("Name")
  @NotNull(message = "Name is mandatory.")
  @NotBlank(message = "Name is mandatory.")
  private String name;

  @Cnpj
  @JsonProperty("CNPJ")
  @NotNull(message = "CNPJ is mandatory.")
  @NotBlank(message = "CNPJ is mandatory.")
  private String cnpj;

  @ApiModelProperty(hidden = true)
  private String token;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCnpj() {
    return cnpj;
  }

  public void setCnpj(String cnpj) {
    this.cnpj = cnpj;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }
}
