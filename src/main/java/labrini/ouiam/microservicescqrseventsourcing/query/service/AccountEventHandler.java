package labrini.ouiam.microservicescqrseventsourcing.query.service;

import labrini.ouiam.microservicescqrseventsourcing.commandsApi.events.AccountCreatedEvent;
import labrini.ouiam.microservicescqrseventsourcing.query.entities.Account;
import labrini.ouiam.microservicescqrseventsourcing.query.repo.AccountRepository;
import labrini.ouiam.microservicescqrseventsourcing.query.repo.AccountTransactionRepository;
import lombok.AllArgsConstructor;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.EventMessage;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountEventHandler {
    private AccountRepository accountRepository;
    private AccountTransactionRepository accountTransactionRepository;

    //***** Handle AccountCreatedEvent *****//
    @EventHandler
    public void on(AccountCreatedEvent event, EventMessage<AccountCreatedEvent> eventEventMessage){
        Account account=Account.builder()
                .id(event.getId())
                .currency(event.getCurrency())
                .balance(event.getInitialBalance())
                .status(event.getStatus())
                .createdAt(eventEventMessage.getTimestamp())
                .build();
        accountRepository.save(account);
    }
}
