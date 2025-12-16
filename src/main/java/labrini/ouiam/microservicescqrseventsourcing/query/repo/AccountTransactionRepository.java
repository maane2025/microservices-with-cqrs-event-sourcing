package labrini.ouiam.microservicescqrseventsourcing.query.repo;

import labrini.ouiam.microservicescqrseventsourcing.query.entities.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountTransactionRepository extends JpaRepository<AccountTransaction,Long> {
}
