package com.example.account.service.balance;

import com.example.account.service.GenericAccountService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class ConcreteBalanceService extends GenericAccountService implements BalanceService {


    public ConcreteBalanceService(RestTemplate restTemplate,
                                  @Value("${baseUrl}") String baseUrl,
                                  @Value("${balanceUrl}") String serviceUrl,
                                  @Value("${authSchema}") String authSchema,
                                  @Value("${apiKey}") String apiKey) {
        super(restTemplate, baseUrl,serviceUrl, authSchema, apiKey);
    }

    @Override
    public ResponseEntity<BalanceResponse> getBalance(String accountId) {
        String url = baseUrl + serviceUrl;
        url = url.replace("{accountId}", accountId);
        HttpHeaders headers = getHttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<BalanceResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, BalanceResponse.class);
        if(response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity.ok(response.getBody());
        }else {
            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
        }
    }

}
