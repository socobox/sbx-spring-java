package com.sbxcloud.java.spring.starter.sbxcore.login;


import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "name",
        "code",
        "email",
        "login",
        "role",
        "domain",
        "display_name",
        "domain_id",
        "membership_role",
        "member_of",
        "following",
        "home_folder_key"
})
public class User {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("code")
    private Object code;
    @JsonProperty("email")
    private String email;
    @JsonProperty("login")
    private String login;
    @JsonProperty("role")
    private String role;
    @JsonProperty("domain")
    private String domain;
    @JsonProperty("display_name")
    private String displayName;
    @JsonProperty("domain_id")
    private Integer domainId;
    @JsonProperty("membership_role")
    private String membershipRole;
    @JsonProperty("member_of")
    private List<Membership> memberOf = null;
    @JsonProperty("following")
    private List<Object> following = null;
    @JsonProperty("home_folder_key")
    private String homeFolderKey;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("code")
    public Object getCode() {
        return code;
    }

    @JsonProperty("code")
    public void setCode(Object code) {
        this.code = code;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("login")
    public String getLogin() {
        return login;
    }

    @JsonProperty("login")
    public void setLogin(String login) {
        this.login = login;
    }

    @JsonProperty("role")
    public String getRole() {
        return role;
    }

    @JsonProperty("role")
    public void setRole(String role) {
        this.role = role;
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

    @JsonProperty("domain_id")
    public Integer getDomainId() {
        return domainId;
    }

    @JsonProperty("domain_id")
    public void setDomainId(Integer domainId) {
        this.domainId = domainId;
    }

    @JsonProperty("membership_role")
    public String getMembershipRole() {
        return membershipRole;
    }

    @JsonProperty("membership_role")
    public void setMembershipRole(String membershipRole) {
        this.membershipRole = membershipRole;
    }

    @JsonProperty("member_of")
    public List<Membership> getMemberOf() {
        return memberOf;
    }

    @JsonProperty("member_of")
    public void setMemberOf(List<Membership> memberOf) {
        this.memberOf = memberOf;
    }

    @JsonProperty("following")
    public List<Object> getFollowing() {
        return following;
    }

    @JsonProperty("following")
    public void setFollowing(List<Object> following) {
        this.following = following;
    }

    @JsonProperty("home_folder_key")
    public String getHomeFolderKey() {
        return homeFolderKey;
    }

    @JsonProperty("home_folder_key")
    public void setHomeFolderKey(String homeFolderKey) {
        this.homeFolderKey = homeFolderKey;
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