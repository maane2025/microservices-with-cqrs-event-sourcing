package labrini.ouiam.analyticsservice.repo;

import labrini.ouiam.analyticsservice.entities.AccountAnalytics;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountAnalyticsRepository extends JpaRepository<AccountAnalytics, Long> {
    AccountAnalytics findByAccountId(String accountId);
}
