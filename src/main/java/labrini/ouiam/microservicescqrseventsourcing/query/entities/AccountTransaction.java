package labrini.ouiam.microservicescqrseventsourcing.query.entities;

import jakarta.persistence.*;
import labrini.ouiam.microservicescqrseventsourcing.commandsApi.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity @Data
@NoArgsConstructor @AllArgsConstructor @Builder
public class AccountTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double amount;
    private Instant instant;
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    @ManyToOne
    private Account account;
}
