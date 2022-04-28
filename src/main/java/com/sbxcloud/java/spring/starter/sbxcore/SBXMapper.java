package com.sbxcloud.java.spring.starter.sbxcore;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

public class SBXMapper {

  private final Map<Object,Object> object;


  public SBXMapper(Map<Object,Object> object) {
    this.object = object;
  }

  public Integer getInt(String fieldName){
    if(!object.containsKey(fieldName)){
      return null;
    }

    var tmp = object.get(fieldName);

    if( tmp instanceof Double val){
      return val.intValue();
    }

    return null;
  }

  public Float getFloat(String fieldName){
    if(!object.containsKey(fieldName)){
      return null;
    }
    var tmp = object.get(fieldName);

    if( tmp instanceof Double val){
      return val.floatValue();
    }

    return null;
  }

  public String getString(String fieldName){
    if(!object.containsKey(fieldName)){
      return null;
    }
    var tmp = object.get(fieldName);

    if( tmp instanceof String val){
      return val;
    }

    return null;
  }

  public Date getDate(String fieldName){
    if(!object.containsKey(fieldName)){
      return null;
    }
    var tmp = object.get(fieldName);

    if( tmp instanceof Date val){
      return val;
    }

    return null;
  }

  public Boolean getBoolean(String fieldName){
    if(!object.containsKey(fieldName)){
      return null;
    }
    var tmp = object.get(fieldName);

    if( tmp instanceof Boolean val){
      return val;
    }

    return null;
  }

  public String getRef(String fieldName){
    if(!object.containsKey(fieldName)){
      return null;
    }
    var tmp = object.get(fieldName);

    if( tmp instanceof String val){
      return val;
    }

    return null;
  }

  public String getText(String fieldName){
    if(!object.containsKey(fieldName)){
      return null;
    }
    var tmp = object.get(fieldName);

    if( tmp instanceof String val){
      return val;
    }

    return null;
  }





}
