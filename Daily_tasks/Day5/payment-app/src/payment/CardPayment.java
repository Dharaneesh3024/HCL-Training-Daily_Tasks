package payment;

public class CardPayment extends Payment implements Refundable {

    private final String last4;
    private final double limit;

    public CardPayment(String payer, String last4, double limit) {
        super(payer);
        this.last4 = last4;
        this.limit = limit;
    }

    @Override
    public String getMode() {
        return "CARD *" + last4;
    }

    @Override
    protected double getCharge(double amount) {
        return amount * 0.02;   // 2% card fee
    }

    @Override
    public boolean pay(double amount) {
        if (amount > limit) {
            System.out.println("[" + getMode() + "] declined, over limit of " + limit);
            return false;
        }
        return super.pay(amount);
    }

    @Override
    public boolean refund(double amount) {
        if (amount <= 0 || amount > paidTotal) {
            return false;
        }
        paidTotal -= amount;
        System.out.println("[" + getMode() + "] refunded " + amount);
        return true;
    }
}
