import java.sql.*;
import java.util.*;

public class BankingSystem {
    private Connection connection;

    public BankingSystem() {
        try {
            // Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish a connection
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/OnlineBanking", "root", "password");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean createUser(String username, String password, String pinCode) {
        String query = "INSERT INTO Users (username, password, pin_code) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, pinCode);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean createAccount(int userId, String accountType) {
        String query = "INSERT INTO Accounts (user_id, account_type) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setString(2, accountType);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean transferFunds(int fromAccountId, int toAccountId, double amount) {
        try {
            connection.setAutoCommit(false);
            Account fromAccount = getAccountById(fromAccountId);
            Account toAccount = getAccountById(toAccountId);

            if (fromAccount != null && toAccount != null && fromAccount.withdraw(amount)) {
                toAccount.deposit(amount);
                updateAccountBalance(fromAccount);
                updateAccountBalance(toAccount);
                logTransaction(fromAccountId, "withdraw", amount);
                logTransaction(toAccountId, "deposit", amount);
                connection.commit();
                return true;
            } else {
                connection.rollback();
            }
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private Account getAccountById(int accountId) {
        String query = "SELECT * FROM Accounts WHERE account_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Account(
                        rs.getInt("account_id"),
                        rs.getInt("user_id"),
                        rs.getString("account_type"),
                        rs.getDouble("balance")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void updateAccountBalance(Account account) {
        String query = "UPDATE Accounts SET balance = ? WHERE account_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDouble(1, account.getBalance());
            stmt.setInt(2, account.getAccountId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void logTransaction(int accountId, String transactionType, double amount) {
        String query = "INSERT INTO Transactions (account_id, transaction_type, amount) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, accountId);
            stmt.setString(2, transactionType);
            stmt.setDouble(3, amount);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public double getAccountBalance(int accountId) {
        Account account = getAccountById(accountId);
        return (account != null) ? account.getBalance() : 0.0;
    }

    public List<Transaction> getTransactionHistory(int accountId) {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM Transactions WHERE account_id = ? ORDER BY transaction_date DESC";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, accountId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                transactions.add(new Transaction(
                        rs.getInt("transaction_id"),
                        rs.getInt("account_id"),
                        rs.getString("transaction_type"),
                        rs.getDouble("amount"),
                        rs.getTimestamp("transaction_date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public boolean applyForLoan(int userId, double loanAmount) {
        String query = "INSERT INTO Loans (user_id, loan_amount, outstanding_amount) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setDouble(2, loanAmount);
            stmt.setDouble(3, loanAmount);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean makeLoanPayment(int loanId, double paymentAmount) {
        String query = "SELECT * FROM Loans WHERE loan_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, loanId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                double outstandingAmount = rs.getDouble("outstanding_amount");
                if (paymentAmount > 0 && paymentAmount <= outstandingAmount) {
                    outstandingAmount -= paymentAmount;
                    String updateQuery = "UPDATE Loans SET outstanding_amount = ? WHERE loan_id = ?";
                    try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                        updateStmt.setDouble(1, outstandingAmount);
                        updateStmt.setInt(2, loanId);
                        updateStmt.executeUpdate();
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

