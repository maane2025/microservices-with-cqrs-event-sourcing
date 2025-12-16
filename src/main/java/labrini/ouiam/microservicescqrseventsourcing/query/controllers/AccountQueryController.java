package labrini.ouiam.microservicescqrseventsourcing.query.controllers;

import labrini.ouiam.microservicescqrseventsourcing.query.entities.Account;
import labrini.ouiam.microservicescqrseventsourcing.query.entities.AccountTransaction;
import labrini.ouiam.microservicescqrseventsourcing.query.queries.GetAllAccountTransaction;
import labrini.ouiam.microservicescqrseventsourcing.query.queries.GetAllAccounts;
import lombok.AllArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/queries/accounts")
@AllArgsConstructor
public class AccountQueryController {
    private QueryGateway queryGateway;

    @GetMapping("/list")
    public CompletableFuture<List<Account>> accountList() {
        CompletableFuture<List<Account>> accounts = queryGateway.query(new GetAllAccounts(), ResponseTypes.multipleInstancesOf(Account.class));
        return accounts;
    }

    @GetMapping("/accountTransactions")
    public CompletableFuture<List<AccountTransaction>> accountTransactions() {
        CompletableFuture<List<AccountTransaction>> accountTransactions = queryGateway.query(new GetAllAccountTransaction(), ResponseTypes.multipleInstancesOf(AccountTransaction.class));
        return accountTransactions;
    }
}
