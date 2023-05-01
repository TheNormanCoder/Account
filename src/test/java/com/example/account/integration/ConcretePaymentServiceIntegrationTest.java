package com.example.account.integration;

import com.example.account.service.payment.request.*;
import com.example.account.service.payment.response.MoneyTransfer;
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

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
@Disabled
class ConcretePaymentServiceIntegrationTest {


    @Autowired
    private TestRestTemplate restTemplate;

    private HttpHeaders headers;

    private String baseUrl;

    private String paymentUrl;

    private String accountId;

    @BeforeEach
    public void setup() {
        headers = new HttpHeaders();
        headers.add("Auth-Schema", "S2S");
        headers.add("Api-Key", "FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP");
        headers.set("X-Time-Zone", "Europe/Rome");
        baseUrl = "https://sandbox.platfr.io";
        accountId = "14537780";
        paymentUrl = "/api/gbs/banking/v4.0/accounts/{accountId}/payments/money-transfers";
    }
    @Test
    void testExecutePayment() {
        String url = baseUrl + paymentUrl;
        url = url.replace("{accountId}", accountId);
        headers.setContentType(MediaType.APPLICATION_JSON);
        Payment payment = getPayment();
        HttpEntity<Payment> requestEntity = new HttpEntity<>(payment, headers);
        ResponseEntity<MoneyTransfer> responseEntity = restTemplate.postForEntity(url, requestEntity, MoneyTransfer.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    private Payment getPayment() {
        Payment payment = new Payment();
        //payment.setExecutionDate("2019-04-01");
        //payment.setUri("REMITTANCE_INFORMATION");
        payment.setDescription("Payment invoice 75/2017");
        payment.setAmount(BigDecimal.valueOf(800));
        payment.setCurrency("EUR");
        //payment.setUrgent(false);
        //payment.setInstant(false);
        //payment.setFeeType("SHA");
        //payment.setFeeAccountId("45685475");
        Creditor creditor = new Creditor();
        creditor.setName("Norman William Billanova");
        Account account = new Account();
        account.setAccountCode("IT18C0347501605CC0010685824");
        //account.setBicCode("SELBIT2BXXX");
        creditor.setAccount(account);
        //Address address = new Address();
        //address.setAddress(null);
        //address.setCity(null);
        //address.setCountryCode(null);
        //creditor.setAddress(address);
        payment.setCreditor(creditor);
        //TaxRelief taxRelief = new TaxRelief();
        //taxRelief.setTaxReliefId("L449");
        //taxRelief.setCondoUpgrade(false);
        //taxRelief.setCreditorFiscalCode("FNZLRT80A01F839Z");
        //taxRelief.setBeneficiaryType("NATURAL_PERSON");
        //NaturalPersonBeneficiary naturalPersonBeneficiary = new NaturalPersonBeneficiary();
        //naturalPersonBeneficiary.setFiscalCode1("FNZLRT80A01F839Z");
        //taxRelief.setNaturalPersonBeneficiary(naturalPersonBeneficiary);
        //LegalPersonBeneficiary legalPersonBeneficiary = new LegalPersonBeneficiary();
        //legalPersonBeneficiary.setFiscalCode(null);
        //legalPersonBeneficiary.setLegalRepresentativeFiscalCode(null);
        //taxRelief.setLegalPersonBeneficiary(legalPersonBeneficiary);
        //payment.setTaxRelief(taxRelief);
        return payment;
    }

}
