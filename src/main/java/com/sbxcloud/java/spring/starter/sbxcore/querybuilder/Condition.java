package com.sbxcloud.java.spring.starter.sbxcore.querybuilder;


import java.util.HashMap;


public class Condition {

  private ANDOR andOr;
  private Object val;
  private String field;
  private OP op;

  public HashMap<String, Object> getCondition() {
    HashMap<String, Object> hash = new HashMap<>();
    hash.put("ANDOR", andOr);
    hash.put("VAL", val);
    hash.put("FIELD", field);
    hash.put("OP", op.getValue());
    return hash;
  }

  public ANDOR getAndOr() {
    return andOr;
  }

  public void setAndOr(ANDOR andOr) {
    this.andOr = andOr;
  }

  public Object getVal() {
    return val;
  }

  public void setVal(Object val) {
    this.val = val;
  }

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public OP getOp() {
    return op;
  }

  public void setOp(OP op) {
    this.op = op;
  }
}
