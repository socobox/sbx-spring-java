package com.sbxcloud.java.spring.starter.sbxcore;


public class Environment {

    private Integer domain;
    private String baseUrl = "https://sbxcloud.com/api";
    private String appKey;


    public Integer getDomain() {

        if (this.domain == null) {
            throw new RuntimeException("You need to define a domain id on the properties file as: sbx.domain=<id-of-your-domain>");
        }

        return domain;
    }

    public void setDomain(Integer domain) {
        this.domain = domain;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getAppKey() {

      if (this.domain == null) {
        throw new RuntimeException("You need to define an App-Key in order to be able to communicate with SBX Cloud, define it on the properties file as: sbx.app-key=<your-app-key>");
      }

      return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }
}
