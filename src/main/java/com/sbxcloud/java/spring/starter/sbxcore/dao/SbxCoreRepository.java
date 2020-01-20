package com.sbxcloud.java.spring.starter.sbxcore.dao;


import com.sbxcloud.java.spring.starter.sbxcore.domain.SbxCloudScriptResponse;
import com.sbxcloud.java.spring.starter.sbxcore.domain.SbxResponse;
import com.sbxcloud.java.spring.starter.sbxcore.login.LoginResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

public interface SbxCoreRepository {

    <T> Mono<SbxResponse<T>> find(Class<T> model, String body, String token);

    <T> Mono<SbxResponse<T>> upsert(Class<T> model, String body, String token);

    <T> Mono<SbxCloudScriptResponse<T>> run(String key, Class<?> clazz, Map<String, Object> params, Boolean test,String token);

    Mono<LoginResponse> login(String user, String password, Integer domainId);
}
