package com.sbxcloud.java.spring.starter;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sbxcloud.java.spring.starter.sbxcore.SbxCore;
import com.sbxcloud.java.spring.starter.sbxcore.domain.SbxResponse;
import com.sbxcloud.java.spring.starter.sbxcore.util.SBXModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import javax.security.auth.login.LoginException;
import java.util.Arrays;

@SpringBootTest
class StarterApplicationTests {


    @Autowired
    private SbxCore svc;

    @Test
    void contextLoads() {

        Mono<SbxResponse<Product>> res = svc.login(System.getenv("TEST_LOGIN"), System.getenv("TEST_PASSWORD"), svc.currentDomain())
                .flatMap(loginResponse -> {

                    if (!loginResponse.getSuccess()) {
                        return Mono.error(new LoginException("Invalid Credentials"));
                    }

                    return svc.find(Product.class, loginResponse.getToken())
                            .andWhereIsEqualTo("active", true)
                            .andWhereIsEqualTo("grower.active", true)
                            .fetchModels(Arrays.asList("variety", "product_group", "variety.color")).send();
                });


        StepVerifier.create(res).expectNextMatches(productSbxResponse -> {
            System.out.println(productSbxResponse.getResults());
            return productSbxResponse.getSuccess();
        }).verifyComplete();

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
