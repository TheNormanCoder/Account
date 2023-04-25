package com.example.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import com.example.account.service.transaction.ConcreteTransactionService;
import com.example.account.service.transaction.Transaction;
import com.example.account.service.transaction.TransactionResponse;
import com.example.account.service.transaction.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


@SpringBootTest
public class ConcreteTransactionServiceTest {

    private TransactionService transactionService;
    @Mock
    private RestTemplate restTemplate;

    @Value("${baseUrl}")
    private String baseUrl;

    @Value("${transactionUrl}")
    private String serviceUrl;

    @Value("${authSchema}")
    private String authSchema;

    @Value("${apiKey}")
    private String apiKey;

    @Value("${accountId}")
    private String accountId;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transactionService =new ConcreteTransactionService(restTemplate, baseUrl,serviceUrl,authSchema  ,apiKey);
    }

    @Test
    @Disabled("Questo test è ancora in fase di sviluppo")
    public void testGetTransactions() {
        List<Transaction> expectedTransactions = Arrays.asList(
                new Transaction("1234", "Credit", 100.0),
                new Transaction("5678", "Debit", 50.0));
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setList(expectedTransactions);
        ResponseEntity<TransactionResponse> responseEntity = ResponseEntity.ok(transactionResponse);
        String expectedUrl = baseUrl + serviceUrl + "/" + accountId + "?fromAccountingDate=2023-01-01&toAccountingDate=2023-04-01";
        HttpHeaders expectedHeaders = new HttpHeaders();
        expectedHeaders.set("Auth-Schema", authSchema);
        expectedHeaders.set("Api-Key", apiKey);
        HttpEntity<String> entity = new HttpEntity<>(expectedHeaders);
        when(restTemplate.exchange(expectedUrl, HttpMethod.GET, entity, TransactionResponse.class)).thenReturn(responseEntity);
        ResponseEntity<TransactionResponse> actualTransactions = transactionService.getTransactions(accountId, "2023-01-01", "2023-04-01");
        assertEquals(expectedTransactions, actualTransactions.getBody().getList());
    }

}
