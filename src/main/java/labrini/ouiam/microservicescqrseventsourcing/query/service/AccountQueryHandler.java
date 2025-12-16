package labrini.ouiam.microservicescqrseventsourcing.query.service;

import labrini.ouiam.microservicescqrseventsourcing.query.entities.Account;
import labrini.ouiam.microservicescqrseventsourcing.query.entities.AccountTransaction;
import labrini.ouiam.microservicescqrseventsourcing.query.queries.GetAllAccountTransaction;
import labrini.ouiam.microservicescqrseventsourcing.query.queries.GetAllAccounts;
import labrini.ouiam.microservicescqrseventsourcing.query.repo.AccountRepository;
import labrini.ouiam.microservicescqrseventsourcing.query.repo.AccountTransactionRepository;
import lombok.AllArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AccountQueryHandler {
    private AccountRepository accountRepository;
    private AccountTransactionRepository accountTransactionRepository;

    //***** Handle GetAllAccounts Query *****//
    @QueryHandler
    public List<Account> on(GetAllAccounts query) {
        return accountRepository.findAll();
    }

    @QueryHandler
    public List<AccountTransaction> on(GetAllAccountTransaction query) {
        return accountTransactionRepository.findAll();
    }
}
