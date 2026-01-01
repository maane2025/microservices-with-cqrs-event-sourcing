package aimane.maane.microservicescqrseventsourcing.commandsApi.events;

import aimane.maane.microservicescqrseventsourcing.commandsApi.enums.AccountStatus;
import lombok.Getter;

public class AccountCreatedEvent extends BaseEvent<String> {

    @Getter
    private String currency;
    @Getter
    private double initialBalance;
    @Getter
    private AccountStatus status;

    public AccountCreatedEvent(String id, String currency, double initialBalance ,AccountStatus status) {
        super(id);
        this.initialBalance = initialBalance;
        this.currency = currency;
        this.status = status;
    }
}
