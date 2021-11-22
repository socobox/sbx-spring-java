package com.sbxcloud.java.spring.starter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sbxcloud.java.spring.starter.sbxcore.SbxCore;
import com.sbxcloud.java.spring.starter.sbxcore.domain.SbxResponse;
import com.sbxcloud.java.spring.starter.sbxcore.login.LoginResponse;
import com.sbxcloud.java.spring.starter.sbxcore.util.SBXModel;
import com.sbxcloud.java.spring.starter.sbxcore.util.SBXReference;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class StarterApplicationTests {


  @Autowired
  private SbxCore svc;

  private String token;

  @BeforeEach
  void login() {

    Mono<LoginResponse> res = svc.login(System.getenv("TEST_LOGIN"), System.getenv("TEST_PASSWORD"), svc.currentDomain());

    StepVerifier.create(res).expectNextMatches(productSbxResponse -> {

      if(productSbxResponse != null && productSbxResponse.getSuccess()){
        token = productSbxResponse.getToken();
      }

      return StringUtils.hasText(token);
    }).verifyComplete();

  }

  @Order(2)
  @Test
  void create() {


    var city = new City();
    city.setCountry("20b3a7e1-b20f-491e-8240-1647c72a4f5b");
    city.setFedexAccount("8291457");
    city.setFedexName("BAQ");
    city.setName("BAQ");
    city.setDaysInAdvance("0");
    city.setFedexZipcode("33115");

    Mono<SbxResponse<City>> res = svc.upsert(City.class, Collections.singletonList(city),token);

    StepVerifier.create(res).expectNextMatches(SbxResponse::getSuccess).verifyComplete();

  }


  @Order(3)
  @Test
  void update() {


    Mono<SbxResponse<City>> res = svc.find(City.class, token)
          .andWhereIsEqualTo("name", "BAQ")
          .fetchModels("country").loadAllPages()
          .map(response -> response.getResults().stream().findFirst())
          .flatMap(it -> {
            City t = it.orElseThrow(IndexOutOfBoundsException::new);
            System.out.println(t.getKey());
            t.setName("Barranquilla");
            return svc.upsert(City.class, t, token);
          });

    StepVerifier.create(res).expectNextMatches(SbxResponse::getSuccess).verifyComplete();

  }


  @Order(4)
  @Test
  void delete() {


    Mono<SbxResponse<City>> res = svc.find(City.class, token)
      .andWhereIsEqualTo("name", "Barranquilla")
      .fetchModels("country").loadAllPages()
      .map(response -> response.getResults().stream().findFirst())
      .flatMap(it -> {
        City t = it.orElseThrow(IndexOutOfBoundsException::new);
        System.out.println(t.getKey());

        return svc.delete(City.class, Collections.singletonList(t.getKey()) , token);
      });

    StepVerifier.create(res).expectNextMatches(SbxResponse::getSuccess).verifyComplete();

  }

  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  @SBXModel("country")
  @JsonIgnoreProperties(ignoreUnknown = true)
  private static class Country {

    @JsonProperty("_KEY")
    private String key;

    private String countryIso;

    private String shipper;

    public String getKey() {
      return key;
    }

    public void setKey(String key) {
      this.key = key;
    }

    public String getCountryIso() {
      return countryIso;
    }

    public void setCountryIso(String countryIso) {
      this.countryIso = countryIso;
    }

    public String getShipper() {
      return shipper;
    }

    public void setShipper(String shipper) {
      this.shipper = shipper;
    }

    @Override
    public String toString() {
      return "Country{" +
        "_KEY='" + key + '\'' +
        ", countryIso='" + countryIso + '\'' +
        ", shipper='" + shipper + '\'' +
        '}';
    }

  }

  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  @SBXModel("city")
  @JsonIgnoreProperties(ignoreUnknown = true)
  private static class City {

    @JsonProperty("_KEY")
    private String key;

    private String name;

    private String daysInAdvance;

    private String country;

    private String fedexAccount;

    private String fedexName;

    private String fedexZipcode;


    @JsonIgnore
    @SBXReference(model = "country", keyField = "country")
    private Country countryRef;


    @Override
    public String toString() {
      return "City{" +
        "_KEY='" + key + '\'' +
        ", name='" + name + '\'' +
        ", daysInAdvance='" + daysInAdvance + '\'' +
        ", country='" + country + '\'' +
        ", fedexAccount='" + fedexAccount + '\'' +
        ", fedexName='" + fedexName + '\'' +
        ", fedexZipcode='" + fedexZipcode + '\'' +
        ", countryRef=" + countryRef +
        '}';
    }

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

    public String getDaysInAdvance() {
      return daysInAdvance;
    }

    public void setDaysInAdvance(String daysInAdvance) {
      this.daysInAdvance = daysInAdvance;
    }

    public String getCountry() {
      return country;
    }

    public void setCountry(String country) {
      this.country = country;
    }

    public String getFedexAccount() {
      return fedexAccount;
    }

    public void setFedexAccount(String fedexAccount) {
      this.fedexAccount = fedexAccount;
    }

    public String getFedexName() {
      return fedexName;
    }

    public void setFedexName(String fedexName) {
      this.fedexName = fedexName;
    }

    public String getFedexZipcode() {
      return fedexZipcode;
    }

    public void setFedexZipcode(String fedexZipcode) {
      this.fedexZipcode = fedexZipcode;
    }

    public Country getCountryRef() {
      return countryRef;
    }

    public void setCountryRef(Country countryRef) {
      this.countryRef = countryRef;
    }

    public String getKey() {
      return key;
    }

    public void setKey(String key) {
      this.key = key;
    }
  }


}
