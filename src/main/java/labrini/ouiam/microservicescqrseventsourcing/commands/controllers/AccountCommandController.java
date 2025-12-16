package labrini.ouiam.microservicescqrseventsourcing.commands.controllers;

import labrini.ouiam.microservicescqrseventsourcing.commandsApi.commands.CreateAccountCommand;
import labrini.ouiam.microservicescqrseventsourcing.commandsApi.commands.CreditAccountCommand;
import labrini.ouiam.microservicescqrseventsourcing.commandsApi.dto.CreateAccountDTO;
import labrini.ouiam.microservicescqrseventsourcing.commandsApi.dto.CreditAccountDTO;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/commands/accounts")
@AllArgsConstructor
public class AccountCommandController {
    private CommandGateway commandGateway;
    private EventStore evenStrore;

    @PostMapping("/create")
    public CompletableFuture<String> createAccount(@RequestBody CreateAccountDTO request) {
         CompletableFuture<String> result = commandGateway.send(new CreateAccountCommand(
                UUID.randomUUID().toString(),
                request.initialBalance(),
                request.currency()
        ));
        return result;
    }

    @PostMapping("/credit")
        public CompletableFuture<String> creditAccount(@RequestBody CreditAccountDTO request) {
        CompletableFuture<String> result = commandGateway.send(new CreditAccountCommand(
                request.accountId(),
                request.amount(),
                request.currency()
        ));
        return result;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception ex) {
        return ResponseEntity.internalServerError().body(ex.getMessage());
    }

    @GetMapping("/eventStore/{accountId}")
    public Stream getEventStore(@PathVariable String accountId) {
        return evenStrore.readEvents(accountId).asStream();
    }
}
