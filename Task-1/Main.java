import java.util.Scanner;
import java.util.List;
import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        BankingSystem bankingSystem = new BankingSystem();
        Scanner scanner = new Scanner(System.in);

        // User input for creating users
        System.out.print("Enter username for User 1: ");
        String username1 = scanner.nextLine();
        System.out.print("Enter password for User 1: ");
        String password1 = scanner.nextLine();
        System.out.print("Enter pin code for User 1: ");
        String pinCode1 = scanner.nextLine();
        bankingSystem.createUser(username1, password1, pinCode1);

        System.out.print("Enter username for User 2: ");
        String username2 = scanner.nextLine();
        System.out.print("Enter password for User 2: ");
        String password2 = scanner.nextLine();
        System.out.print("Enter pin code for User 2: ");
        String pinCode2 = scanner.nextLine();
        bankingSystem.createUser(username2, password2, pinCode2);

        // User input for creating accounts
        System.out.print("Enter account type for User 1: ");
        String accountType1 = scanner.nextLine();
        bankingSystem.createAccount(1, accountType1);

        System.out.print("Enter account type for User 2: ");
        String accountType2 = scanner.nextLine();
        bankingSystem.createAccount(2, accountType2);

        // User input for transferring funds
        System.out.print("Enter amount to transfer from User 1 to User 2: ");
        double amount = scanner.nextDouble();
        bankingSystem.transferFunds(1, 2, amount);

        // Get account balance
        System.out.println("User 1's balance: " + bankingSystem.getAccountBalance(1));
        System.out.println("User 2's balance: " + bankingSystem.getAccountBalance(2));

        // Get transaction history
        List<Transaction> transactions = bankingSystem.getTransactionHistory(1);
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }

        // User input for applying for loan
        System.out.print("Enter loan amount for User 1: ");
        double loanAmount = scanner.nextDouble();
        bankingSystem.applyForLoan(1, loanAmount);

        // User input for making loan payment
        System.out.print("Enter loan payment amount for User 1: ");
        double paymentAmount = scanner.nextDouble();
        bankingSystem.makeLoanPayment(1, paymentAmount);

        scanner.close();
    }
}

