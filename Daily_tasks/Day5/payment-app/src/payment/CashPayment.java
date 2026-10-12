package payment;

public class CashPayment extends Payment {

    public CashPayment(String payer) {
        super(payer);
    }

    @Override
    public String getMode() {
        return "CASH";
    }

    @Override
    protected double getCharge(double amount) {
        return 0;
    }
}
