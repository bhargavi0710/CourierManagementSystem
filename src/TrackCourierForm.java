import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class TrackCourierForm extends JFrame {
    private JTextField courierIdField;
    private JButton trackButton;

    public TrackCourierForm() {
        setTitle("Track Courier");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Components
        courierIdField = new JTextField(20);
        trackButton = new JButton("Track");

        // Layout
        JPanel panel = new JPanel();
        panel.add(new JLabel("Courier ID:"));
        panel.add(courierIdField);
        panel.add(trackButton);
        add(panel);

        // ActionListener
        trackButton.addActionListener(e -> trackCourier());
    }

    private void trackCourier() {
        int courierId = Integer.parseInt(courierIdField.getText());

        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM couriers WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, courierId);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String courierInfo = "Courier ID: " + rs.getInt("id") +
                                     "\nSender: " + rs.getString("sender_name") +
                                     "\nReceiver: " + rs.getString("receiver_name") +
                                     "\nPickup Address: " + rs.getString("pickup_address") +
                                     "\nDelivery Address: " + rs.getString("delivery_address") +
                                     "\nStatus: " + rs.getString("status") +
                                     "\nAssigned To: " + rs.getString("assigned_user");
                JOptionPane.showMessageDialog(this, courierInfo);
            } else {
                JOptionPane.showMessageDialog(this, "Courier not found!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
