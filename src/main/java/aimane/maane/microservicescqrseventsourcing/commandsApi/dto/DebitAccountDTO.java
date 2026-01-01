package aimane.maane.microservicescqrseventsourcing.commandsApi.dto;

public record DebitAccountDTO(
        String accountId,
        double amount,
        String currency

) {
}
