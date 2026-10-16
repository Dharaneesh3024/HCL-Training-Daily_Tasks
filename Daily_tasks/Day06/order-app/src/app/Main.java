package app;

import exception.InvalidQuantityException;
import exception.OrderProcessingException;
import service.OrderProcessor;

public class Main {

    public static void main(String[] args) {
        OrderProcessor op = new OrderProcessor();

        System.out.println("=== 1. normal order ===");
        run(op, "pen:3");

        System.out.println("\n=== 2. checked exception, chained cause (STACK TRACE 1) ===");
        run(op, "bag:5");

        System.out.println("\n=== 3. unchecked exception, bad number (STACK TRACE 2) ===");
        run(op, "pen:abc");

        System.out.println("\n=== 4. missing quantity (multi-catch, other branch) ===");
        run(op, "notebook");

        System.out.println("\n=== 5. negative quantity ===");
        run(op, "pen:-2");

        System.out.println("\n=== audit log ===");
        for (String line : op.getAuditLog()) {
            System.out.println(line);
        }
    }

    private static void run(OrderProcessor op, String line) {
        try {
            op.processText(line);
        } catch (OrderProcessingException e) {
            System.out.println("Order failed: " + e.getMessage());
            System.out.println("Root cause: " + e.getCause().getMessage());
            e.printStackTrace(System.out);
        } catch (InvalidQuantityException e) {
            System.out.println("Bad input: " + e.getMessage());
            e.printStackTrace(System.out);
        }
    }
}
