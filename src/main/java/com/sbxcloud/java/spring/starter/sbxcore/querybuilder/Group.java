package com.sbxcloud.java.spring.starter.sbxcore.querybuilder;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.LinkedList;
import java.util.List;

public class Group {

  @JsonProperty("ANDOR")
  private final AndOr andOr;

  @JsonProperty("GROUP")
  private List<Condition> conditions =  new LinkedList<>();

  public Group(AndOr andOr) {
    this.andOr = andOr;
  }

  public AndOr getAndOr() {
    return andOr;
  }

  public List<Condition> getConditions() {
    return conditions;
  }

  public void setConditions(List<Condition> conditions) {
    this.conditions = conditions;
  }


  public void addCondition(Condition condition) {
    conditions.add(condition);
  }
}
