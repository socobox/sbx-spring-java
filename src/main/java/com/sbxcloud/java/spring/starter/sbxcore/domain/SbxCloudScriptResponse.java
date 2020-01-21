package com.sbxcloud.java.spring.starter.sbxcore.domain;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class SbxCloudScriptResponse<T> {

    private Boolean success;

    private CloudScriptRun cloudScriptRun;

    private String error;

    private RawResponse<T> response;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public CloudScriptRun getCloudScriptRun() {
        return cloudScriptRun;
    }

    public void setCloudScriptRun(CloudScriptRun cloudScriptRun) {
        this.cloudScriptRun = cloudScriptRun;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public RawResponse<T> getResponse() {
        return response;
    }

    public void setResponse(RawResponse<T> response) {
        this.response = response;
    }

    public static class RawResponse<T> {

        private T body;


        public T getBody() {
            return body;
        }

        public void setBody(T body) {
            this.body = body;
        }
    }




}
