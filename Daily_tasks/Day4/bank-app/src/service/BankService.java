package service;

import model.BankAccount;
import java.util.ArrayList;
import java.util.List;

public class BankService {

    private final List<BankAccount> accounts = new ArrayList<>();

    public BankAccount openAccount(String name, double openingBalance) {
        BankAccount acc = new BankAccount(name, openingBalance);
        accounts.add(acc);
        return acc;
    }

    public BankAccount find(int accountNumber) {
        for (BankAccount a : accounts) {
            if (a.getAccountNumber() == accountNumber) {
                return a;
            }
        }
        return null;
    }

    public void withdraw(int accountNumber, double amount) {
        BankAccount acc = find(accountNumber);
        if (acc == null) {
            throw new IllegalArgumentException("No such account: " + accountNumber);
        }
        acc.withdraw(amount);
    }

    public void deposit(int accountNumber, double amount) {
        BankAccount acc = find(accountNumber);
        if (acc == null) {
            throw new IllegalArgumentException("No such account: " + accountNumber);
        }
        acc.deposit(amount);
    }
}
