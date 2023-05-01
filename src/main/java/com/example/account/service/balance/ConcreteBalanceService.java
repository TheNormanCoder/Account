package com.example.account.service.balance;

import com.example.account.service.GenericAccountService;
import com.example.account.service.balance.response.BalanceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class ConcreteBalanceService extends GenericAccountService implements BalanceService {

    private static final Logger logger = LoggerFactory.getLogger(ConcreteBalanceService.class);
    public ConcreteBalanceService(RestTemplate restTemplate,
                                  @Value("${baseUrl}") String baseUrl,
                                  @Value("${balanceUrl}") String serviceUrl,
                                  @Value("${authSchema}") String authSchema,
                                  @Value("${apiKey}") String apiKey) {
        super(restTemplate, baseUrl,serviceUrl, authSchema, apiKey);
    }

    @Override
    public ResponseEntity<BalanceResponse> getBalance(String accountId) {
        ResponseEntity<BalanceResponse> response= null;
        String url = baseUrl + serviceUrl;
        url = url.replace("{accountId}", accountId);
        HttpHeaders headers = getHttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        try {
            logger.debug(entity.toString());
            response = restTemplate.exchange(url, HttpMethod.GET, entity, BalanceResponse.class);
            if(response.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.ok(response.getBody());
            }else {
                return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
            }
        }catch (Exception e){
            logger.error(entity.toString());
            return ResponseEntity.status(500)
                    .headers(headers)
                    .body(new BalanceResponse());
        }

    }

}
