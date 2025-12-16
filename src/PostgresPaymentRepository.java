import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostgresPaymentRepository implements PaymentRepository {

    private Connection conn;

    public PostgresPaymentRepository() {
        try {
            Class.forName("org.postgresql.Driver");

            conn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/ecommerce",
                    "postgres",
                    "passwordbro"
            );
            System.out.println("Connected to PostgreSQL");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void create(Payment p) {
        String sql = "INSERT INTO payment (id, user_id, amount, provider, status) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, p.getId());
            stmt.setInt(2, p.getUserId());
            stmt.setDouble(3, p.getAmount());
            stmt.setString(4, p.getProvider());
            stmt.setString(5, p.getStatus());
            stmt.executeUpdate();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Payment read(int id) {
        String sql = "SELECT * FROM payment WHERE id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            Payment p = null;
            if (rs.next()) {
                p = new Payment(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getDouble("amount"),
                        rs.getString("provider"),
                        rs.getString("status")
                );
            }
            rs.close();
            stmt.close();
            return p;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Payment> readAll() {
        List<Payment> list = new ArrayList<>();
        String sql = "SELECT * FROM payment";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Payment p = new Payment(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getDouble("amount"),
                        rs.getString("provider"),
                        rs.getString("status")
                );
                list.add(p);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void update(Payment p) {
        String sql = "UPDATE payment SET user_id = ?, amount = ?, provider = ?, status = ? WHERE id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, p.getUserId());
            stmt.setDouble(2, p.getAmount());
            stmt.setString(3, p.getProvider());
            stmt.setString(4, p.getStatus());
            stmt.setInt(5, p.getId());
            stmt.executeUpdate();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM payment WHERE id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
