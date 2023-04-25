package com.example.account.service.payment;

import com.example.account.service.GenericAccountService;
import com.example.account.service.payment.request.*;
import com.example.account.service.payment.response.MoneyTransfer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class ConcretePaymentService extends GenericAccountService implements PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(ConcretePaymentService.class);
    public ConcretePaymentService(RestTemplate restTemplate,
                                  @Value("${baseUrl}") String baseUrl,
                                  @Value("${paymentUrl}") String serviceUrl,
                                  @Value("${authSchema}") String authSchema,
                                  @Value("${apiKey}") String apiKey) {
        super(restTemplate, baseUrl,serviceUrl, authSchema, apiKey);
    }


    @Override
    public ResponseEntity<MoneyTransfer> executePayment(String accountId,Payment request) {
        ResponseEntity<MoneyTransfer> responseEntity = null;
        String url = baseUrl + serviceUrl;
        url = url.replace("{accountId}", accountId);
        HttpHeaders headers = getHttpHeaders();
        headers.set("X-Time-Zone", "Europe/Rome");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Payment> requestEntity = new HttpEntity<>(request, headers);
        try{
            logger.debug(requestEntity.toString());
            responseEntity = restTemplate.postForEntity(url, requestEntity, MoneyTransfer.class);
            if(responseEntity.getStatusCode().is2xxSuccessful()) {
                return ResponseEntity.ok(responseEntity.getBody());
            }else {
                return ResponseEntity.status(responseEntity.getStatusCode()).body(responseEntity.getBody());
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            logger.error(requestEntity.toString());
            return ResponseEntity.status(500)
                    .headers(headers)
                    .body(new MoneyTransfer());
        }
    }

    /*
    private Payment getPayment() {
        Payment payment = new Payment();
        payment.setExecutionDate("2019-04-01");
        payment.setUri("REMITTANCE_INFORMATION");
        payment.setDescription("Payment invoice 75/2017");
        payment.setAmount(800);
        payment.setCurrency("EUR");
        payment.setUrgent(false);
        payment.setInstant(false);
        payment.setFeeType("SHA");
        payment.setFeeAccountId("45685475");
        Creditor creditor = new Creditor();
        creditor.setName("John Doe");
        Account account = new Account();
        account.setAccountCode("IT23A0336844430152923804660");
        account.setBicCode("SELBIT2BXXX");
        creditor.setAccount(account);
        Address address = new Address();
        address.setAddress(null);
        address.setCity(null);
        address.setCountryCode(null);
        creditor.setAddress(address);
        payment.setCreditor(creditor);
        TaxRelief taxRelief = new TaxRelief();
        taxRelief.setTaxReliefId("L449");
        taxRelief.setCondoUpgrade(false);
        taxRelief.setCreditorFiscalCode("56258745832");
        taxRelief.setBeneficiaryType("NATURAL_PERSON");
        NaturalPersonBeneficiary naturalPersonBeneficiary = new NaturalPersonBeneficiary();
        naturalPersonBeneficiary.setFiscalCode1("MRLFNC81L04A859L");
        taxRelief.setNaturalPersonBeneficiary(naturalPersonBeneficiary);
        LegalPersonBeneficiary legalPersonBeneficiary = new LegalPersonBeneficiary();
        legalPersonBeneficiary.setFiscalCode(null);
        legalPersonBeneficiary.setLegalRepresentativeFiscalCode(null);
        taxRelief.setLegalPersonBeneficiary(legalPersonBeneficiary);
        payment.setTaxRelief(taxRelief);
        return payment;
    }*/

}
