package aimane.maane.microservicescqrseventsourcing.commands.aggregate;

import aimane.maane.microservicescqrseventsourcing.commandsApi.commands.CreateAccountCommand;
import aimane.maane.microservicescqrseventsourcing.commandsApi.commands.CreditAccountCommand;
import aimane.maane.microservicescqrseventsourcing.commandsApi.commands.DebitAccountCommand;
import aimane.maane.microservicescqrseventsourcing.commandsApi.enums.AccountStatus;
import aimane.maane.microservicescqrseventsourcing.commandsApi.events.AccountCreatedEvent;
import aimane.maane.microservicescqrseventsourcing.commandsApi.events.AccountCreditedEvent;
import aimane.maane.microservicescqrseventsourcing.commandsApi.events.AccountDebitedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class AccountAggregate {
    @AggregateIdentifier
    private String accountId;
    private double balance;
    private String currency;
    private AccountStatus status;

    public AccountAggregate() {
        // Default constructor required by Axon
    }

    // *********************** Command handler for CreateAccountCommand ************************//
    @CommandHandler
    public AccountAggregate(CreateAccountCommand command) {
        if(command.getInitialBalance() < 0) {
            throw new RuntimeException("Initial balance cannot be negative");
        }
        AggregateLifecycle.apply(new AccountCreatedEvent(
                command.getId(),
                command.getCurrency(),
                command.getInitialBalance(),
                AccountStatus.CREATED
        ));
    }

    // *********************** Event sourcing handler for AccountCreatedEvent ************************//
    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        this.accountId = event.getId();
        this.currency = event.getCurrency();
        this.balance = event.getInitialBalance();
        this.status = event.getStatus();
    }

    // *********************** Command handler for CreditAccountCommand ************************//
    @CommandHandler
    public void handle(CreditAccountCommand command) {
        if(command.getAmount() <= 0) {
            throw new RuntimeException("Credit amount must be positive");
        }
        AggregateLifecycle.apply(new AccountCreditedEvent(
                command.getId(),
                command.getAmount(),
                command.getCurrency()
        ));
    }

    // *********************** Event sourcing handler for AccountCreditedEvent ************************//
    @EventSourcingHandler
    public void on(AccountCreditedEvent event) {
        this.balance += event.getAmount();
    }

    // *********************** Command handler for DebitAccountCommand ************************//
    @CommandHandler
    public void handle(DebitAccountCommand command) {
        if(command.getAmount() > this.balance) {
            throw new RuntimeException("Insufficient balance");
        }
        AggregateLifecycle.apply(new AccountDebitedEvent(
                command.getId(),
                command.getAmount(),
                command.getCurrency()
        ));
    }

    // ********************** Event sourcing handler for AccountDebitedEvent ************************//
    @EventSourcingHandler
    public void on(AccountDebitedEvent event) {
        this.balance -= event.getAmount();
    }
}
