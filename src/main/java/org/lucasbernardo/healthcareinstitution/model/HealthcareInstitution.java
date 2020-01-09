package org.lucasbernardo.healthcareinstitution.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.lucasbernardo.healthcareinstitution.model.validator.Cnpj;

/**
 *
 * @author Lucas<sorackb@gmail.com>
 */
@Entity
@Table(name = "healthcare_institution", uniqueConstraints = @UniqueConstraint(columnNames = "cnpj", name = "UC_HEALTHCARE_INSTITUTION_CNPJ"))
public class HealthcareInstitution implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(nullable = false)
  @JsonProperty("Name")
  @NotNull(message = "Name is mandatory.")
  @NotBlank(message = "Name is mandatory.")
  private String name;

  @Cnpj
  @Column(nullable = false)
  @JsonProperty("CNPJ")
  @NotNull(message = "CNPJ is mandatory.")
  @NotBlank(message = "CNPJ is mandatory.")
  private String cnpj;

  @Column(nullable = false)
  @JsonIgnore
  private Integer balance = 20;

  public Integer getId() {
    return id;
  }

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

  public Integer getBalance() {
    return balance;
  }

  public void charge() {
    this.balance -= 1;
  }
}
