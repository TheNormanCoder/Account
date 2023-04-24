package com.example.account.service.transaction;

import com.example.account.service.GenericAccountService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ConcreteTransactionService extends GenericAccountService implements TransactionService {


    public ConcreteTransactionService(RestTemplate restTemplate,
                                      @Value("${baseUrl}") String baseUrl,
                                      @Value("${transactionUrl}") String serviceUrl,
                                      @Value("${authSchema}") String authSchema,
                                      @Value("${apiKey}") String apiKey) {
        super(restTemplate, baseUrl,serviceUrl, authSchema, apiKey);
    }

    @Override
    public ResponseEntity<TransactionResponse> getTransactions(String accountId, String fromDate, String toDate) {
        String url = getUrl(accountId, fromDate, toDate);
        HttpHeaders headers = getHttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<TransactionResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, TransactionResponse.class);
        if(response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok(response.getBody());
        }else {
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }
    }

    public String getUrl(String accountId, String fromDate, String toDate) {
        String url = UriComponentsBuilder.fromHttpUrl(this.baseUrl)
                .path(this.serviceUrl)
                .queryParam("fromAccountingDate", fromDate)
                .queryParam("toAccountingDate", toDate)
                .buildAndExpand(accountId)
                .toUriString();
        return url;
    }

}
