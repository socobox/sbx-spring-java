package com.sbxcloud.java.spring.starter.sbxcore.querybuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.HashMap;
import java.util.List;


public class QueryBuilder {

  private Query q;
  private Group group;
  private ObjectMapper mapper = new ObjectMapper();

  public QueryBuilder() {
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    group = new Group();
    q = new Query();
    group = new Group();
    group.setAndOr(ANDOR.AND);
  }

  public QueryBuilder setDomain(Integer domain) {
    q.setDomain(domain);
    return this;
  }

  public QueryBuilder setModel(String model) {
    q.setModel(model);
    return this;
  }

  public QueryBuilder setPage(Integer page) {
    q.setPage(page);
    return this;
  }

  public QueryBuilder setPageSize(Integer size) {
    q.setSize(size);
    return this;
  }

  public QueryBuilder fetchModels(List<String> models) {
    q.setFetch(models);
    return this;
  }

  public QueryBuilder addObject(Object o) {
    q.addObject(o);
    return this;
  }

  public <T> QueryBuilder addObjectArray(List<T> objects) {
    objects.forEach(q::addObject);
    return this;
  }

  public QueryBuilder whereWithKeys(List<String> keys) {
    HashMap<String, List<String>>  where = new HashMap<>();
    where.put("keys", keys);
    q.setWhereKeys(where);
    return this;
  }

  public QueryBuilder newGroup(ANDOR connector) {
    q.addGroup(group);
    group = new Group();
    group.setAndOr(connector);
    return this;
  }

  public QueryBuilder setReferenceJoin(OP operator, String filterField, String referenceField, String model, Object value) {
    ReferenceJoin rj = new ReferenceJoin();
    rj.setRowModel(model);
    rj.setReferenceField(referenceField);
    rj.setFilter(makeCondition(ANDOR.AND, filterField, operator, value));
    return this;
  }

  public QueryBuilder addCondition(ANDOR connector, String fieldName, OP operator, Object value) {
    if (!fieldName.matches("^[a-zA-Z0-9\\._-]+$")) {
      return this;
    }
    if (group.getConditions().size() < 1) {
      connector = ANDOR.AND;
    }
    group.addCondition(makeCondition(connector, fieldName, operator, value));
    return this;
  }

  public String compile() {
    if (q.getWhere().isEmpty() && !group.getConditions().isEmpty()) {
      q.addGroup(group);
    }
    HashMap<String,Object> query = q.getQuery();
    try {
      return mapper.writeValueAsString(query);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return "";
  }

  private Condition makeCondition(ANDOR connector, String fieldName, OP operator, Object value) {
    Condition condition = new Condition();
    condition.setAndOr(connector);
    condition.setField(fieldName);
    condition.setOp(operator);
    condition.setVal(value);
    return condition;
  }

}
