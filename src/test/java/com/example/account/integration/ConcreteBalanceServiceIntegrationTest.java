package com.example.account.integration;

import com.example.account.service.balance.response.BalanceResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Disabled
public class ConcreteBalanceServiceIntegrationTest {


    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers;

    private String baseUrl;

    private String balanceUrl;

    private String accountId;

    @BeforeEach
    public void setup() {
        headers = new HttpHeaders();
        headers.add("Auth-Schema", "S2S");
        headers.add("Api-Key", "FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP");

        baseUrl = "https://sandbox.platfr.io";
        accountId = "14537780";
        balanceUrl = "/api/gbs/banking/v4.0/accounts/{accountId}/balance";
    }

    @Test
    public void testGetBalance() {
        String url = baseUrl + balanceUrl;
        url = url.replace("{accountId}", accountId);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<BalanceResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, BalanceResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0.0, response.getBody().getBalance());
    }

}
