package com.sbxcloud.java.spring.starter.sbxcore.querybuilder;


import java.util.HashMap;


public class ReferenceJoin {

  private String rowModel;
  private Condition filter;
  private String referenceField;

  public HashMap<String, Object> getReferenceJoin() {
    HashMap<String, Object> hash = new HashMap<String, Object>();
    HashMap<String, Object> c = filter.getCondition();
    c.remove("ANDOR");
    hash.put("row_model", rowModel);
    hash.put("filter", c);
    hash.put("reference_field", referenceField);
    return hash;
  }

  public String getRowModel() {
    return rowModel;
  }

  public void setRowModel(String rowModel) {
    this.rowModel = rowModel;
  }

  public Condition getFilter() {
    return filter;
  }

  public void setFilter(Condition filter) {
    this.filter = filter;
  }

  public String getReferenceField() {
    return referenceField;
  }

  public void setReferenceField(String referenceField) {
    this.referenceField = referenceField;
  }
}
