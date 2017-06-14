/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdm.model;

import java.util.Date;

/**
 *
 * @author heinz
 */
public class Lobbyist {

  private int id;
  private String forename;
  private String surname;
  private String company;
  private Date dob;

  public Lobbyist() {

  }

  public Lobbyist(int id, String forename, String surname, String company, Date dob) {
    this.id = id;
    this.forename = forename;
    this.surname = surname;
    this.company = company;
    this.dob = dob;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getForename() {
    return forename;
  }

  public void setForename(String forename) {
    this.forename = forename;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public Date getDob() {
    return dob;
  }

  public void setDob(Date dob) {
    this.dob = dob;
  }

}
