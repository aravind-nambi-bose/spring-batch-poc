package com.bose.batch.batch.dto;

public class BatchData {

  private int id;
  private String data;

  public BatchData(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }
}
