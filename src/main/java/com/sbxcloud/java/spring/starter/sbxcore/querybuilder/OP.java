package com.sbxcloud.java.spring.starter.sbxcore.querybuilder;

public enum OP {
  IN("IN"),
  NOTIN("NOT IN"),
  IS("IS"),
  ISNOT("IS NOT"),
  NOT("<>"),
  NOTEQ("!="),
  EQ("="),
  LIKE("LIKE"),
  LT("<"),
  GT(">"),
  LET("<="),
  GET(">=");

  private String val;

  OP(String s) {
    this.val = s;
  }

  public String getValue() {
    return val;
  }
}
