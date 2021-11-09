package com.sbxcloud.java.spring.starter.sbxcore.querybuilder;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Operator {

  EQ, LT, GT, LT_OR_EQ, GT_OR_EQ, NOT_EQ, IS_NULL, NOT_NULL, IN, NOT_IN, LIKE;

  @JsonValue
  @Override
  public String toString() {

    return switch (this) {
      case LT -> "<";
      case GT -> ">";
      case LT_OR_EQ -> "<=";
      case GT_OR_EQ -> ">=";
      case NOT_EQ -> "!=";
      case IS_NULL -> "IS";
      case NOT_NULL -> "IS NOT";
      case IN -> "IN";
      case NOT_IN -> "NOT IN";
      case LIKE -> "LKE";
      default -> "=";
    };

  }
}
