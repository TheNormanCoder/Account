package com.example.account;

import com.example.account.service.balance.BalanceService;
import com.example.account.service.balance.response.BalanceResponse;
import com.example.account.service.payment.PaymentService;
import com.example.account.service.payment.request.Payment;
import com.example.account.service.payment.response.MoneyTransfer;
import com.example.account.service.transaction.TransactionService;
import com.example.account.service.transaction.response.TransactionResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private AccountController accountController;

    @Mock
    private BalanceService balanceService;

    @Mock
    private TransactionService transactionService;

    @Mock
    private PaymentService paymentService;

    @Test
    public void testGetBalance() throws Exception {
        // Arrange
        String accountId = "12345678";
        BalanceResponse balanceResponse = new BalanceResponse(accountId, 500.0);
        when(balanceService.getBalance(accountId)).thenReturn(ResponseEntity.ok(balanceResponse));

        // Act & Assert
        mockMvc.perform(get("/api/balance/{accountId}", accountId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testMoneyTransfer() throws Exception {
        // Arrange
        String accountId = "12345678";
        Payment payment = new Payment("87654321", 100.0);
        MoneyTransfer moneyTransfer = new MoneyTransfer(accountId, "87654321", 100.0);
        when(paymentService.executePayment(accountId, payment)).thenReturn(ResponseEntity.ok(moneyTransfer));

        // Act & Assert
        mockMvc.perform(post("/api/moneyTransfer/{accountId}", accountId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"recipient\":\"87654321\",\"amount\":100.0}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetTransactions() throws Exception {
        // Arrange
        String accountId = "12345678";
        String fromDate = LocalDate.now().minusDays(10).toString();
        String toDate = LocalDate.now().toString();
        TransactionResponse transactionResponse = new TransactionResponse(accountId, fromDate, toDate);
        when(transactionService.getTransactions(accountId, fromDate, toDate)).thenReturn(ResponseEntity.ok(transactionResponse));

        // Act & Assert
        mockMvc.perform(get("/api/transactions/{accountId}/{fromAccountingDate}/{toAccountingDate}", accountId, fromDate, toDate)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
