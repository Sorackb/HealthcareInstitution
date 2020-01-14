package org.lucasbernardo.healthcareinstitution.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.lucasbernardo.healthcareinstitution.model.type.GenderType;
import org.lucasbernardo.healthcareinstitution.model.validator.ValueOfEnum;

/**
 *
 * @author Lucas<sorackb@gmail.com>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "Exam")
public class ExamDto {

  @ApiModelProperty(hidden = true)
  private Integer id;
  @ApiModelProperty(hidden = true)
  private HealthcareInstitutionDto healthcareInstitution;
  @JsonProperty("PatientName")
  @NotNull(message = "PatientName is mandatory.")
  @NotBlank(message = "PatientName is mandatory.")
  private String patientName;
  @JsonProperty("PatientAge")
  @Min(value = 0, message = "PatientAge should be a positive integer.")
  @NotNull(message = "PatientAge is mandatory.")
  private Integer patientAge;
  @ValueOfEnum(enumClass = GenderType.class, message = "PatientGender must be \"M\" or \"F\".")
  @JsonProperty("PatientGender")
  @NotNull(message = "PatientGender is mandatory.")
  @NotBlank(message = "PatientGender is mandatory.")
  private String patientGender;
  @JsonProperty("PhysicianName")
  @NotNull(message = "PhysicianName is mandatory.")
  @NotBlank(message = "PhysicianName is mandatory.")
  private String physicianName;
  @JsonProperty("PhysicianCRM")
  @NotNull(message = "PhysicianCRM is mandatory.")
  @NotBlank(message = "PhysicianCRM is mandatory.")
  private String physicianCRM;
  @JsonProperty("ProcedureName")
  @NotNull(message = "ProcedureName is mandatory.")
  @NotBlank(message = "ProcedureName is mandatory.")
  private String procedureName;

  public String getPatientName() {
    return patientName;
  }

  public void setPatientName(String patientName) {
    this.patientName = patientName;
  }

  public Integer getPatientAge() {
    return patientAge;
  }

  public void setPatientAge(Integer patientAge) {
    this.patientAge = patientAge;
  }

  public String getPatientGender() {
    return patientGender;
  }

  public void setPatientGender(String patientGender) {
    this.patientGender = patientGender;
  }

  public String getPhysicianName() {
    return physicianName;
  }

  public void setPhysicianName(String physicianName) {
    this.physicianName = physicianName;
  }

  public String getPhysicianCRM() {
    return physicianCRM;
  }

  public void setPhysicianCRM(String physicianCRM) {
    this.physicianCRM = physicianCRM;
  }

  public String getProcedureName() {
    return procedureName;
  }

  public void setProcedureName(String procedureName) {
    this.procedureName = procedureName;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public HealthcareInstitutionDto getHealthcareInstitution() {
    return healthcareInstitution;
  }

  public void setHealthcareInstitution(HealthcareInstitutionDto healthcareInstitution) {
    this.healthcareInstitution = healthcareInstitution;
  }
}
