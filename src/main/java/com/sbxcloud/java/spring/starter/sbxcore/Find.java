package com.sbxcloud.java.spring.starter.sbxcore;


import com.sbxcloud.java.spring.starter.sbxcore.dao.SbxCoreRepository;
import com.sbxcloud.java.spring.starter.sbxcore.domain.SbxResponse;
import com.sbxcloud.java.spring.starter.sbxcore.querybuilder.ANDOR;
import com.sbxcloud.java.spring.starter.sbxcore.querybuilder.OP;
import com.sbxcloud.java.spring.starter.sbxcore.querybuilder.QueryBuilder;
import com.sbxcloud.java.spring.starter.sbxcore.util.SBXModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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
        this.query = new QueryBuilder().setDomain(domain).setModel(model);
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

                    HashMap<String, HashMap<String, Map<String, Object>>> fetch = new HashMap<>(new HashMap<>());

                    if (tSbxResponse.getFetchModels() != null) {
                        merge(tSbxResponse.getFetchModels(), fetch);
                    }

                    return Flux.fromIterable(IntStream.range(2, tSbxResponse.getTotalPages()).boxed().collect(Collectors.toList()))
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

            rows.forEach(rowMap::put);

        });
    }


    public Mono<SbxResponse<T>> loadPage(Integer page) {
        LOG.debug("Loading page: "+page);
        this.setPage(page);
        return sbxCoreRepository.find(type, this.compile(), token)
            .map( p -> {
                LOG.debug("Loaded page:"+page);
                return p;
            });
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
