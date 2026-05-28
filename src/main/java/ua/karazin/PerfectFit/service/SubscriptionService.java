package ua.karazin.PerfectFit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.karazin.PerfectFit.entity.Subscription;
import ua.karazin.PerfectFit.repository.SubscriptionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    @Autowired
    SubscriptionService(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    public Subscription getSubscriptionById(Long subscriptionId) {
        Optional<Subscription> optionalSubscription = subscriptionRepository.findById(subscriptionId);
        return optionalSubscription.orElse(null);
    }

    public Subscription createSubscription(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

    public Subscription updateSubscription(Subscription subscription) {
        Optional<Subscription> optionalSubscription = subscriptionRepository.findById(Long.valueOf(subscription.getId()));

        if(optionalSubscription.isPresent()) {
            Subscription existingSubscription = optionalSubscription.get();
            existingSubscription.setName(subscription.getName());
            existingSubscription.setDescription(subscription.getDescription());
            existingSubscription.setPrice(subscription.getPrice());
            return subscriptionRepository.save(existingSubscription);
        }
        return null;
    }

    public void deleteSubscription(Long subscriptionId) {
        subscriptionRepository.deleteById(subscriptionId);
    }


}
