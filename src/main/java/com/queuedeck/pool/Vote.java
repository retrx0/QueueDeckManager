/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.queuedeck.pool;

/**
 *
 * @author ABDULRAHMAN ILLO
 */
import java.sql.Timestamp;

public class Vote {

  private String candidate;
  private Timestamp timeCast;

  public Vote(String candidate, Timestamp timeCast) {
    this.candidate = candidate.toUpperCase();
    this.timeCast = timeCast;
  }

  public String getCandidate() {
    return candidate;
  }

  public void setCandidate(String candidate) {
    this.candidate = candidate.toUpperCase();
  }

  public Timestamp getTimeCast() {
    return timeCast;
  }

  public void setTimeCast(Timestamp timeCast) {
    this.timeCast = timeCast;
  }

}