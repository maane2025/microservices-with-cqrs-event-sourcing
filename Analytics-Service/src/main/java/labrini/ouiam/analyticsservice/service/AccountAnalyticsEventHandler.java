package labrini.ouiam.analyticsservice.service;

import labrini.ouiam.analyticsservice.entities.AccountAnalytics;
import labrini.ouiam.analyticsservice.queries.GetAllAccountAnalytics;
import labrini.ouiam.analyticsservice.queries.GetAllAccountAnalyticsByAccountId;
import labrini.ouiam.analyticsservice.repo.AccountAnalyticsRepository;
import labrini.ouiam.microservicescqrseventsourcing.commandsApi.events.AccountCreatedEvent;
import labrini.ouiam.microservicescqrseventsourcing.commandsApi.events.AccountCreditedEvent;
import labrini.ouiam.microservicescqrseventsourcing.commandsApi.events.AccountDebitedEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.LifecycleState;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.axonframework.queryhandling.QueryUpdateEmitter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service @Slf4j @AllArgsConstructor @Transactional
public class AccountAnalyticsEventHandler {
    private AccountAnalyticsRepository accountAnalyticsRepository;
    private QueryUpdateEmitter queryUpdateEmitter;

    @EventHandler
    public void on(AccountCreatedEvent event){
        log.info("============================================");
        log.info("AccountCreatedEvent received");
        AccountAnalytics accountAnalytics=AccountAnalytics.builder()
                .accountId(event.getId())
                .totalCredit(0)
                .totalDebit(0)
                .balance(event.getInitialBalance())
                .totalNumberOfCredits(0)
                .totalNumberOfDebits(0)
                .build();
        accountAnalyticsRepository.save(accountAnalytics);
    }

    @EventHandler
    public void on(AccountDebitedEvent event){
        log.info("============================================");
        log.info("AccountDebitedEvent received");
        AccountAnalytics accountAnalytics=accountAnalyticsRepository.findByAccountId(event.getId());
        accountAnalytics.setTotalDebit(accountAnalytics.getTotalDebit() + event.getAmount());
        accountAnalytics.setBalance(accountAnalytics.getBalance() - event.getAmount());
        accountAnalytics.setTotalNumberOfDebits(accountAnalytics.getTotalNumberOfDebits() + 1);
        accountAnalyticsRepository.save(accountAnalytics);
        queryUpdateEmitter.emit(GetAllAccountAnalyticsByAccountId.class,
                (query) -> query.getAccountId().equals(event.getId()),accountAnalytics );
    }

    @EventHandler
    public void on(AccountCreditedEvent event){
        log.info("============================================");
        log.info("AccountCreditedEvent received");
        AccountAnalytics accountAnalytics=accountAnalyticsRepository.findByAccountId(event.getId());
        accountAnalytics.setTotalCredit(accountAnalytics.getTotalCredit()+ event.getAmount());
        accountAnalytics.setBalance(accountAnalytics.getBalance() + event.getAmount());
        accountAnalytics.setTotalNumberOfCredits(accountAnalytics.getTotalNumberOfCredits() + 1);
        accountAnalyticsRepository.save(accountAnalytics);
        queryUpdateEmitter.emit(GetAllAccountAnalyticsByAccountId.class,
                (query) -> query.getAccountId().equals(event.getId()),accountAnalytics );
    }

    @QueryHandler
    public List<AccountAnalytics> on(GetAllAccountAnalytics event){
        log.info("============================================");
        log.info(" GetAllAccountAnalytics received");
        return accountAnalyticsRepository.findAll();
    }

    @QueryHandler
    public AccountAnalytics on(GetAllAccountAnalyticsByAccountId event){
        log.info("============================================");
        log.info(" GetAllAccountAnalyticsByAccountId received");
        return accountAnalyticsRepository.findByAccountId(event.getAccountId());
    }

}
