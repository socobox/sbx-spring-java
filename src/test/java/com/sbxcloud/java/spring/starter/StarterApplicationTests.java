package com.sbxcloud.java.spring.starter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sbxcloud.java.spring.starter.sbxcore.SbxCore;
import com.sbxcloud.java.spring.starter.sbxcore.domain.SbxCloudScriptResponse;
import com.sbxcloud.java.spring.starter.sbxcore.domain.SbxResponse;
import com.sbxcloud.java.spring.starter.sbxcore.util.SBXModel;
import com.sbxcloud.java.spring.starter.sbxcore.util.SBXReference;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import javax.security.auth.login.LoginException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class StarterApplicationTests {


    @Autowired
    private SbxCore svc;

    @Order(1)
    @Test
    void contextLoads() {

        Mono<SbxResponse<Audit>> res = svc.login(System.getenv("TEST_LOGIN"), System.getenv("TEST_PASSWORD"), svc.currentDomain())
                .flatMap(loginResponse -> {

                    if (!loginResponse.getSuccess()) {
                        return Mono.error(new LoginException("Invalid Credentials"));
                    }

                    return svc.find(Audit.class, loginResponse.getToken())
                            .andWhereIsEqualTo("action", "DELETE")
                            .fetchModels("user").loadAllPages();
                });


        StepVerifier.create(res).expectNextMatches(productSbxResponse -> {
            return productSbxResponse.getResults().size() == 7268;
        }).verifyComplete();

    }


    @Order(2)
    @Test
    void update() {


        Mono<SbxResponse<Audit>> res = svc.login(System.getenv("TEST_LOGIN"), System.getenv("TEST_PASSWORD"), svc.currentDomain())
                .flatMap(loginResponse -> {

                    if (!loginResponse.getSuccess()) {
                        return Mono.error(new LoginException("Invalid Credentials"));
                    }

                    return svc.find(Audit.class, loginResponse.getToken())
                            .andWhereIsEqualTo("action", "TEST")
                            .fetchModels("user").loadAllPages()
                    .map(response -> response.getResults().stream().findFirst())
                            .flatMap(it->{
                                Audit t = it.orElseThrow(IndexOutOfBoundsException::new);
                                System.out.println(t._KEY);
                                t.action = "DELETE";
                                return svc.upsert(Audit.class, t, loginResponse.getToken());
                            });


                });


        StepVerifier.create(res).expectNextMatches(SbxResponse::getSuccess).verifyComplete();

    }

//
//    @Test
//    void csRun() {
//
//        Mono<SbxCloudScriptResponse<AuditData>> res = svc.login(System.getenv("TEST_LOGIN"), System.getenv("TEST_PASSWORD"), svc.currentDomain())
//                .flatMap(loginResponse -> {
//
//                    if (!loginResponse.getSuccess()) {
//                        return Mono.error(new LoginException("Invalid Credentials"));
//                    }
//
//                    return svc.run("09F4BED8-7976-43E7-8896-DD249B908991", AuditData.class, Collections.emptyMap(), false,loginResponse.getToken());
//                });
//
//
//        StepVerifier.create(res).expectNextMatches(productSbxResponse -> {
//            System.out.println(productSbxResponse.getResponse().getBody());
//            return productSbxResponse.getSuccess();
//        }).verifyComplete();
//
//    }


    @SBXModel("user")
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class User {

        private String role;


        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        @Override
        public String toString() {
            return "User{" +
                    "role='" + role + '\'' +
                    '}';
        }
    }



    @SBXModel("audit")
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Audit {


        private String _KEY;

        private String action;


        private String user;


        @JsonIgnore
        @SBXReference(model = "user", keyField = "user")
        private User userRef;

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public User getUserRef() {
            return userRef;
        }

        public void setUserRef(User userRef) {
            this.userRef = userRef;
        }

        public String getAction() {
            return action;
        }

        public void setAction(String action) {
            this.action = action;
        }


        @Override
        public String toString() {
            return "Audit{" +
                    "action='" + action + '\'' +
                    ", user='" + user + '\'' +
                    ", userRef=" + userRef +
                    '}';
        }

        public String get_KEY() {
            return _KEY;
        }

        public void set_KEY(String _KEY) {
            this._KEY = _KEY;
        }
    }

    private static class AuditData{

        private List<Audit> items;

        public List<Audit> getItems() {
            return items;
        }

        public void setItems(List<Audit> items) {
            this.items = items;
        }

        @Override
        public String toString() {
            return "AuditData{" +
                    "items=" + items +
                    '}';
        }
    }


    @SBXModel("add_masterlist")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Product {

        public Product() {
        }

        @JsonProperty("_KEY")
        private String key;

        private String variety;


        @JsonProperty("product_group")
        private String productGroup;

        private String searchText;

        private Integer length;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getVariety() {
            return variety;
        }

        public void setVariety(String variety) {
            this.variety = variety;
        }

        public String getProductGroup() {
            return productGroup;
        }

        public void setProductGroup(String productGroup) {
            this.productGroup = productGroup;
        }

        public String getSearchText() {
            return searchText;
        }

        public void setSearchText(String searchText) {
            this.searchText = searchText;
        }

        public Integer getLength() {
            return length;
        }

        public void setLength(Integer length) {
            this.length = length;
        }

        @Override
        public String toString() {
            return "Product{" +
                    "key='" + key + '\'' +
                    ", variety='" + variety + '\'' +
                    ", productGroup='" + productGroup + '\'' +
                    ", searchText='" + searchText + '\'' +
                    ", length=" + length +
                    '}';
        }
    }


}
