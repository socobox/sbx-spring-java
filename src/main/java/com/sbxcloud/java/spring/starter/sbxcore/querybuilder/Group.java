package com.sbxcloud.java.spring.starter.sbxcore.querybuilder;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Group {

  private ANDOR andOr;
  private List<Condition> conditions = new ArrayList<>();

  public void addCondition(Condition condition) {
    this.conditions.add(condition);
  }

  public HashMap<String, Object> getGroup () {
    HashMap<String, Object> hash = new HashMap<String, Object>();
    hash.put("ANDOR", andOr);
    hash.put("GROUP", conditions.stream().map(Condition::getCondition).collect(Collectors.toList()));
    return hash;
  }

  public ANDOR getAndOr() {
    return andOr;
  }

  public void setAndOr(ANDOR andOr) {
    this.andOr = andOr;
  }

  public List<Condition> getConditions() {
    return conditions;
  }

  public void setConditions(List<Condition> conditions) {
    this.conditions = conditions;
  }
}
