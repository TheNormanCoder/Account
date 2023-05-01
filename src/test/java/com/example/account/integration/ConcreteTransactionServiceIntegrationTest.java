package com.example.account.integration;

import com.example.account.service.transaction.ConcreteTransactionService;

import com.example.account.service.transaction.response.TransactionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.util.UriComponentsBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Disabled
public class ConcreteTransactionServiceIntegrationTest {


    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers;

    private String baseUrl;

    private String transactionUrl;

    private String accountId;

    private String authSchema;
    private String apiKey;

    @BeforeEach
    public void setup() {

        baseUrl = "https://sandbox.platfr.io";
        accountId = "14537780";
        transactionUrl = "/api/gbs/banking/v4.0/accounts/{accountId}/transactions";
        authSchema = "S2S";
        apiKey = "FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP";
        headers = new HttpHeaders();
        headers.add("Auth-Schema", "S2S");
        headers.add("Api-Key", "FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP");

    }

    @Test
    public void testGetTransaction() {
        String url = UriComponentsBuilder.fromHttpUrl(this.baseUrl)
                .path(this.transactionUrl)
                .queryParam("fromAccountingDate", "2023-01-01")
                .queryParam("toAccountingDate", "2023-04-01")
                .buildAndExpand(this.accountId)
                .toUriString();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<TransactionResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, TransactionResponse.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    @Test
    public void testGetTransactions() {
        String fromDate = "2023-01-01";
        String toDate = "2023-04-01";
        ConcreteTransactionService transactionService = new ConcreteTransactionService(restTemplate.getRestTemplate(), baseUrl, transactionUrl, authSchema, apiKey);

        ResponseEntity<TransactionResponse> transactions = transactionService.getTransactions(accountId, fromDate, toDate);


        assertEquals(null, transactions.getBody().getList());
    }
}
