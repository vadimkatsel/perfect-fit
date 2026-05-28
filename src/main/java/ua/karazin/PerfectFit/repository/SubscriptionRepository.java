package ua.karazin.PerfectFit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.karazin.PerfectFit.entity.Client;
import ua.karazin.PerfectFit.entity.SaleSubscription;
import ua.karazin.PerfectFit.entity.Subscription;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {


    @Query("SELECT ss FROM Subscription s " +
    "JOIN SaleSubscription ss ON ss.subscription = s " +
    "JOIN client c ON c = ss.client " +
    "WHERE (GETDATE() BETWEEN ss.beginSubscription AND ss.deadlineSubscription) " +
    "AND c = :clP")
    public SaleSubscription getSubscriptionForClient(@Param("clP")Client client);



}

