import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;

public class AddCourierForm extends JFrame {
    private JTextField senderField, receiverField, pickupField, deliveryField;
    private JButton addButton;
    private String username;
    private JTable couriersTable;
    private DefaultTableModel tableModel;

    public AddCourierForm(String username) {
        this.username = username;
        setTitle("Add New Courier");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Components
        senderField = new JTextField(20);
        receiverField = new JTextField(20);
        pickupField = new JTextField(20);
        deliveryField = new JTextField(20);
        addButton = new JButton("Add Courier");

        // Layout for inputs
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Sender Name:"));
        inputPanel.add(senderField);
        inputPanel.add(new JLabel("Receiver Name:"));
        inputPanel.add(receiverField);
        inputPanel.add(new JLabel("Pickup Address:"));
        inputPanel.add(pickupField);
        inputPanel.add(new JLabel("Delivery Address:"));
        inputPanel.add(deliveryField);
        inputPanel.add(addButton);
        
        // Table to display "Your Couriers"
        String[] columnNames = {"Courier ID", "Sender", "Receiver", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0);
        couriersTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(couriersTable);

        // Layout for the whole form
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(inputPanel);
        mainPanel.add(new JLabel("Your Couriers:"));
        mainPanel.add(tableScrollPane);
        add(mainPanel);

        // ActionListener to handle adding courier
        addButton.addActionListener(e -> addCourier());

        // Load existing couriers for the user
        loadUserCouriers();
    }

    private void addCourier() {
        String sender = senderField.getText();
        String receiver = receiverField.getText();
        String pickup = pickupField.getText();
        String delivery = deliveryField.getText();

        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO couriers (sender_name, receiver_name, pickup_address, delivery_address, status, assigned_user) VALUES (?, ?, ?, ?, 'Pending', ?)";
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, sender);
            stmt.setString(2, receiver);
            stmt.setString(3, pickup);
            stmt.setString(4, delivery);
            stmt.setString(5, username);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    int courierId = rs.getInt(1); // Retrieve the auto-generated ID of the inserted courier
                    JOptionPane.showMessageDialog(this, "Courier added successfully! Courier ID: " + courierId);
                    // Clear input fields
                    senderField.setText("");
                    receiverField.setText("");
                    pickupField.setText("");
                    deliveryField.setText("");
                    // Reload couriers table to include the new courier
                    loadUserCouriers();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void loadUserCouriers() {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT id, sender_name, receiver_name, status FROM couriers WHERE assigned_user = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            // Clear the existing table rows
            tableModel.setRowCount(0);

            while (rs.next()) {
                int id = rs.getInt("id");
                String sender = rs.getString("sender_name");
                String receiver = rs.getString("receiver_name");
                String status = rs.getString("status");
                tableModel.addRow(new Object[]{id, sender, receiver, status});
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AddCourierForm("user").setVisible(true));
    }
}
