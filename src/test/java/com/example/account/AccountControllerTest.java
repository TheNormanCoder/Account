package com.example.account;

import com.example.account.service.balance.response.BalanceResponse;
import com.example.account.service.payment.request.Payment;
import com.example.account.service.payment.response.MoneyTransfer;
import com.example.account.service.transaction.response.TransactionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountController accountController;

    private BalanceResponse balanceResponse;
    private MoneyTransfer moneyTransfer;
    private TransactionResponse transactionResponse;

    @BeforeEach
    public void setUp() {
        balanceResponse = new BalanceResponse();
        balanceResponse.setDate("2023-05-02");
        balanceResponse.setBalance(new BigDecimal(1000));
        balanceResponse.setAvailableBalance(new BigDecimal(900));
        balanceResponse.setCurrency("EUR");

        moneyTransfer = new MoneyTransfer();
        moneyTransfer.setSender("12345678");
        moneyTransfer.setRecipient("23456789");
        moneyTransfer.setAmount(new BigDecimal(100));
        moneyTransfer.setCurrency("EUR");

        transactionResponse = new TransactionResponse();
        transactionResponse.setAccountId("12345678");
        transactionResponse.setFromAccountingDate("2023-04-01");
        transactionResponse.setToAccountingDate("2023-04-30");
    }

    @Test
    public void getBalanceTest() throws Exception {
        when(accountController.getBalance("12345678")).thenReturn(ResponseEntity.ok(balanceResponse));
        mockMvc.perform(get("/api/balance/12345678"))
                .andExpect(status().isOk());
    }

    @Test
    public void moneyTransferTest() throws Exception {
        Payment payment = new Payment();
        payment.setRecipient("23456789");
        payment.setAmount(new BigDecimal(100));

        when(accountController.moneyTransfer("12345678", payment)).thenReturn(ResponseEntity.ok(moneyTransfer));
        mockMvc.perform(post("/api/moneyTransfer/12345678"))
                .andExpect(status().isOk());
    }

    @Test
    public void getTransactionsTest() throws Exception {
        when(accountController.getTransactions("12345678", "2023-04-01", "2023-04-30")).thenReturn(ResponseEntity.ok(transactionResponse));
        mockMvc.perform(get("/api/transactions/12345678/2023-04-01/2023-04-30"))
                .andExpect(status().isOk());
    }
}
