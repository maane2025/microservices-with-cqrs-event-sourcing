package labrini.ouiam.microservicescqrseventsourcing.commands.aggregate;

import labrini.ouiam.microservicescqrseventsourcing.commandsApi.commands.CreateAccountCommand;
import labrini.ouiam.microservicescqrseventsourcing.commandsApi.enums.AccountStatus;
import labrini.ouiam.microservicescqrseventsourcing.commandsApi.events.AccountCreatedEvent;
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

    @CommandHandler
    public AccountAggregate(CreateAccountCommand command) {
        if(command.getInitialBalance() < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }
        AggregateLifecycle.apply(new AccountCreatedEvent(
                command.getId(),
                command.getCurrency(),
                command.getInitialBalance(),
                AccountStatus.CREATED
        ));
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        this.accountId = event.getId();
        this.currency = event.getCurrency();
        this.balance = event.getInitialBalance();
        this.status = event.getStatus();
    }
}
