package com.sbxcloud.java.spring.starter.sbxcore.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbxcloud.java.spring.starter.sbxcore.SbxCore;
import com.sbxcloud.java.spring.starter.sbxcore.domain.SbxCloudScriptResponse;
import com.sbxcloud.java.spring.starter.sbxcore.domain.SbxResponse;
import com.sbxcloud.java.spring.starter.sbxcore.login.LoginResponse;
import com.sbxcloud.java.spring.starter.sbxcore.util.SBXReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.LoggingCodecSupport;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.io.IOException;
import java.lang.reflect.Field;

import java.util.*;


@Repository
public class SbxCoreRepositoryImpl implements SbxCoreRepository {



    private static Logger LOG = LoggerFactory.getLogger(SbxCoreRepository.class);


    private String sbxFind = SbxCore.getUrl("find");

    private String sbxUpdate = SbxCore.getUrl("update");

    private String sbxLogin = SbxCore.getUrl("login");

    private String sbxInsert = SbxCore.getUrl("row");
    private String sbxSendEMail = SbxCore.getUrl("sendMail");

    private String sbxRun = SbxCore.getUrl("cloudScriptRun");


    @Override
    public <T> Mono<SbxResponse<T>> find(Class<T> model, String body, String token) {
        // LOG.debug(model.getAnnotation(SbxModelName.class).value());
        try {
            return getSbxResponse(sbxFind, body, model, token);
        } catch (Exception ex) {
            LOG.error("Find " + model.getName() + " ", ex);
            return handleError(ex);
        }
    }

    @Override
    public <T> Mono<SbxResponse<T>> upsert(Class<T> model, String body, String token) {
        try {
            String url = body.contains("_KEY") ? sbxUpdate : sbxInsert;
            return getSbxResponse(url, body, model, token);
        } catch (Exception ex) {
            LOG.debug("Upsert " + model.getName() + " " + ex.getMessage());
            return handleError(ex);
        }
    }

    @Override
    public <T> Mono<SbxCloudScriptResponse<T>> run(String key, Class<?> clazz, Map<String, Object> params, Boolean test,String token) {


        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("key",key);
        objectMap.put("params",params);
        objectMap.put("test",test);



        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 5000)).build();

//        exchangeStrategies.messageWriters().stream()
//                .filter(LoggingCodecSupport.class::isInstance)
//                .forEach(writer -> ((LoggingCodecSupport)writer).setEnableLoggingRequestDetails(true));
//
//        HttpClient httpClient = HttpClient
//                .create()
//                .wiretap(true);


        WebClient webClient = WebClient.builder()
                .exchangeStrategies(exchangeStrategies)
//                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();

        return webClient.post()
                .uri(sbxRun)
                .headers(httpHeaders -> httpHeaders.setAll(getHeaders(token).toSingleValueMap()))
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(objectMap))
                .exchange()
                .flatMap(res -> {
                    return res.bodyToMono(String.class);
                })
                .map(response -> SbxCore.parseBody(clazz, response));
    }



    @Override
    public Mono<LoginResponse> login(String user, String password, Integer domainId) {

        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 5000)).build();

        WebClient webClient = WebClient.builder().exchangeStrategies(exchangeStrategies).build();


        return webClient.get()
                .uri(UriComponentsBuilder
                        .fromHttpUrl(sbxLogin)
                        .queryParam("domain", domainId)
                        .queryParam("login", user)
                        .queryParam("password", password).toUriString())
                .headers(httpHeaders -> httpHeaders.setAll(getHeaders(null).toSingleValueMap()))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(LoginResponse.class);

    }

    private HttpHeaders getHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setContentType(MediaType.APPLICATION_JSON);
        Optional.ofNullable(token).ifPresent(headers::setBearerAuth);
        headers.set("App-Key", SbxCore.environment.getAppKey());
        return headers;
    }

    private <T> Mono<SbxResponse<T>> getSbxResponse(String url, String body, Class<?> clazz, String token) throws IOException {

        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()

                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024)).build();

        WebClient webClient = WebClient.builder().exchangeStrategies(exchangeStrategies).build();


        return webClient.post()
                .uri(url)

                .headers(httpHeaders -> httpHeaders.setAll(getHeaders(token).toSingleValueMap()))
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(body))
                .retrieve()
                .bodyToMono(String.class)
                .map(response -> {
                    try {
                        return handleSbxResponse(response, clazz);
                    } catch (IOException e) {
                        LOG.error("Error Connecting to SBX: ", e);
                        SbxResponse<T> bad = new SbxResponse<>();
                        bad.setSuccess(false);
                        bad.setError(e.getMessage());
                        return bad;
                    }
                });

    }

    private <T> SbxResponse<T> handleSbxResponse(String response, Class<?> clazz) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JavaType type = mapper.getTypeFactory().constructParametricType(SbxResponse.class, clazz);
        SbxResponse<T> r = mapper.readValue(response, type);
        mapReferences(mapper, r.getResults(), clazz, r.getFetchModels());
        return r;
    }

    @SuppressWarnings("unchecked")
    private <T> void mapReferences(ObjectMapper mapper, List<T> items, Class<?> clazz, HashMap<String, HashMap<String, Map<String, Object>>> fetch) {

        for (Field field : clazz.getDeclaredFields()) {
            Class fieldType = field.getType();
            String fieldName = field.getName();

            if (fetch!=null && field.isAnnotationPresent(SBXReference.class)) {

                SBXReference a = field.getAnnotation(SBXReference.class);
                Map<String, Map<String, Object>> map = fetch.get(a.model());
                LinkedList ls = new LinkedList();

                items.forEach(item -> {

                    String key = get(item, a.keyField());

                    // only process the key if it is not null and it part of the fetched results.
                    if (key != null && map.containsKey(key)) {

                        String refKey = get(item, a.keyField());
                        Map<String, Object> rawMap = map.get(refKey);
                        T obj = (T) mapper.convertValue(rawMap, fieldType);

                        // create a list of the newly created objects to pass them throught the reference mapper too
                        ls.add(obj);

                        set(item, fieldName, obj);
                    }


                });


                // map the nested references of the new objects.
                if (!ls.isEmpty()) {
                    mapReferences(mapper, ls, fieldType, fetch);
                }


            }

        }


    }


    @SuppressWarnings("unchecked")
    public static <V> V get(Object object, String fieldName) {
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                return (V) field.get(object);
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        return null;
    }

    public static boolean set(Object object, String fieldName, Object fieldValue) {
        Class<?> clazz = object.getClass();
        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField(fieldName);
                field.setAccessible(true);
                field.set(object, fieldValue);
                return true;
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        }
        return false;
    }

    private <T> Mono<SbxResponse<T>> handleError(Exception ex) {
        SbxResponse<T> res = new SbxResponse<>();
        res.setSuccess(false);
        res.setError(ex.getMessage());
        return Mono.just(res);
    }

}
