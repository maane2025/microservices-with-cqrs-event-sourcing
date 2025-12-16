package labrini.ouiam.microservicescqrseventsourcing.commandsApi.dto;

public record CreateAccountDTO(
        String currency,
        double initialBalance
) {
}
