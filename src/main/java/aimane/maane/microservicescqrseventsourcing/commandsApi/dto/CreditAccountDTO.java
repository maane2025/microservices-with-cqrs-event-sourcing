package aimane.maane.microservicescqrseventsourcing.commandsApi.dto;

public record CreditAccountDTO(
        String accountId,
        double amount,
        String currency

) {
}
