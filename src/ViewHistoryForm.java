import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class ViewHistoryForm extends JFrame {
    private JButton viewEmployeesButton, viewCouriersButton;

    public ViewHistoryForm() {
        setTitle("View Deletion History");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Components
        viewEmployeesButton = new JButton("View Deleted Employees");
        viewCouriersButton = new JButton("View Deleted Couriers");

        // Layout
        JPanel panel = new JPanel();
        panel.add(viewEmployeesButton);
        panel.add(viewCouriersButton);
        add(panel);

        // ActionListeners
        viewEmployeesButton.addActionListener(e -> viewDeletedEmployees());
        viewCouriersButton.addActionListener(e -> viewDeletedCouriers());
    }

    private void viewDeletedEmployees() {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM previous_employees";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            StringBuilder employeeData = new StringBuilder("Deleted Employees:\n");
            while (rs.next()) {
                employeeData.append("ID: ").append(rs.getInt("id")).append(", ");
                employeeData.append("Name: ").append(rs.getString("name")).append(", ");
                employeeData.append("Position: ").append(rs.getString("position")).append(", ");
                employeeData.append("Email: ").append(rs.getString("email")).append(", ");
                employeeData.append("Phone: ").append(rs.getString("phone")).append(", ");
                employeeData.append("Deleted At: ").append(rs.getTimestamp("deleted_at")).append("\n");
            }

            JOptionPane.showMessageDialog(this, employeeData.toString());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void viewDeletedCouriers() {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM previous_couriers";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            StringBuilder courierData = new StringBuilder("Deleted Couriers:\n");
            while (rs.next()) {
                courierData.append("ID: ").append(rs.getInt("id")).append(", ");
                courierData.append("Sender: ").append(rs.getString("sender_name")).append(", ");
                courierData.append("Receiver: ").append(rs.getString("receiver_name")).append(", ");
                courierData.append("Pickup Address: ").append(rs.getString("pickup_address")).append(", ");
                courierData.append("Delivery Address: ").append(rs.getString("delivery_address")).append(", ");
                courierData.append("Status: ").append(rs.getString("status")).append(", ");
                courierData.append("Deleted At: ").append(rs.getTimestamp("deleted_at")).append("\n");
            }

            JOptionPane.showMessageDialog(this, courierData.toString());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
