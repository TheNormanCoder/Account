package com.example.account;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import com.example.account.service.transaction.ConcreteTransactionService;
import com.example.account.service.transaction.repository.TransactionResponseRepository;
import com.example.account.service.transaction.response.Transaction;
import com.example.account.service.transaction.response.TransactionResponse;
import com.example.account.service.transaction.TransactionService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.client.RestTemplate;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;


@SpringBootTest
@ActiveProfiles("test")
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

    @Mock
    private TransactionResponseRepository transactionResponseRepository;


    public static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.4.10"))
            .withExposedPorts(27017);

    @BeforeAll
    public static void startMongoDBContainer() {
        mongoDBContainer.start();
    }
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transactionService  = new ConcreteTransactionService(restTemplate, baseUrl, serviceUrl, authSchema, apiKey, transactionResponseRepository);
    }


    @DynamicPropertySource
    public static void mongoProperties(DynamicPropertyRegistry registry) {
        String uri = String.format("mongodb://%s:%d/testdb",
                mongoDBContainer.getContainerIpAddress(),
                mongoDBContainer.getMappedPort(27017));
        registry.add("spring.data.mongodb.uri", () -> uri);
    }
    @Test
    public void testGetTransactions() {
        List<Transaction> expectedTransactions = Arrays.asList(
                new Transaction("1234", "Credit", new BigDecimal(100.0)),
                new Transaction("5678", "Debit", new BigDecimal(50.0)));
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setList(expectedTransactions);
        ResponseEntity<TransactionResponse> responseEntity = ResponseEntity.ok(transactionResponse);
        String expectedUrl = baseUrl + serviceUrl.replace("{accountId}", accountId) + "?fromAccountingDate=2023-01-01&toAccountingDate=2023-04-01";
        HttpHeaders expectedHeaders = new HttpHeaders();
        expectedHeaders.set("Auth-Schema", authSchema);
        expectedHeaders.set("Api-Key", apiKey);
        HttpEntity<String> entity = new HttpEntity<>(expectedHeaders);
        when(restTemplate.exchange(expectedUrl, HttpMethod.GET, entity, TransactionResponse.class)).thenReturn(responseEntity);
        ResponseEntity<TransactionResponse> actualTransactions = transactionService.getTransactions(accountId, "2023-01-01", "2023-04-01");
        verify(restTemplate).exchange(expectedUrl, HttpMethod.GET, entity, TransactionResponse.class);
        assertEquals(expectedTransactions, actualTransactions.getBody().getList());
        // Verifica che il metodo saveAll() del repository sia stato chiamato con la lista corretta di transazioni
        verify(transactionResponseRepository).saveAll(expectedTransactions);
    }


}
