package aimane.maane.microservicescqrseventsourcing.query.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import aimane.maane.microservicescqrseventsourcing.commandsApi.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Account {
    @Id
    private String id;
    private double balance;
    private String currency;
    private AccountStatus status;
    private Instant createdAt;
    @OneToMany(mappedBy = "account")
    private List<AccountTransaction> accountTransactions;
}
