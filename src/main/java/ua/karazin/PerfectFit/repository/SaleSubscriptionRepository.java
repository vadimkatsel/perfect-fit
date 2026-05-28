package ua.karazin.PerfectFit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.karazin.PerfectFit.entity.SaleSubscription;

@Repository
public interface SaleSubscriptionRepository extends JpaRepository<SaleSubscription, Long> {


}
