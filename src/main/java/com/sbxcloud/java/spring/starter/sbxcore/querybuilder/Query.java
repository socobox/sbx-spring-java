package com.sbxcloud.java.spring.starter.sbxcore.querybuilder;



import java.util.*;
import java.util.stream.Collectors;


public class Query {

  private Integer domain;
  private String model;
  private Integer page = 1;
  private Integer size = 1000;
  private List<Group> where = new ArrayList<>();
  private Set<String> fetch = new HashSet<>();
  private List<Object> rows = new ArrayList<>();
  private ReferenceJoin referenceJoin;
  private HashMap<String, List<String>> whereKeys;

  public HashMap<String, Object> getQuery () {
    HashMap<String, Object> hash = new HashMap<String, Object>();
    hash.put("domain", domain);
    hash.put("row_model", model);
    hash.put("page", page);
    hash.put("size", size);
    if (where != null) {
      hash.put("where", where);
    }
    if (whereKeys != null) {
      hash.put("where", whereKeys);
    }
    if (rows != null && !rows.isEmpty()) {
      hash.remove("where");
      hash.put("rows", rows);
    }
    if (fetch.size() > 0) {
      hash.put("fetch", fetch);
    }
    if(referenceJoin != null) {
      hash.put("reference_join", referenceJoin.getReferenceJoin());
    }
    return hash;
  }

  public void addGroup(Group g) {
    this.where.add(g);
    if (whereKeys != null) {
      this.removeWhereKeys();
    }
    if (rows != null) {
      this.removeRows();
    }
  }

  public void addObject(Object o) {
    this.rows.add(o);
    if (whereKeys != null) {
      this.removeWhereKeys();
    }
    if (where != null) {
      this.removeWhere();
    }
  }

  private void removeWhere () {
    this.where = null;
  }

  private void removeWhereKeys () {
    this.whereKeys = null;
  }

  private void removeRows () {
    this.rows = null;
  }


  public Integer getDomain() {
    return domain;
  }

  public void setDomain(Integer domain) {
    this.domain = domain;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public Integer getPage() {
    return page;
  }

  public void setPage(Integer page) {
    this.page = page;
  }

  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

  public List<Group> getWhere() {
    return where;
  }

  public void setWhere(List<Group> where) {
    this.where = where;
  }

  public Set<String> getFetch() {
    return fetch;
  }

  public void addFetch(String... fetch) {
    this.fetch.addAll(List.of(fetch)) ;
  }

  public List<Object> getRows() {
    return rows;
  }

  public void setRows(List<Object> rows) {
    this.rows = rows;
  }

  public ReferenceJoin getReferenceJoin() {
    return referenceJoin;
  }

  public void setReferenceJoin(ReferenceJoin referenceJoin) {
    this.referenceJoin = referenceJoin;
  }

  public HashMap<String, List<String>> getWhereKeys() {
    return whereKeys;
  }

  public void setWhereKeys(HashMap<String, List<String>> whereKeys) {
    this.whereKeys = whereKeys;
  }
}