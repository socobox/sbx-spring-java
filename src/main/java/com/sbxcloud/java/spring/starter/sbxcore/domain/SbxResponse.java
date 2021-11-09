package com.sbxcloud.java.spring.starter.sbxcore.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@JsonIgnoreProperties({"model", "cloud_script_run"})
public class SbxResponse<T> {

    private Boolean success;

    @JsonProperty("row_count")
    private Integer rowCount;

    @JsonProperty("total_pages")
    private Integer totalPages;

    private List<T> results = Collections.emptyList();

    @JsonProperty("fetched_results")
    private HashMap<String, HashMap<String, Map<String, Object>>> fetchModels;

    private List<String> keys;

    private String error;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getRowCount() {
        return rowCount;
    }

    public void setRowCount(Integer rowCount) {
        this.rowCount = rowCount;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

  
    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

    public HashMap<String, HashMap<String, Map<String, Object>>> getFetchModels() {
        return fetchModels;
    }

    public void setFetchModels(HashMap<String, HashMap<String, Map<String, Object>>> fetchModels) {
        this.fetchModels = fetchModels;
    }

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "SbxResponse{" +
          "success=" + success +
          ", rowCount=" + rowCount +
          ", totalPages=" + totalPages +
          ", results=" + results +
          ", fetchModels=" + fetchModels +
          ", keys=" + keys +
          ", error='" + error + '\'' +
          '}';
    }
}
