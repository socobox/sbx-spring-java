package com.sbxcloud.java.spring.starter.sbxcore;


import com.sbxcloud.java.spring.starter.sbxcore.dao.SbxCoreRepository;
import com.sbxcloud.java.spring.starter.sbxcore.domain.SbxResponse;
import com.sbxcloud.java.spring.starter.sbxcore.querybuilder.ANDOR;
import com.sbxcloud.java.spring.starter.sbxcore.querybuilder.OP;
import com.sbxcloud.java.spring.starter.sbxcore.querybuilder.QueryBuilder;
import com.sbxcloud.java.spring.starter.sbxcore.util.SBXModel;
import reactor.core.publisher.Mono;

import java.util.List;

public class Find<T> {

    private QueryBuilder query;

    private final Class<T> type;

    private final SbxCoreRepository sbxCoreRepository;

    private final String token;

    public Find(Class<T> type, SbxCoreRepository sbxCoreRepository, String token) {
        this.sbxCoreRepository = sbxCoreRepository;
        this.type = type;
        this.token = token;
        String model = type.getAnnotation(SBXModel.class).value();
        Integer domain = SbxCore.environment.getDomain();
        this.query = new QueryBuilder().setDomain(domain).setModel(model);
    }

    public String compile() {
        return this.query.compile();
    }

    public Mono<SbxResponse<T>> send() {
        return sbxCoreRepository.find(type, this.compile(), token);
    }

    public Find<T> newGroupWithAnd() {
        this.query.newGroup(ANDOR.AND);
        return this;
    }

    public Find<T> newGroupWithOr() {
        this.query.newGroup(ANDOR.OR);
        return this;
    }

    public Find<T> andWhereIsEqualTo(String field, Object value) {
        this.query.addCondition(ANDOR.AND, field, OP.EQ, value);
        return this;
    }

    public Find<T> andWhereIsNotNull(String field) {
        this.query.addCondition(ANDOR.AND, field, OP.ISNOT, null);
        return this;
    }

    public Find<T> andWhereIsNull(String field) {
        this.query.addCondition(ANDOR.AND, field, OP.IS, null);
        return this;
    }

    public Find<T> andWhereIsGreaterThan(String field, Object value) {
        this.query.addCondition(ANDOR.AND, field, OP.GT, value);
        return this;
    }

    public Find<T> andWhereIsLessThan(String field, Object value) {
        this.query.addCondition(ANDOR.AND, field, OP.LT, value);
        return this;
    }

    public Find<T> andWhereIsGreaterOrEqualTo(String field, Object value) {
        this.query.addCondition(ANDOR.AND, field, OP.GET, value);
        return this;
    }

    public Find<T> andWhereIsLessOrEqualTo(String field, Object value) {
        this.query.addCondition(ANDOR.AND, field, OP.LET, value);
        return this;
    }

    public Find<T> andWhereIsNotEqualTo(String field, Object value) {
        this.query.addCondition(ANDOR.AND, field, OP.NOTEQ, value);
        return this;
    }

    public Find<T> andWhereItStartsWith(String field, Object value) {
        this.query.addCondition(ANDOR.AND, field, OP.LIKE, "%" + value.toString());
        return this;
    }

    public Find<T> andWhereItEndsWith(String field, Object value) {
        this.query.addCondition(ANDOR.AND, field, OP.LIKE, value.toString() + "%");
        return this;
    }

    public Find<T> andWhereItContains(String field, Object value) {
        String v = "%" + String.join("%", value.toString().trim().split(" ")) + "%";
        this.query.addCondition(ANDOR.AND, field, OP.LIKE, v);
        return this;
    }

    public Find<T> andWhereIsIn(String field, Object value) {
        this.query.addCondition(ANDOR.AND, field, OP.IN, value);
        return this;
    }

    public Find<T> andWhereIsNotIn(String field, Object value) {
        this.query.addCondition(ANDOR.AND, field, OP.NOTIN, value);
        return this;
    }

    public Find<T> orWhereIsEqualTo(String field, Object value) {
        this.query.addCondition(ANDOR.OR, field, OP.EQ, value);
        return this;
    }

    public Find<T> orWhereIsNotNull(String field) {
        this.query.addCondition(ANDOR.OR, field, OP.ISNOT, null);
        return this;
    }

    public Find<T> orWhereIsNull(String field) {
        this.query.addCondition(ANDOR.OR, field, OP.IS, null);
        return this;
    }

    public Find<T> orWhereIsGreaterThan(String field, Object value) {
        this.query.addCondition(ANDOR.OR, field, OP.GT, value);
        return this;
    }

    public Find<T> orWhereIsLessThan(String field, Object value) {
        this.query.addCondition(ANDOR.OR, field, OP.LT, value);
        return this;
    }

    public Find<T> orWhereIsGreaterOrEqualTo(String field, Object value) {
        this.query.addCondition(ANDOR.OR, field, OP.GET, value);
        return this;
    }

    public Find<T> orWhereIsLessOrEqualTo(String field, Object value) {
        this.query.addCondition(ANDOR.OR, field, OP.LET, value);
        return this;
    }

    public Find<T> orWhereIsNotEqualTo(String field, Object value) {
        this.query.addCondition(ANDOR.OR, field, OP.NOTEQ, value);
        return this;
    }

    public Find<T> orWhereItStartsWith(String field, Object value) {
        this.query.addCondition(ANDOR.OR, field, OP.LIKE, "%" + value.toString());
        return this;
    }

    public Find<T> orWhereItEndsWith(String field, Object value) {
        this.query.addCondition(ANDOR.OR, field, OP.LIKE, value.toString() + "%");
        return this;
    }

    public Find<T> orWhereItContains(String field, Object value) {
        String v = "%" + String.join("%", value.toString().trim().split(" ")) + "%";
        this.query.addCondition(ANDOR.OR, field, OP.LIKE, v);
        return this;
    }

    public Find<T> orWhereIsIn(String field, Object value) {
        this.query.addCondition(ANDOR.OR, field, OP.IN, value);
        return this;
    }

    public Find<T> orWhereIsNotIn(String field, Object value) {
        this.query.addCondition(ANDOR.OR, field, OP.NOTIN, value);
        return this;
    }

    public Find<T> joinBetween(String field, Object value) {
        // TODO: fill this function
        return this;
    }

    public Find<T> whereWithKeys(List<String> keys) {
        this.query.whereWithKeys(keys);
        return this;
    }

    public Find<T> fetchModels(List<String> models) {
        this.query.fetchModels(models);
        return this;
    }

    public Find<T> setPage(Integer page) {
        this.query.setPage(page);
        return this;
    }

    public Find<T> setPageSize(Integer pageSize) {
        this.query.setPageSize(pageSize);
        return this;
    }
}
