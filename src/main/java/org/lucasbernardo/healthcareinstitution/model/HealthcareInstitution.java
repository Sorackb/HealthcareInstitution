package org.lucasbernardo.healthcareinstitution.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.UniqueConstraint;

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
  private String name;

  @Column(nullable = false)
  private String cnpj;

  @Column(nullable = false)
  private Integer balance = 20;

  @Column(nullable = false)
  private String token;

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

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public void charge() {
    this.balance -= 1;
  }
}
