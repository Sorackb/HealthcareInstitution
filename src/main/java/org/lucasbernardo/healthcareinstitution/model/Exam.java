package org.lucasbernardo.healthcareinstitution.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Lucas<sorackb@gmail.com>
 */
@Entity
@Table(name = "exam")
public class Exam implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "healthcare_institution_id", nullable = false)
  private HealthcareInstitution healthcareInstitution;
  @Column(name = "patient_name", nullable = false)
  @JsonProperty("PatientName")
  @NotBlank(message = "PatientName is mandatory")
  private String patientName;
  @Column(name = "patient_age", nullable = false)
  @JsonProperty("PatientAge")
  @NotNull(message = "PatientAge is mandatory")
  private Integer patientAge;
  @Column(name = "patient_gender", nullable = false)
  @Enumerated(EnumType.STRING)
  @JsonProperty("PatientGender")
  @NotNull(message = "PatientGender is mandatory")
  private Gender patientGender;
  @Column(name = "physician_name", nullable = false)
  @JsonProperty("PhysicianName")
  @NotBlank(message = "PhysicianName is mandatory")
  private String physicianName;
  @Column(name = "physician_crm", nullable = false)
  @JsonProperty("PhysicianCRM")
  @NotBlank(message = "PhysicianCRM is mandatory")
  private String physicianCRM;
  @Column(name = "procedure_name", nullable = false)
  @JsonProperty("ProcedureName")
  @NotBlank(message = "ProcedureName is mandatory")
  private String procedureName;
  @Column(name = "charged", nullable = false)
  @JsonIgnore
  private Boolean charged = false;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public HealthcareInstitution getHealthcareInstitution() {
    return healthcareInstitution;
  }

  public void setHealthcareInstitution(HealthcareInstitution healthcareInstitution) {
    this.healthcareInstitution = healthcareInstitution;
  }

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

  public Gender getPatientGender() {
    return patientGender;
  }

  public void setPatientGender(Gender patientGender) {
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

  public Boolean isCharged() {
    return charged;
  }

  public void setCharged(Boolean charged) {
    this.charged = charged;
  }
}
