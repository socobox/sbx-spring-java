package com.sbxcloud.java.spring.starter.sbxcore.domain;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;


import java.util.HashMap;
import java.util.Map;


public class CloudScriptResponse {

  private Boolean success;

  private Integer boxesBought;

  private HashMap<String, Object> box;

  private HashMap<String, Object> results;

  private String error;

  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  @JsonAnyGetter
  public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
  }

  @JsonAnySetter
  public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
  }


  public Boolean getSuccess() {
    return success;
  }

  public void setSuccess(Boolean success) {
    this.success = success;
  }

  public Integer getBoxesBought() {
    return boxesBought;
  }

  public void setBoxesBought(Integer boxesBought) {
    this.boxesBought = boxesBought;
  }

  public HashMap<String, Object> getBox() {
    return box;
  }

  public void setBox(HashMap<String, Object> box) {
    this.box = box;
  }

  public HashMap<String, Object> getResults() {
    return results;
  }

  public void setResults(HashMap<String, Object> results) {
    this.results = results;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public void setAdditionalProperties(Map<String, Object> additionalProperties) {
    this.additionalProperties = additionalProperties;
  }
}
