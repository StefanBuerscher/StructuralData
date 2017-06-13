/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdm.model;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author heinz
 */
public class Politican implements Serializable {

  private int id;
  private String forename;
  private String surname;
  private String party;
  private Date dob;

  public Politican() {

  }

  public Politican(int id, String forename, String surname, String party, Date dob) {
    this.id = id;
    this.forename = forename;
    this.surname = surname;
    this.party = party;
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

  public String getParty() {
    return party;
  }

  public void setParty(String party) {
    this.party = party;
  }

  public Date getDob() {
    return dob;
  }

  public void setDob(Date dob) {
    this.dob = dob;
  }

}
