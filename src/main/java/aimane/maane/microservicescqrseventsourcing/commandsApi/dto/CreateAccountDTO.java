package aimane.maane.microservicescqrseventsourcing.commandsApi.dto;

public record CreateAccountDTO(
        String currency,
        double initialBalance
) {
}
