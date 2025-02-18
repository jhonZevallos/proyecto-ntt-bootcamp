package com.nttdata.transaction.service.impl;

import com.nttdata.transaction.adapter.AccountAdapter;
import com.nttdata.transaction.adapter.CreditAdapter;
import com.nttdata.transaction.adapter.CreditCardAdapter;
import com.nttdata.transaction.adapter.CustomerAdapter;
import com.nttdata.transaction.client.model.*;
import com.nttdata.transaction.mapper.TransactionMapper;
import com.nttdata.transaction.server.account.model.Account;
import com.nttdata.transaction.server.account.model.AccountRequest;
import com.nttdata.transaction.server.credit.model.Credit;
import com.nttdata.transaction.server.creditcard.model.CreditCard;
import com.nttdata.transaction.server.customer.model.Customer;
import com.nttdata.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private CustomerAdapter customerAdapter;

    @Autowired
    private AccountAdapter accountAdapter;

    @Autowired
    private CreditCardAdapter creditCardAdapter;

    @Autowired
    private CreditAdapter creditAdapter;

    @Override
    public Mono<Product> listProduct(String idClient) {
        Mono<Customer> customerDto = customerAdapter.getCustomerById(idClient);
        Mono<List<Account>> listAccount = customerDto.flatMap(cstm ->
                accountAdapter.getAccountByIdHolder(cstm.getId()));
        Mono<List<CreditCard>> listCreditCard = customerDto.flatMap(crdt ->
                creditCardAdapter.getCreditCardByCustomerId(crdt.getId()));
        Mono<List<Credit>> listCredit = customerDto.flatMap(card ->
                creditAdapter.getCreditByCustomerId(card.getId()));

        return Mono.zip(customerDto,listAccount,listCredit,listCreditCard)
                .map(tuple -> {
            Customer customer = tuple.getT1();
            List<ProductAccount> accounts = tuple.getT2().stream()
                    .map(x-> {
                        ProductAccount productAccount = new ProductAccount();
                        productAccount.setId(x.getId());
                        productAccount.setType(x.getTypeAccount().getValue());
                        productAccount.setBalance(x.getBalance());
                        return productAccount;
                    }).toList();
            List<ProductCredit> credits = tuple.getT3().stream()
                    .map(x-> {
                        ProductCredit productCredit = new ProductCredit();
                        productCredit.setId(x.getId());
                        productCredit.setType(x.getTypeCredit().getValue());
                        productCredit.setAmount(x.getAmount());
                        return productCredit;
                    }).toList();
            List<ProductCreditcard> creditcards = tuple.getT4().stream()
                    .map(x-> {
                        ProductCreditcard productCreditcard = new ProductCreditcard();
                        productCreditcard.setId(x.getId());
                        productCreditcard.setCardnumber(x.getCardNumber());
                        productCreditcard.setBalance(x.getCurrentBalance());
                        return  productCreditcard;
                    }).toList();

            Product product = new Product();
            product.setType(customer.getType().getValue());
            product.setName(customer.getName());
            product.setAccount(accounts);
            product.setCredit(credits);
            product.setCreditcard(creditcards);
            return product;
        });
    }

    @Override
    public Mono<Account> createAccount(AccountRequest request) {
        return customerAdapter.getCustomerById(request.getHolder())
                .flatMap(customer -> {
                    String customerType = customer.getType().getValue().toLowerCase();
                    String accountType = request.getTypeAccount().getValue().toLowerCase();

                    Mono<Boolean> validationMono;

                    switch (customerType) {
                        case "personal":
                            validationMono = validateAccountPersonal(request.getHolder(), accountType);
                            break;
                        case "empresarial":
                            validationMono = validateAccountEmpresarial(accountType);
                            break;
                        default:
                            return Mono.error(new RuntimeException("Tipo de cliente no reconocido."));
                    }

                    return validationMono
                            .flatMap(isInvalid -> {
                                if (isInvalid) {
                                    return Mono.error(new RuntimeException(
                                            "El cliente " + (customerType.equals("personal") ? "ya tiene" : "no puede tener") +
                                                    " una cuenta: " + request.getTypeAccount()));
                                }

                                return accountAdapter.createAccount(request);
                            });
                });
    }

    private Mono<Boolean> validateAccountPersonal(String idCliente,String typeAccount) {
        return this.listProduct(idCliente)
                .map(product -> {
                    List<ProductAccount> accounts = product.getAccount();
                    if(accounts != null && !accounts.isEmpty()) {
                        return accounts.stream()
                                .anyMatch(x-> x.getType().equalsIgnoreCase(typeAccount));
                    }
                    return false;
                });
    }

    private Mono<Boolean> validateAccountEmpresarial(String typeAccount) {
        return Mono.just(typeAccount)
                .map(type -> "ahorro".equalsIgnoreCase(type) ||
                        "plazo_fijo".equalsIgnoreCase(type));
    }
}
