package com.sbxcloud.java.spring.starter.sbxcore.login;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "domain_id",
        "domain",
        "display_name",
        "role",
        "name",
        "home",
        "license",
        "config",
        "app",
        "home_key"
})
public class Membership {

    @JsonProperty("domain_id")
    private Integer domainId;
    @JsonProperty("domain")
    private String domain;
    @JsonProperty("display_name")
    private String displayName;
    @JsonProperty("role")
    private String role;
    @JsonProperty("name")
    private String name;
    @JsonProperty("home")
    private Object home;
    @JsonProperty("license")
    private Integer license;
    @JsonProperty("config")
    private Config config;
    @JsonProperty("app")
    private Integer app;
    @JsonProperty("home_key")
    private String homeKey;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("domain_id")
    public Integer getDomainId() {
        return domainId;
    }

    @JsonProperty("domain_id")
    public void setDomainId(Integer domainId) {
        this.domainId = domainId;
    }

    @JsonProperty("domain")
    public String getDomain() {
        return domain;
    }

    @JsonProperty("domain")
    public void setDomain(String domain) {
        this.domain = domain;
    }

    @JsonProperty("display_name")
    public String getDisplayName() {
        return displayName;
    }

    @JsonProperty("display_name")
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @JsonProperty("role")
    public String getRole() {
        return role;
    }

    @JsonProperty("role")
    public void setRole(String role) {
        this.role = role;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("home")
    public Object getHome() {
        return home;
    }

    @JsonProperty("home")
    public void setHome(Object home) {
        this.home = home;
    }

    @JsonProperty("license")
    public Integer getLicense() {
        return license;
    }

    @JsonProperty("license")
    public void setLicense(Integer license) {
        this.license = license;
    }

    @JsonProperty("config")
    public Config getConfig() {
        return config;
    }

    @JsonProperty("config")
    public void setConfig(Config config) {
        this.config = config;
    }

    @JsonProperty("app")
    public Integer getApp() {
        return app;
    }

    @JsonProperty("app")
    public void setApp(Integer app) {
        this.app = app;
    }

    @JsonProperty("home_key")
    public String getHomeKey() {
        return homeKey;
    }

    @JsonProperty("home_key")
    public void setHomeKey(String homeKey) {
        this.homeKey = homeKey;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
