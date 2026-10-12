import java.util.Scanner;

public class ATMSystem {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        final int PIN = 1234;
        int tries = 0;
        boolean loggedIn = false;

        // Allow a maximum of 3 PIN attempts
        while (tries < 3) {

            System.out.print("Enter PIN: ");
            int enteredPin = input.nextInt();

            if (enteredPin == PIN) {
                loggedIn = true;
                System.out.println("Authentication successful!");
                break;
            }

            tries++;

            if (tries < 3) {
                System.out.println("Incorrect PIN. Please try again.");
            } else {
                System.out.println("Maximum PIN attempts exceeded.");
            }
        }

        // End the program if authentication fails
        if (!loggedIn) {
            input.close();
            return;
        }

        int accountBalance = 5000;
        int option;

        // Display ATM operations
        do {

            System.out.println("\n===== ATM =====");
            System.out.println("1. View Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. View Transactions");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");

            option = input.nextInt();

            switch (option) {

                case 1:
                    System.out.println("Available Balance: ₹" + accountBalance);
                    break;

                case 2:
                    System.out.print("Enter amount to deposit: ");
                    int depositAmount = input.nextInt();

                    if (depositAmount <= 0) {
                        System.out.println("Please enter a valid amount.");
                        continue;
                    }

                    accountBalance += depositAmount;
                    System.out.println("Amount deposited successfully.");
                    break;

                case 3:
                    System.out.print("Enter amount to withdraw: ");
                    int withdrawAmount = input.nextInt();

                    if (withdrawAmount <= 0) {
                        System.out.println("Please enter a valid amount.");
                        continue;
                    }

                    if (withdrawAmount > accountBalance) {
                        System.out.println("Not enough balance.");
                        break;
                    }

                    accountBalance -= withdrawAmount;
                    System.out.println("Amount withdrawn successfully.");
                    break;

                case 4:
                    int[] transactionHistory = {1000, -500, 2000, -300};

                    System.out.println("\n===== TRANSACTION HISTORY =====");

                    for (int amount : transactionHistory) {
                        System.out.println("Transaction Amount: ₹" + amount);
                    }

                    break;

                case 5:
                    System.out.println("Thank you. Have a nice day!");
                    break;

                default:
                    System.out.println("Please select a valid option.");
            }

        } while (option != 5);

        input.close();
    }
}