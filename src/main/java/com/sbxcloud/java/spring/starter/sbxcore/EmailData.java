package com.sbxcloud.java.spring.starter.sbxcore;



import java.util.HashMap;
import java.util.List;


public class EmailData {
  private String subject;
  private String from;
  private Integer domain;
  private String to;
  private List<String> cc;
  private List<String> bcc;
  private String template;
  private String templateKey;
  private HashMap<String, Object> data;


  public EmailData(String from, String subject, String to, HashMap<String, Object> data) {
    this.subject = subject;
    this.from = from;
    this.to = to;
    this.data = data;
  }


  public HashMap<String, Object> getData() {
    return data;
  }

  public void setData(HashMap<String, Object> data) {
    this.data = data;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getFrom() {
    return from;
  }

  public void setFrom(String from) {
    this.from = from;
  }

  public Integer getDomain() {
    return domain;
  }

  public void setDomain(Integer domain) {
    this.domain = domain;
  }

  public String getTo() {
    return to;
  }

  public void setTo(String to) {
    this.to = to;
  }

  public List<String> getCc() {
    return cc;
  }

  public void setCc(List<String> cc) {
    this.cc = cc;
  }

  public List<String> getBcc() {
    return bcc;
  }

  public void setBcc(List<String> bcc) {
    this.bcc = bcc;
  }

  public String getTemplate() {
    return template;
  }

  public void setTemplate(String template) {
    this.template = template;
  }

  public String getTemplateKey() {
    return templateKey;
  }

  public void setTemplateKey(String templateKey) {
    this.templateKey = templateKey;
  }
}
