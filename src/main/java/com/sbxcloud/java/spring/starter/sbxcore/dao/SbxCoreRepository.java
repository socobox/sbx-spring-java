package com.sbxcloud.java.spring.starter.sbxcore.dao;


import com.sbxcloud.java.spring.starter.sbxcore.domain.SbxResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface SbxCoreRepository {

    <T> Mono<SbxResponse<T>> find(Class<T> model, String body, String token);

    <T> Mono<SbxResponse<T>> upsert(Class<T> model, String body, String token);

    <T> Mono<SbxResponse<T>> run(String key, Map<String, Object> params, String token);

}
