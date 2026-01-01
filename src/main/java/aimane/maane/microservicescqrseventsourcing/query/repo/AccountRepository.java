package aimane.maane.microservicescqrseventsourcing.query.repo;

import aimane.maane.microservicescqrseventsourcing.query.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
