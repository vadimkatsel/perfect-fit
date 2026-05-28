package ua.karazin.PerfectFit.entity;


import jakarta.persistence.*;
import org.springframework.stereotype.Component;
import ua.karazin.PerfectFit.constants.tables.SaleSubscriptionConstants;

import java.time.LocalDate;

@Entity
@Component
@Table(name = SaleSubscriptionConstants.TABLE_NAME)
public class SaleSubscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = SaleSubscriptionConstants.COLUMN_ID_NAME)
    private int id;
    @Column(name = SaleSubscriptionConstants.COLUMN_BEGIN_SUBSCRIPTION_NAME)
    private LocalDate beginSubscription;
    @Column(name = SaleSubscriptionConstants.COLUMN_DEADLINE_SUBSCRIPTION_NAME)
    private LocalDate deadlineSubscription;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = SaleSubscriptionConstants.COLUMN_FK_SUBSCRIPTION_NAME)
    private Subscription subscription;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = SaleSubscriptionConstants.COLUMN_FK_CLIENT_NAME)
    private Client client;


    public SaleSubscription() {}
    public SaleSubscription(int id, LocalDate beginSubscription, LocalDate deadlineSubscription, Subscription subscription, Client client) {
        this.id = id;
        this.beginSubscription = beginSubscription;
        this.deadlineSubscription = deadlineSubscription;
        this.subscription = subscription;
        this.client = client;
    }


    public int getId() {
        return id;
    }

    public LocalDate getBeginSubscription() {
        return beginSubscription;
    }

    public LocalDate getDeadlineSubscription() {
        return deadlineSubscription;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public Client getClient() {
        return client;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setBeginSubscription(LocalDate beginSubscription) {
        this.beginSubscription = beginSubscription;
    }

    public void setDeadlineSubscription(LocalDate deadlineSubscription) {
        this.deadlineSubscription = deadlineSubscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
