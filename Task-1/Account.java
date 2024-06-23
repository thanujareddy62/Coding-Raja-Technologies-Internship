public class Account {
    private int accountId;
    private int userId;
    private String accountType;
    private double balance;

    // Constructors, getters, and setters

    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && this.balance >= amount) {
            this.balance -= amount;
            return true;
        }
        return false;
    }
}

