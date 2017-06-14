/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdm.model;

/**
 *
 * @author heinz
 */
public class Bribery {

  private int fkPolitician;
  private int fkLobbyist;
  private int value;
  private String reason;
  private boolean confirmed;

  public Bribery() {

  }

  public Bribery(int fkPolitician, int fkLobbyist, int value, String reason, boolean confirmed) {
    this.fkPolitician = fkPolitician;
    this.fkLobbyist = fkLobbyist;
    this.value = value;
    this.reason = reason;
    this.confirmed = confirmed;
  }

  public int getFkPolitician() {
    return fkPolitician;
  }

  public void setFkPolitician(int fkPolitician) {
    this.fkPolitician = fkPolitician;
  }

  public int getFkLobbyist() {
    return fkLobbyist;
  }

  public void setFkLobbyist(int fkLobbyist) {
    this.fkLobbyist = fkLobbyist;
  }

  public int getValue() {
    return value;
  }

  public void setValue(int value) {
    this.value = value;
  }

  public String getReason() {
    return reason;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public boolean isConfirmed() {
    return confirmed;
  }

  public void setConfirmed(boolean confirmed) {
    this.confirmed = confirmed;
  }

}
