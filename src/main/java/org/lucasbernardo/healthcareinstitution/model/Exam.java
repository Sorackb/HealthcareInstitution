package org.lucasbernardo.healthcareinstitution.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
  @ManyToOne
  @JoinColumn(name = "healthcare_institution_id", nullable = false)
  private HealthcareInstitution healthcareInstitution;
  @Column(name = "patient_name", nullable = false)
  private String patientName;
  @Column(name = "patient_age", nullable = false)
  private Integer patientAge;
  @Column(name = "patient_gender", nullable = false)
  private String patientGender;
  @Column(name = "physician_name", nullable = false)
  private String physicianName;
  @Column(name = "physician_crm", nullable = false)
  private String physicianCRM;
  @Column(name = "procedure_name", nullable = false)
  private String procedureName;
  @Column(name = "charged", nullable = false)
  private Boolean charged = false;

  public Integer getId() {
    return id;
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

  public Boolean isCharged() {
    return charged;
  }

  public void setCharged(Boolean charged) {
    this.charged = charged;
  }
}
