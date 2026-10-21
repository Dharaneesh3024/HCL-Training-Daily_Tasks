package service;

import calculator.SubscriptionCalculator;
import exception.SubscriptionNotFoundException;
import model.Subscription;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class SubscriptionService {

    private final Map<Integer, Subscription> subscriptions = new LinkedHashMap<>();
    private final Map<String, String> emails = new LinkedHashMap<>();

    public void add(Subscription subscription) {
        subscriptions.put(subscription.getId(), subscription);
    }

    public void registerEmail(String customer, String email) {
        emails.put(customer, email);
    }

    public Optional<Subscription> findById(int id) {
        return Optional.ofNullable(subscriptions.get(id));
    }

    public Subscription getById(int id) {
        return findById(id).orElseThrow(() -> new SubscriptionNotFoundException(id));
    }

    public Optional<Subscription> findByCustomer(String customer) {
        return subscriptions.values().stream()
                .filter(s -> s.getCustomer().equalsIgnoreCase(customer))
                .findFirst();
    }

    public Optional<String> findEmail(String customer) {
        return Optional.ofNullable(emails.get(customer));
    }

    public Optional<String> findEmailBySubscription(int id) {
        return findById(id).flatMap(s -> findEmail(s.getCustomer()));
    }

    public Optional<LocalDate> findExpiry(int id) {
        return findById(id).map(s -> SubscriptionCalculator.expiryDate(s.getStartDate(), s.getPlan()));
    }
}
