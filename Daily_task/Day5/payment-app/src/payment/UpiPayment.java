package payment;

public class UpiPayment extends Payment implements Refundable {

    private final String upiId;

    public UpiPayment(String payer, String upiId) {
        super(payer);
        this.upiId = upiId;
    }

    @Override
    public String getMode() {
        return "UPI " + upiId;
    }

    @Override
    protected double getCharge(double amount) {
        return 0;   // no fee on UPI
    }

    @Override
    public boolean pay(double amount) {
        if (!upiId.contains("@")) {
            System.out.println("[UPI] invalid id: " + upiId);
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

    @Override
    public String refundPolicy() {
        return "UPI refunds go back to the same account in 3-5 days";
    }
}
