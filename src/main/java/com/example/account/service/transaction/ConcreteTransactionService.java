package com.example.account.service.transaction;

import com.example.account.service.GenericAccountService;
import com.example.account.service.transaction.repository.TransactionResponseRepository;
import com.example.account.service.transaction.response.TransactionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ConcreteTransactionService extends GenericAccountService implements TransactionService {

    private static final Logger logger = LoggerFactory.getLogger(ConcreteTransactionService.class);

    private TransactionResponseRepository transactionResponseRepository;
    public ConcreteTransactionService(RestTemplate restTemplate,
                                      @Value("${baseUrl}") String baseUrl,
                                      @Value("${transactionUrl}") String serviceUrl,
                                      @Value("${authSchema}") String authSchema,
                                      @Value("${apiKey}") String apiKey) {
        super(restTemplate, baseUrl,serviceUrl, authSchema, apiKey);
    }

    @Autowired
    public ConcreteTransactionService(RestTemplate restTemplate, @Value("${baseUrl}") String baseUrl, @Value("${transactionUrl}") String serviceUrl, @Value("${authSchema}") String authSchema, @Value("${apiKey}") String apiKey, TransactionResponseRepository transactionResponseRepository) {
        super(restTemplate, baseUrl,serviceUrl, authSchema, apiKey);
        this.transactionResponseRepository = transactionResponseRepository;
    }

    @Override
    public ResponseEntity<TransactionResponse> getTransactions(String accountId, String fromDate, String toDate) {
        ResponseEntity<TransactionResponse> response=null;
        String url = getUrl(accountId, fromDate, toDate);
        HttpHeaders headers = getHttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);
        try {
            logger.debug(entity.toString());
            response = restTemplate.exchange(url, HttpMethod.GET, entity, TransactionResponse.class);
            if(response.getStatusCode().is2xxSuccessful()) {
                TransactionResponse transactionResponse = response.getBody();
                transactionResponseRepository.saveAll(transactionResponse.getList());
                return ResponseEntity.ok(response.getBody());
            }else {
                return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
            }
        }catch (Exception e){
            logger.error(entity.toString());
            if (response != null) {
                logger.error(response.toString());
                return ResponseEntity.status(response.getStatusCode())
                        .headers(headers)
                        .body(new TransactionResponse());
            } else {
                logger.error("Response is null");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .headers(headers)
                        .body(new TransactionResponse());
            }
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
