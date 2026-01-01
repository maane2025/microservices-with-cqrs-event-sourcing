package aimane.maane.microservicescqrseventsourcing.query.repo;

import aimane.maane.microservicescqrseventsourcing.query.entities.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountTransactionRepository extends JpaRepository<AccountTransaction,Long> {
}
