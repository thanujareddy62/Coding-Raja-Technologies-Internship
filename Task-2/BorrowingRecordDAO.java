import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BorrowingRecordDAO {
    public void addBorrowingRecord(BorrowingRecord record) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO BorrowingRecords (book_id, patron_id, borrow_date, return_date, fine) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, record.getBookId());
            stmt.setInt(2, record.getPatronId());
            stmt.setDate(3, new java.sql.Date(record.getBorrowDate().getTime()));
            stmt.setDate(4, record.getReturnDate() != null ? new java.sql.Date(record.getReturnDate().getTime()) : null);
            stmt.setDouble(5, record.getFine());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<BorrowingRecord> getAllBorrowingRecords() {
        List<BorrowingRecord> records = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM BorrowingRecords";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                BorrowingRecord record = new BorrowingRecord();
                record.setRecordId(rs.getInt("record_id"));
                record.setBookId(rs.getInt("book_id"));
                record.setPatronId(rs.getInt("patron_id"));
                record.setBorrowDate(rs.getDate("borrow_date"));
                record.setReturnDate(rs.getDate("return_date"));
                record.setFine(rs.getDouble("fine"));
                records.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    // Additional methods for updating and deleting borrowing records
}

