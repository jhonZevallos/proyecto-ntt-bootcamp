package com.nttdata.transaction.service.impl;

import com.nttdata.transaction.adapter.AccountAdapter;
import com.nttdata.transaction.adapter.CreditAdapter;
import com.nttdata.transaction.adapter.CreditCardAdapter;
import com.nttdata.transaction.adapter.CustomerAdapter;
import com.nttdata.transaction.client.model.*;
import com.nttdata.transaction.server.account.model.Account;
import com.nttdata.transaction.server.account.model.AccountRequest;
import com.nttdata.transaction.server.credit.model.Credit;
import com.nttdata.transaction.server.creditcard.model.CreditCard;
import com.nttdata.transaction.server.customer.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class TransactionServiceImplTest {

    @Mock
    private CustomerAdapter customerAdapter;

    @Mock
    private AccountAdapter accountAdapter;

    @Mock
    private CreditCardAdapter creditCardAdapter;

    @Mock
    private CreditAdapter creditAdapter;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    Customer customer = new Customer();
    Account account = new Account();
    CreditCard creditCard = new CreditCard();
    Credit credit = new Credit();
    AccountRequest accountRequest = new AccountRequest();
    TransferRequest transferRequest = new TransferRequest();
    Product product = new Product();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        customer.setId("customer123");
        customer.setName("John Doe");
        customer.setType(Customer.TypeEnum.PERSONAL);

        account.setId("account123");
        account.setTypeAccount(Account.TypeAccountEnum.AHORRO);
        account.setBalance(new BigDecimal("1000.00"));

        creditCard.setId("creditCard123");
        creditCard.setCardNumber("1234-5678-9012-3456");
        creditCard.setCurrentBalance(new BigDecimal("500.00"));

        credit.setId("credit123");
        credit.setTypeCredit(Credit.TypeCreditEnum.PERSONAL);
        credit.setAmount(new BigDecimal("2000.00"));

        accountRequest.setHolder("customer123");
        accountRequest.setTypeAccount(AccountRequest.TypeAccountEnum.AHORRO);

        List<ProductAccount> accounts = List.of(new ProductAccount());
        List<ProductCredit> credits = List.of(new ProductCredit());
        List<ProductCreditcard> creditCards = List.of(new ProductCreditcard());
        product.setType("personal");
        product.setName("John Doe");
        product.setAccount(accounts);
        product.setCredit(credits);
        product.setCreditcard(creditCards);

        transferRequest.setSourceAccount("sourceAccount123");
        transferRequest.setDestinationAccount("destinationAccount123");
        transferRequest.setAmount(new BigDecimal("100.00"));
    }

    @Test
    public void testListProduct() {

        when(customerAdapter.getCustomerById(anyString()))
                .thenReturn(Mono.just(customer));
        when(accountAdapter.getAccountByIdHolder(anyString()))
                .thenReturn(Mono.just(List.of(account)));
        when(creditCardAdapter.getCreditCardByCustomerId(anyString()))
                .thenReturn(Mono.just(List.of(creditCard)));
        when(creditAdapter.getCreditByCustomerId(anyString()))
                .thenReturn(Mono.just(List.of(credit)));

        Mono<Product> result = transactionService.listProduct("customer123");

        StepVerifier.create(result)
                .expectNextMatches(product -> product.getName().equals("John Doe")
                        && product.getAccount().size() == 1
                        && product.getCredit().size() == 1
                        && product.getCreditcard().size() == 1)
                .verifyComplete();
    }

    @Test
    public void testTransferBetweenAccounts() {
        Account sourceAccount = new Account();
        sourceAccount.setId("sourceAccount123");
        sourceAccount.setBalance(new BigDecimal("500.00"));

        Account destinationAccount = new Account();
        destinationAccount.setId("destinationAccount123");
        destinationAccount.setBalance(new BigDecimal("200.00"));

        when(accountAdapter.getAccountByAccountNumber("sourceAccount123"))
                .thenReturn(Mono.just(sourceAccount));
        when(accountAdapter.getAccountByAccountNumber("destinationAccount123"))
                .thenReturn(Mono.just(destinationAccount));
        when(accountAdapter.updateAccount(any(Account.class)))
                .thenReturn(Mono.just(sourceAccount))
                .thenReturn(Mono.just(destinationAccount));

        Mono<TransferResponse> result = transactionService.transferBetweenAccounts(transferRequest);

        StepVerifier.create(result)
                .expectNextMatches(response -> response
                        .getMensaje().equals("Transferencia exitosa de: 100.00 " +
                                "a cuenta: destinationAccount123"))
                .verifyComplete();
    }

    @Test
    public void testTransferBetweenAccounts_error() {
        Account sourceAccount = new Account();
        sourceAccount.setId("sourceAccount123");
        sourceAccount.setBalance(new BigDecimal("50.00"));

        Account destinationAccount = new Account();
        destinationAccount.setId("destinationAccount123");
        destinationAccount.setBalance(new BigDecimal("200.00"));

        when(accountAdapter.getAccountByAccountNumber("sourceAccount123"))
                .thenReturn(Mono.just(sourceAccount));
        when(accountAdapter.getAccountByAccountNumber("destinationAccount123"))
                .thenReturn(Mono.just(destinationAccount));
        when(accountAdapter.updateAccount(any(Account.class)))
                .thenReturn(Mono.just(sourceAccount))
                .thenReturn(Mono.just(destinationAccount));

        Mono<TransferResponse> result = transactionService.transferBetweenAccounts(transferRequest);

        StepVerifier.create(result)
                .expectError(RuntimeException.class)
                .verify();
    }
}