package labrini.ouiam.microservicescqrseventsourcing.commands.controllers;

import labrini.ouiam.microservicescqrseventsourcing.commandsApi.commands.CreateAccountCommand;
import labrini.ouiam.microservicescqrseventsourcing.commandsApi.dto.CreateAccountDTO;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/commands/accounts")
@AllArgsConstructor
public class AccountCommandController {
    private CommandGateway commandGateway;

    @PostMapping("/create")
    public CompletableFuture<String> createAccount(@RequestBody CreateAccountDTO request) {
         CompletableFuture<String> result = commandGateway.send(new CreateAccountCommand(
                UUID.randomUUID().toString(),
                request.initialBalance(),
                request.currency()
        ));
        return result;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception ex) {
        return ResponseEntity.internalServerError().body(ex.getMessage());
    }
}
