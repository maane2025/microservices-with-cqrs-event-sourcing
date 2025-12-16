package labrini.ouiam.microservicescqrseventsourcing.commandsApi.dto;

public record DebitAccountDTO(
        String accountId,
        double amount,
        String currency

) {
}
