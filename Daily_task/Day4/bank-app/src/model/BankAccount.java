package model;

import java.util.Objects;

public class BankAccount {

    private static int totalAccounts = 0;

    private final int accountNumber;
    private String holderName;
    private double balance;

    public BankAccount() {
        this("Unknown");
    }

    public BankAccount(String holderName) {
        this(holderName, 0.0);
    }

    public BankAccount(String holderName, double openingBalance) {
        if (openingBalance < 0) {
            throw new IllegalArgumentException("Opening balance cannot be negative");
        }
        totalAccounts++;
        this.accountNumber = 1000 + totalAccounts;
        this.holderName = holderName;
        this.balance = openingBalance;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit must be positive");
        }
        balance += amount;
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal must be positive");
        }
        if (amount > balance) {
            throw new IllegalStateException("Insufficient funds");
        }

        double fee = 0;
        if (amount > 5000) {
            fee = amount * 0.01;
        }
        balance -= amount;
        balance -= fee;
    }

    public static int getTotalAccounts() { return totalAccounts; }
    public int getAccountNumber() { return accountNumber; }
    public String getHolderName() { return holderName; }
    public double getBalance() { return balance; }

    public void setHolderName(String holderName) {
        if (holderName == null || holderName.isBlank()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.holderName = holderName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BankAccount)) return false;
        BankAccount other = (BankAccount) o;
        return accountNumber == other.accountNumber;
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }

    @Override
    public String toString() {
        return "Account #" + accountNumber + " [" + holderName + "] balance = " + balance;
    }
}
