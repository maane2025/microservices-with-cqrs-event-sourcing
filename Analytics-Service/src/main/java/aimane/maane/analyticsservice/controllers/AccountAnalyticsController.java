package aimane.maane.analyticsservice.controllers;

import org.springframework.http.MediaType;
import aimane.maane.analyticsservice.entities.AccountAnalytics;
import aimane.maane.analyticsservice.queries.GetAllAccountAnalytics;
import aimane.maane.analyticsservice.queries.GetAllAccountAnalyticsByAccountId;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.awt.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@AllArgsConstructor
@Slf4j
public class AccountAnalyticsController {
    private QueryGateway queryGateway;

    @GetMapping("/query/accountAnalytics")
    public CompletableFuture<List<AccountAnalytics>> accountAnalytics(){
        log.info("Received request for account analytics");
        return queryGateway.query(new GetAllAccountAnalytics(), ResponseTypes.multipleInstancesOf(AccountAnalytics.class));

    }

    @GetMapping("/query/accountAnalytics/{accountId}")
    public CompletableFuture<AccountAnalytics> getaccountAnalyticsById(@PathVariable String accountId){
        log.info(" Received request for account analytics for accountId: {}", accountId);
        return queryGateway.query(new GetAllAccountAnalyticsByAccountId(accountId), ResponseTypes.instanceOf(AccountAnalytics.class));
    }

    @GetMapping(value = "/query/accountAnalytics/{accountId}/watch",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<AccountAnalytics> watch(@PathVariable String accountId){
        SubscriptionQueryResult<AccountAnalytics, AccountAnalytics> queryResult =
                queryGateway.subscriptionQuery(
                        new GetAllAccountAnalyticsByAccountId(accountId),
                        AccountAnalytics.class,
                        AccountAnalytics.class
                );
        return queryResult.initialResult()
                .concatWith(queryResult.updates());
    }
}
