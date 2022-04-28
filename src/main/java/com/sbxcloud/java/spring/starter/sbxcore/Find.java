package com.sbxcloud.java.spring.starter.sbxcore;


import com.sbxcloud.java.spring.starter.sbxcore.dao.SbxCoreRepository;
import com.sbxcloud.java.spring.starter.sbxcore.domain.SbxResponse;
import com.sbxcloud.java.spring.starter.sbxcore.querybuilder.AndOr;
import com.sbxcloud.java.spring.starter.sbxcore.querybuilder.Operator;
import com.sbxcloud.java.spring.starter.sbxcore.querybuilder.QueryBuilder;
import com.sbxcloud.java.spring.starter.sbxcore.util.SBXModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public final class Find<T> {


    private static Logger LOG = LoggerFactory.getLogger(Find.class);

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
        this.query = QueryBuilder.find().setDomain(domain).setModel(model);
    }

    public Find(String model, Class<T> type, SbxCoreRepository sbxCoreRepository, String token) {
        this.sbxCoreRepository = sbxCoreRepository;
        this.type = type;
        this.token = token;
        //String model = model;//type.getAnnotation(SBXModel.class).value();
        Integer domain = SbxCore.environment.getDomain();
        this.query = QueryBuilder.find().setDomain(domain).setModel(model);
    }

    public String compile() {
        return this.query.compile();
    }

    public Mono<SbxResponse<T>> loadAllPages() {

        return loadPage(1)
                .flatMap(tSbxResponse -> {


                    // If there was an error
                    if (!tSbxResponse.getSuccess()) {
                        return Mono.just(tSbxResponse);
                    }

                    LinkedList<T> items = new LinkedList<T>(tSbxResponse.getResults());

                    var fetch = new HashMap<String, HashMap<String, Map<String, Object>>>(new HashMap<>());

                    if (tSbxResponse.getFetchModels() != null) {
                        merge(tSbxResponse.getFetchModels(), fetch);
                    }

                    return Flux.fromIterable(IntStream.rangeClosed(2, tSbxResponse.getTotalPages())
                            .boxed()
                            .collect(Collectors.toList()))
                            .delayElements(Duration.ofMillis(50))
                            .flatMap(this::loadPage, 2)
                            .map(res -> {
                                items.addAll(res.getResults());
                                if (res.getFetchModels() != null) {
                                    merge(res.getFetchModels(), fetch);
                                }
                                return res;
                            })
                            .collectList()
                            .map(ls -> {
                                tSbxResponse.setFetchModels(fetch);
                                tSbxResponse.setResults(items);
                                return tSbxResponse;
                            });

                });
    }


    private void merge(HashMap<String, HashMap<String, Map<String, Object>>> source, HashMap<String, HashMap<String, Map<String, Object>>> target) {


        source.forEach((modelName, rows) -> {

            if (!target.containsKey(modelName)) {
                target.put(modelName, new HashMap<>());
            }

            HashMap<String, Map<String, Object>> rowMap = target.get(modelName);

            rowMap.putAll(rows);
        });
    }


    public Mono<SbxResponse<T>> loadPage(Integer page) {
        this.setPage(page);
        System.out.println("loading page "+page+" "+ LocalDateTime.now());
        return sbxCoreRepository.find(type, this.compile(), token);
    }

    public Find<T> newGroupWithAnd() {
        this.query.newAndGroup();
        return this;
    }

    public Find<T> newGroupWithOr() {
        this.query.newOrGroup();
        return this;
    }

    public Find<T> andWhereIsEqualTo(String field, Object value) {
        this.query.addCondition(AndOr.AND, field, Operator.EQ, value);
        return this;
    }

    public Find<T> andWhereIsNotNull(String field) {
        this.query.addCondition(AndOr.AND, field, Operator.NOT_NULL, null);
        return this;
    }

    public Find<T> andWhereIsNull(String field) {
        this.query.addCondition(AndOr.AND, field, Operator.IS_NULL, null);
        return this;
    }

    public Find<T> andWhereIsGreaterThan(String field, Object value) {
        this.query.addCondition(AndOr.AND, field, Operator.GT, value);
        return this;
    }

    public Find<T> andWhereIsLessThan(String field, Object value) {
        this.query.addCondition(AndOr.AND, field, Operator.LT, value);
        return this;
    }

    public Find<T> andWhereIsGreaterOrEqualTo(String field, Object value) {
        this.query.addCondition(AndOr.AND, field, Operator.GT_OR_EQ, value);
        return this;
    }

    public Find<T> andWhereIsLessOrEqualTo(String field, Object value) {
        this.query.addCondition(AndOr.AND, field, Operator.LT_OR_EQ, value);
        return this;
    }

    public Find<T> andWhereIsNotEqualTo(String field, Object value) {
        this.query.addCondition(AndOr.AND, field, Operator.NOT_EQ, value);
        return this;
    }

    public Find<T> andWhereItStartsWith(String field, Object value) {
        this.query.addCondition(AndOr.AND, field, Operator.LIKE, "%" + value.toString());
        return this;
    }

    public Find<T> andWhereItEndsWith(String field, Object value) {
        this.query.addCondition(AndOr.AND, field, Operator.LIKE, value.toString() + "%");
        return this;
    }

    public Find<T> andWhereItContains(String field, Object value) {
        String v = "%" + String.join("%", value.toString().trim().split(" ")) + "%";
        this.query.addCondition(AndOr.AND, field, Operator.LIKE, v);
        return this;
    }

    public Find<T> andWhereIsIn(String field, Object value) {
        this.query.addCondition(AndOr.AND, field, Operator.IN, value);
        return this;
    }

    public Find<T> andWhereIsNotIn(String field, Object value) {
        this.query.addCondition(AndOr.AND, field, Operator.NOT_IN, value);
        return this;
    }

    public Find<T> orWhereIsEqualTo(String field, Object value) {
        this.query.addCondition(AndOr.OR, field, Operator.EQ, value);
        return this;
    }

    public Find<T> orWhereIsNotNull(String field) {
        this.query.addCondition(AndOr.OR, field, Operator.NOT_NULL, null);
        return this;
    }

    public Find<T> orWhereIsNull(String field) {
        this.query.addCondition(AndOr.OR, field, Operator.IS_NULL, null);
        return this;
    }

    public Find<T> orWhereIsGreaterThan(String field, Object value) {
        this.query.addCondition(AndOr.OR, field, Operator.GT, value);
        return this;
    }

    public Find<T> orWhereIsLessThan(String field, Object value) {
        this.query.addCondition(AndOr.OR, field, Operator.LT, value);
        return this;
    }

    public Find<T> orWhereIsGreaterOrEqualTo(String field, Object value) {
        this.query.addCondition(AndOr.OR, field, Operator.GT_OR_EQ, value);
        return this;
    }

    public Find<T> orWhereIsLessOrEqualTo(String field, Object value) {
        this.query.addCondition(AndOr.OR, field, Operator.LT_OR_EQ, value);
        return this;
    }

    public Find<T> orWhereIsNotEqualTo(String field, Object value) {
        this.query.addCondition(AndOr.OR, field, Operator.NOT_EQ, value);
        return this;
    }

    public Find<T> orWhereItStartsWith(String field, String value) {
        this.query.addCondition(AndOr.OR, field, Operator.LIKE, "%" + value);
        return this;
    }

    public Find<T> orWhereItEndsWith(String field, String value) {
        this.query.addCondition(AndOr.OR, field, Operator.LIKE, value + "%");
        return this;
    }

    public Find<T> orWhereItContains(String field, String value) {
        String v = "%" + String.join("%", value.trim().split(" ")) + "%";
        this.query.addCondition(AndOr.OR, field, Operator.LIKE, v);
        return this;
    }

    public Find<T> orWhereIsIn(String field, Collection<Object> value) {
        this.query.addCondition(AndOr.OR, field, Operator.IN, value);
        return this;
    }

    public Find<T> orWhereIsNotIn(String field, Collection<Object> value) {
        this.query.addCondition(AndOr.OR, field, Operator.NOT_IN, value);
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

    public Find<T> fetchModels(String... models) {
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
