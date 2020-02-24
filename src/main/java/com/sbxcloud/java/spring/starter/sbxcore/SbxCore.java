package com.sbxcloud.java.spring.starter.sbxcore;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbxcloud.java.spring.starter.sbxcore.dao.SbxCoreRepository;
import com.sbxcloud.java.spring.starter.sbxcore.domain.SbxCloudScriptResponse;
import com.sbxcloud.java.spring.starter.sbxcore.domain.SbxResponse;
import com.sbxcloud.java.spring.starter.sbxcore.login.LoginResponse;
import com.sbxcloud.java.spring.starter.sbxcore.querybuilder.QueryBuilder;
import com.sbxcloud.java.spring.starter.sbxcore.util.SBXModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.lang.reflect.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SbxCore {

    public static Environment environment = new Environment();
    private static HashMap<String, String> urls = new HashMap<>();

    private static Logger LOG = LoggerFactory.getLogger(SbxCore.class);


    private final SbxCoreRepository sbxCoreRepository;

    static {
        urls.put("updatePassword", "/user/v1/password");
        urls.put("login", "/user/v1/login");
        urls.put("register", "/user/v1/register");
        urls.put("validate", "/user/v1/validate");
        urls.put("row", "/data/v1/row");
        urls.put("find", "/data/v1/row/find");
        urls.put("update", "/data/v1/row/update");
        urls.put("delete", "/data/v1/row/delete");
        urls.put("downloadFile", "/content/v1/download");
        urls.put("uploadFile", "/content/v1/upload");
        urls.put("addFolder", "/content/v1/folder");
        urls.put("folderList", "/content/v1/folder");
        urls.put("sendMail", "/email/v1/send");
        urls.put("paymentCustomer", "/payment/v1/customer");
        urls.put("paymentCard", "/payment/v1/card'");
        urls.put("paymentToken", "/payment/v1/token");
        urls.put("password", "/user/v1/password/request");
        urls.put("cloudScriptRun", "/cloudscript/v1/run");
    }

    public static String getUrl(String key) {
        return SbxCore.environment.getBaseUrl() + SbxCore.urls.get(key);
    }

    public SbxCore(Integer domain, String appKey, SbxCoreRepository sbxCoreRepository) {

        environment.setDomain(domain);
        environment.setAppKey(appKey);
        this.sbxCoreRepository = sbxCoreRepository;
    }


    public Integer currentDomain() {
        return environment.getDomain();
    }

    public <T> Find<T> find(Class<T> clazz, String token) {
        return new Find<>(clazz, sbxCoreRepository, token);
    }


    /**
     * Utility method to convert a JSON string to a CloudScript response so you can test with simple JSON strings.
     */
    public static <T> SbxCloudScriptResponse<T> parseBody(Class<?> clazz, String response) {
        ObjectMapper mapper = new ObjectMapper();
        JavaType type = mapper.getTypeFactory().constructParametricType(SbxCloudScriptResponse.class, clazz);
        try {
            return mapper.<SbxCloudScriptResponse<T>>readValue(response, type);
        } catch (JsonProcessingException e) {
            e.printStackTrace();

            LOG.error("Error Connecting to SBX: ", e);
            SbxCloudScriptResponse<T> bad = new SbxCloudScriptResponse<>();
            bad.setSuccess(false);
            bad.setError(e.getMessage());
            return bad;
        }
    }


    @SuppressWarnings("unchecked")
    public <T> Mono<SbxResponse<T>> upsert(Class<T> clazz , List<T> data,String token) {
        String model = clazz.getAnnotation(SBXModel.class).value();
        String body = new QueryBuilder().setModel(model).setDomain(SbxCore.environment.getDomain()).addObjectArray(data).compile();
        return sbxCoreRepository.upsert(clazz, body, token);
    }


    public Mono<LoginResponse> login(String user, String password, Integer domainId) {
        return sbxCoreRepository.login(user, password, domainId);
    }

    public <T> Mono<SbxResponse<T>> upsert(Class<T> clazz, T object,String token) {
        return upsert(clazz, Collections.singletonList(object),token);
    }

    public <T> Mono<SbxCloudScriptResponse<T>> run(String cloudScriptKey, Class<T> clazz, Map<String, Object> params, Boolean test, String token) {
        return sbxCoreRepository.run(cloudScriptKey, clazz, params, test, token);
    }

}
