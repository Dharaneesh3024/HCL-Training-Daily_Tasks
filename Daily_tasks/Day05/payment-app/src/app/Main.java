package app;

import payment.*;

public class Main {

    public static void main(String[] args) {
        // reference type is the parent, object type decides which pay() runs
        Payment[] payments = {
            new CardPayment("Dharan", "4821", 50000),
            new UpiPayment("Dharan", "dharan@upi"),
            new CashPayment("Dharan")
        };

        System.out.println("--- runtime polymorphism ---");
        for (Payment p : payments) {
            p.pay(1000);
        }

        System.out.println("\n--- overloaded pay() ---");
        payments[1].pay(250, "canteen bill");

        System.out.println("\n--- declined cases ---");
        payments[0].pay(80000);
        new UpiPayment("Ravi", "ravi-upi").pay(100);

        System.out.println("\n--- refunds (only Refundable ones) ---");
        for (Payment p : payments) {
            if (p instanceof Refundable) {
                Refundable r = (Refundable) p;
                r.refund(500);
                System.out.println("    policy: " + r.refundPolicy());
            } else {
                System.out.println("[" + p.getMode() + "] does not support refunds");
            }
        }
    }
}
