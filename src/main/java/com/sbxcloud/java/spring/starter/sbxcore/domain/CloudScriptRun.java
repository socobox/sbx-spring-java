package com.sbxcloud.java.spring.starter.sbxcore.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CloudScriptRun{

  private Long id;

  private Long cloudScriptId;

  private Date created;

  private Long duration;

  private Long userId;

  private String appKey;

  private Boolean test;

  private List<String> errorLog;


  private List<String> outLog;

  private Map<String,Object> params;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getCloudScriptId() {
    return cloudScriptId;
  }

  public void setCloudScriptId(Long cloudScriptId) {
    this.cloudScriptId = cloudScriptId;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Long getDuration() {
    return duration;
  }

  public void setDuration(Long duration) {
    this.duration = duration;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getAppKey() {
    return appKey;
  }

  public void setAppKey(String appKey) {
    this.appKey = appKey;
  }

  public Boolean getTest() {
    return test;
  }

  public void setTest(Boolean test) {
    this.test = test;
  }

  public List<String> getErrorLog() {
    return errorLog;
  }

  public void setErrorLog(List<String> errorLog) {
    this.errorLog = errorLog;
  }

  public List<String> getOutLog() {
    return outLog;
  }

  public void setOutLog(List<String> outLog) {
    this.outLog = outLog;
  }

  public Map<String, Object> getParams() {
    return params;
  }

  public void setParams(Map<String, Object> params) {
    this.params = params;
  }
}
