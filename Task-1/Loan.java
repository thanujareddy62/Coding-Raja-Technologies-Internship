import java.sql.Timestamp;

public class Loan {
    private int loanId;
    private int userId;
    private double loanAmount;
    private double outstandingAmount;
    private Timestamp loanDate;

    // Constructors, getters, and setters

    public void makePayment(double amount) {
        if (amount > 0 && amount <= this.outstandingAmount) {
            this.outstandingAmount -= amount;
        }
    }
}

