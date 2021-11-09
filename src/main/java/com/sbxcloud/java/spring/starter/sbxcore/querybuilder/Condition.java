package com.sbxcloud.java.spring.starter.sbxcore.querybuilder;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;


public record Condition(@JsonProperty("ANDOR") AndOr andOr,
                        @JsonProperty("FIELD") String field,
                        @JsonProperty("OP") Operator operator,
                        @JsonProperty("VAL") Object value) {


  @JsonIgnore
  public HashMap<String, Object> getCondition() {
    HashMap<String, Object> hash = new HashMap<>();
    hash.put("ANDOR", andOr);
    hash.put("VAL", value);
    hash.put("FIELD", field);
    hash.put("OP", operator.toString());
    return hash;
  }

}
