package exception;

public class SubscriptionNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SubscriptionNotFoundException(int id) {
        super("No subscription with id " + id);
    }
}
