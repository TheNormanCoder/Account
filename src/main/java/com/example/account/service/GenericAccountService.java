package com.example.account.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


public class GenericAccountService {

    protected final RestTemplate restTemplate;
    protected final String baseUrl;
    protected final String serviceUrl;
    protected final String authSchema;
    protected final String apiKey;


    public GenericAccountService(RestTemplate restTemplate,
                           String baseUrl,
                           String serviceUrl,
                           String authSchema,
                           String apiKey) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
        this.serviceUrl = serviceUrl;
        this.authSchema = authSchema;
        this.apiKey = apiKey;

    }

    public HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Auth-Schema", this.authSchema);
        headers.set("Api-Key",this.apiKey);
        return headers;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getAuthSchema() {
        return authSchema;
    }

    public String getApiKey() {
        return apiKey;
    }
}
