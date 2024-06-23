import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatronDAO {
    public void addPatron(Patron patron) {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO Patrons (name, contact_info) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, patron.getName());
            stmt.setString(2, patron.getContactInfo());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Patron> getAllPatrons() {
        List<Patron> patrons = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM Patrons";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Patron patron = new Patron();
                patron.setPatronId(rs.getInt("patron_id"));
                patron.setName(rs.getString("name"));
                patron.setContactInfo(rs.getString("contact_info"));
                patrons.add(patron);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patrons;
    }

    // Additional methods for updating and deleting patrons
}

