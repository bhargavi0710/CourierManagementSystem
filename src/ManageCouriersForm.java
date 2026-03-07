import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class ManageCouriersForm extends JFrame {
    private JTextField senderField, receiverField, pickupField, deliveryField, statusField, assignedUserField;
    private JButton addButton, updateButton, deleteButton, viewButton;

    public ManageCouriersForm() {
        setTitle("Manage Couriers");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Components
        senderField = new JTextField(20);
        receiverField = new JTextField(20);
        pickupField = new JTextField(20);
        deliveryField = new JTextField(20);
        statusField = new JTextField(20);
        assignedUserField = new JTextField(20);

        addButton = new JButton("Add Courier");
        updateButton = new JButton("Update Courier");
        deleteButton = new JButton("Delete Courier");
        viewButton = new JButton("View Couriers");

        // Layout
        JPanel panel = new JPanel();
        panel.add(new JLabel("Sender Name:"));
        panel.add(senderField);
        panel.add(new JLabel("Receiver Name:"));
        panel.add(receiverField);
        panel.add(new JLabel("Pickup Address:"));
        panel.add(pickupField);
        panel.add(new JLabel("Delivery Address:"));
        panel.add(deliveryField);
        panel.add(new JLabel("Status:"));
        panel.add(statusField);
        panel.add(new JLabel("Assigned User:"));
        panel.add(assignedUserField);
        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        panel.add(viewButton);
        add(panel);

        // ActionListeners
        addButton.addActionListener(e -> addCourier());
        updateButton.addActionListener(e -> updateCourier());
        deleteButton.addActionListener(e -> deleteCourier());
        viewButton.addActionListener(e -> viewCouriers());
    }

    private void addCourier() {
        String sender = senderField.getText();
        String receiver = receiverField.getText();
        String pickup = pickupField.getText();
        String delivery = deliveryField.getText();
        String status = statusField.getText();
        String assignedUser = assignedUserField.getText();

        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO couriers (sender_name, receiver_name, pickup_address, delivery_address, status, assigned_user) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, sender);
            stmt.setString(2, receiver);
            stmt.setString(3, pickup);
            stmt.setString(4, delivery);
            stmt.setString(5, status);
            stmt.setString(6, assignedUser);

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Courier added successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void updateCourier() {
        String sender = senderField.getText();
        String receiver = receiverField.getText();
        String pickup = pickupField.getText();
        String delivery = deliveryField.getText();
        String status = statusField.getText();
        String assignedUser = assignedUserField.getText();

        try (Connection conn = DBConnection.getConnection()) {
            String query = "UPDATE couriers SET receiver_name = ?, pickup_address = ?, delivery_address = ?, status = ?, assigned_user = ? WHERE sender_name = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, receiver);
            stmt.setString(2, pickup);
            stmt.setString(3, delivery);
            stmt.setString(4, status);
            stmt.setString(5, assignedUser);
            stmt.setString(6, sender);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Courier updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Courier not found!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void deleteCourier() {
        String sender = senderField.getText();

        try (Connection conn = DBConnection.getConnection()) {
            String query = "DELETE FROM couriers WHERE sender_name = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, sender);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(this, "Courier deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Courier not found!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void viewCouriers() {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM couriers";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            StringBuilder courierData = new StringBuilder("Couriers:\n");
            while (rs.next()) {
                courierData.append("ID: ").append(rs.getInt("id")).append(", ");
                courierData.append("Sender: ").append(rs.getString("sender_name")).append(", ");
                courierData.append("Receiver: ").append(rs.getString("receiver_name")).append(", ");
                courierData.append("Pickup Address: ").append(rs.getString("pickup_address")).append(", ");
                courierData.append("Delivery Address: ").append(rs.getString("delivery_address")).append(", ");
                courierData.append("Status: ").append(rs.getString("status")).append(", ");
                courierData.append("Assigned User: ").append(rs.getString("assigned_user")).append("\n");
            }

            JOptionPane.showMessageDialog(this, courierData.toString());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
