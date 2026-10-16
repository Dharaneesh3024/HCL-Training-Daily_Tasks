package app;

import model.BankAccount;
import service.BankService;

public class Main {

    public static void main(String[] args) {
        BankService bank = new BankService();

        BankAccount dharan = bank.openAccount("Dharan", 20000);
        BankAccount guest = new BankAccount();   // uses chained constructors
        BankAccount ravi = new BankAccount("Ravi");

        System.out.println(dharan);
        System.out.println(guest);
        System.out.println(ravi);
        System.out.println("Accounts created: " + BankAccount.getTotalAccounts());

        int id = dharan.getAccountNumber();
        bank.deposit(id, 2000);
        bank.withdraw(id, 500);
        bank.withdraw(id, 6000);
        bank.withdraw(id, 300);

        System.out.println("Final: " + dharan);
        System.out.println("Expected balance: 15140.0");

        try {
            bank.withdraw(id, 999999);
        } catch (IllegalStateException e) {
            System.out.println("Caught: " + e.getMessage());
        }
    }
}
