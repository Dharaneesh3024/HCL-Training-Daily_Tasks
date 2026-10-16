package payment;

public interface Refundable {

    boolean refund(double amount);

    default String refundPolicy() {
        return "Refund within 7 days";
    }
}
