package labrini.ouiam.microservicescqrseventsourcing.commandsApi.dto;

public record CreditAccountDTO(
        String accountId,
        double amount,
        String currency

) {
}
