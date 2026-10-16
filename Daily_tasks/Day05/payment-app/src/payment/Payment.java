package payment;

public abstract class Payment {

    private static int counter = 0;

    private final int id;
    private final String payer;
    protected double paidTotal;

    protected Payment(String payer) {
        this.payer = payer;
        this.id = ++counter;
    }

    public abstract String getMode();

    protected abstract double getCharge(double amount);

    public boolean pay(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount: " + amount);
            return false;
        }
        double charge = getCharge(amount);
        double total = amount + charge;
        paidTotal += total;
        System.out.println("[" + getMode() + "] " + payer + " paid " + total + " (charge " + charge + ")");
        return true;
    }

    // overloaded: same name, extra parameter
    public boolean pay(double amount, String note) {
        boolean ok = pay(amount);
        if (ok) {
            System.out.println("    note: " + note);
        }
        return ok;
    }

    public int getId() { return id; }
    public String getPayer() { return payer; }
    public double getPaidTotal() { return paidTotal; }
}
