package com.sbxcloud.java.spring.starter.sbxcore.querybuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class QueryBuilder {

  private Query q;
  private Group group;
  private ObjectMapper mapper = new ObjectMapper();

  private QueryBuilder() {
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    q = new Query();
  }

  public static QueryBuilder find(){
    var query =  new QueryBuilder();
    query.group = new Group(AndOr.AND);
    query.q.addGroup(query.group);
    return query;
  }

  public static QueryBuilder upsert(){
    return  new QueryBuilder();
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

  public QueryBuilder fetchModels(String... models) {
    q.addFetch(models);
    return this;
  }

  public <T> QueryBuilder addObject(T o) {
    q.addObject(o);
    return this;
  }

  public <T> QueryBuilder addObjects(List<T> objects) {
    objects.forEach(q::addObject);
    return this;
  }

  public QueryBuilder whereWithKeys(List<String> keys) {
    HashMap<String, List<String>>  where = new HashMap<>();
    where.put("keys", keys);
    q.setWhereKeys(where);
    return this;
  }

  public QueryBuilder newOrGroup() {
    this.group = new Group(AndOr.OR);
    q.addGroup(this.group);
    return this;
  }

  public QueryBuilder newAndGroup() {
    this.group = new Group(AndOr.AND);
    q.addGroup(this.group);
    return this;
  }

  public QueryBuilder setReferenceJoin(Operator operator, String filterField, String referenceField, String model, Object value) {
    ReferenceJoin rj = new ReferenceJoin();
    rj.setRowModel(model);
    rj.setReferenceField(referenceField);
    rj.setFilter(new Condition(AndOr.AND, filterField, operator, value));
    return this;
  }

  public QueryBuilder addCondition(AndOr connector, String fieldName, Operator operator, Object value) {
    if (!fieldName.matches("^[a-zA-Z0-9\\._-]+$")) {
      return this;
    }
    if (group.getConditions().size() < 1) {
      connector = AndOr.AND;
    }
    group.addCondition(new Condition(connector, fieldName, operator, value));
    return this;
  }

  public String compile() {
    HashMap<String,Object> query = q.getQuery();
    try {
      return mapper.writeValueAsString(query);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    return "";
  }



}
