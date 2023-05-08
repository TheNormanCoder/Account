package com.example.account;

import com.example.account.service.balance.response.BalanceResponse;
import com.example.account.service.balance.BalanceService;
import com.example.account.service.payment.PaymentService;
import com.example.account.service.payment.request.Payment;
import com.example.account.service.payment.response.MoneyTransfer;
import com.example.account.service.transaction.response.TransactionResponse;
import com.example.account.service.transaction.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@RestController
@RequestMapping("/api")
public class AccountController {

    private final BalanceService balanceService;
    private final TransactionService transactionService;
    private final PaymentService paymentService;

    @Autowired
    public AccountController(BalanceService balanceService, TransactionService transactionService, PaymentService paymentService) {
        this.balanceService = balanceService;
        this.transactionService = transactionService;
        this.paymentService = paymentService;
    }
    @Operation(summary = "Get balance", description = "Get the balance for a given account ID.")
    @GetMapping(value = "/balance/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200", description = "Successful operation", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = BalanceResponse.class))
    })
    public ResponseEntity<BalanceResponse> getBalance(@PathVariable(value = "accountId") @Pattern(regexp = "^[0-9]{8,10}$") String accountId)
    {
        ResponseEntity<BalanceResponse> output = balanceService.getBalance(accountId);
        return output;
    }

    @Operation(summary = "Transfer money", description = "Transfer money from one account to another.")
    @PostMapping(value = "/moneyTransfer/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200", description = "Successful operation", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = MoneyTransfer.class))
    })
    public ResponseEntity<MoneyTransfer> moneyTransfer(@PathVariable(value = "accountId") @Pattern(regexp = "^[0-9]{8,10}$") String accountId,
                                                       @Valid @RequestBody Payment request)
    {
        ResponseEntity<MoneyTransfer> output = paymentService.executePayment(accountId,request);
        return output;
    }

    @Operation(summary = "Get transactions", description = "Get a list of transactions for a given account ID within a date range.")
    @GetMapping(value = "/transactions/{accountId}/{fromAccountingDate}/{toAccountingDate}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponse(responseCode = "200", description = "Successful operation", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionResponse.class))
    })
    public ResponseEntity<TransactionResponse> getTransactions(@PathVariable(value = "accountId") @Pattern(regexp = "^[0-9]{8,10}$") String accountId,
                                                               @PathVariable(value = "fromAccountingDate")  @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}")String fromAccountingDate,
                                                               @PathVariable(value = "toAccountingDate") @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}") String toAccountingDate)
    {
        if (!isValidDate(fromAccountingDate) || !isValidDate(toAccountingDate)) {
            return ResponseEntity.badRequest().build();
        }
        ResponseEntity<TransactionResponse> output = transactionService.getTransactions(accountId,fromAccountingDate,toAccountingDate);
        return output;
    }


    public static boolean isValidDate(String dateStr) {
        try {
            LocalDate.parse(dateStr);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

}

