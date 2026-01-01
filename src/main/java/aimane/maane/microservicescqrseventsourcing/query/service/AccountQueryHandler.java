package aimane.maane.microservicescqrseventsourcing.query.service;

import aimane.maane.microservicescqrseventsourcing.query.entities.Account;
import aimane.maane.microservicescqrseventsourcing.query.entities.AccountTransaction;
import aimane.maane.microservicescqrseventsourcing.query.queries.GetAllAccountTransaction;
import aimane.maane.microservicescqrseventsourcing.query.queries.GetAllAccounts;
import aimane.maane.microservicescqrseventsourcing.query.repo.AccountRepository;
import aimane.maane.microservicescqrseventsourcing.query.repo.AccountTransactionRepository;
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
