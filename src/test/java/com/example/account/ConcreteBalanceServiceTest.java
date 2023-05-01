package com.example.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.example.account.service.balance.BalanceService;
import com.example.account.service.balance.ConcreteBalanceService;
import com.example.account.service.balance.response.BalanceResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

@SpringBootTest
@ActiveProfiles("test")
public class ConcreteBalanceServiceTest {

    private BalanceService balanceService;

    @Mock
    private RestTemplate restTemplate;

    @Value("${baseUrl}")
    private String baseUrl;

    @Value("${balanceUrl}")
    private String serviceUrl;

    @Value("${authSchema}")
    private String authSchema;

    @Value("${apiKey}")
    private String apiKey;

    @Value("${accountId}")
    private String accountId;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        balanceService = new ConcreteBalanceService(restTemplate, baseUrl,serviceUrl,authSchema, apiKey);
    }

    @Test
    public void testGetBalance() {
        String url = baseUrl + serviceUrl;
        url = url.replace("{accountId}", accountId);
        HttpHeaders headers = new HttpHeaders();
        headers.set("Auth-Schema", authSchema);
        headers.set("Api-Key", apiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        BalanceResponse balanceResponse = new BalanceResponse();
        balanceResponse.setBalance(new BigDecimal("100.0"));
        ResponseEntity<BalanceResponse> responseEntity = ResponseEntity.ok(balanceResponse);
        when(restTemplate.exchange(url, HttpMethod.GET, entity, BalanceResponse.class)).thenReturn(responseEntity);

        ResponseEntity<BalanceResponse> balance = balanceService.getBalance(accountId);
        assertEquals(new BigDecimal("100.0"), balance.getBody().getBalance());
    }

}
