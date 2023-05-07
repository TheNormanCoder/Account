package com.example.account;

import com.example.account.service.balance.repository.BalanceResponseRepository;
import com.example.account.service.balance.response.BalanceResponse;
import com.example.account.service.payment.repository.MoneyTransferRepository;
import com.example.account.service.payment.response.MoneyTransfer;
import com.example.account.service.transaction.repository.TransactionResponseRepository;
import com.example.account.service.transaction.response.Transaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.utility.DockerImageName;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class MyIntegrationTest {

    public static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.4.10"))
            .withExposedPorts(27017);

    @BeforeAll
    public static void setUp() {
        mongoDBContainer.start();
    }

    @DynamicPropertySource
    public static void mongoProperties(DynamicPropertyRegistry registry) {
        String uri = String.format("mongodb://%s:%d/testdb",
                mongoDBContainer.getContainerIpAddress(),
                mongoDBContainer.getMappedPort(27017));
        registry.add("spring.data.mongodb.uri", () -> uri);
    }

    @Autowired
    private BalanceResponseRepository balanceResponseRepository;

    @Autowired
    private MoneyTransferRepository moneyTransferRepository;

    @Autowired
    private TransactionResponseRepository transactionResponseRepository;

    @Test
    public void testBalanceResponseRepository() {
        // Preparazione del test: crea un oggetto BalanceResponse e salvalo nel database
        BalanceResponse balanceResponse = new BalanceResponse();
        balanceResponse.setDate("2023-05-02");
        balanceResponse.setBalance(new BigDecimal("1000.00"));
        balanceResponse.setAvailableBalance(new BigDecimal("900.00"));
        balanceResponse.setCurrency("EUR");
        BalanceResponse savedBalanceResponse = balanceResponseRepository.save(balanceResponse);

        // Esecuzione del test: cerca l'oggetto BalanceResponse salvato nel database utilizzando il suo ID
        BalanceResponse foundBalanceResponse = balanceResponseRepository.findById(savedBalanceResponse.getId()).orElse(null);

        // Verifica del risultato: controlla se l'oggetto BalanceResponse trovato corrisponde a quello salvato
        assertThat(foundBalanceResponse).isNotNull();
        assertThat(foundBalanceResponse).usingRecursiveComparison().isEqualTo(savedBalanceResponse);
    }

    @Test
    public void testMoneyTransferRepository() {
        // Preparazione del test: crea un oggetto MoneyTransfer e salvalo nel database
        MoneyTransfer moneyTransfer = new MoneyTransfer();
        moneyTransfer.setMoneyTransferId("1");
        moneyTransfer.setStatus("completed");
        moneyTransfer.setDirection("outgoing");
        // Impostare gli altri attributi di MoneyTransfer come desiderato
        System.out.println("Before saving: " + moneyTransfer);
        MoneyTransfer savedMoneyTransfer = moneyTransferRepository.save(moneyTransfer);
        System.out.println("After saving: " + savedMoneyTransfer);

        // Esecuzione del test: cerca l'oggetto MoneyTransfer salvato nel database utilizzando il suo ID
        MoneyTransfer foundMoneyTransfer = moneyTransferRepository.findByMoneyTransferId(savedMoneyTransfer.getMoneyTransferId());


        // Verifica del risultato: controlla se l'oggetto MoneyTransfer trovato corrisponde a quello salvato
        assertThat(foundMoneyTransfer).isNotNull();
        assertThat(foundMoneyTransfer).usingRecursiveComparison().isEqualTo(savedMoneyTransfer);
    }

    @Test
    public void testTransactionResponseRepository() {
        // Preparazione del test: crea un oggetto Transaction e salvalo nel database
        Transaction transaction = new Transaction();
        // Imposta gli attributi di Transaction come desiderato
        Transaction savedTransaction = transactionResponseRepository.save(transaction);

        // Esecuzione del test: cerca l'oggetto Transaction salvato nel database utilizzando il suo ID
        Transaction foundTransaction = transactionResponseRepository.findById(savedTransaction.getId()).orElse(null);

        // Verifica del risultato: controlla se l'oggetto Transaction trovato corrisponde a quello salvato
        assertThat(foundTransaction).isNotNull();
        assertThat(foundTransaction).usingRecursiveComparison().isEqualTo(savedTransaction);
    }

}
