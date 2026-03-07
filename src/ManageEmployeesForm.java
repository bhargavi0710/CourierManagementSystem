import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class ManageEmployeesForm extends JFrame {
    private JTextField nameField, positionField, emailField, phoneField;
    private JButton addButton, updateButton, deleteButton, viewButton;

    public ManageEmployeesForm() {
        setTitle("Manage Employees");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Components
        nameField = new JTextField(20);
        positionField = new JTextField(20);
        emailField = new JTextField(20);
        phoneField = new JTextField(20);

        addButton = new JButton("Add Employee");
        updateButton = new JButton("Update Employee");
        deleteButton = new JButton("Delete Employee");
        viewButton = new JButton("View Employees");

        // Layout
        JPanel panel = new JPanel();
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Position:"));
        panel.add(positionField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);
        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        panel.add(viewButton);
        add(panel);

        // ActionListeners
        addButton.addActionListener(e -> addEmployee());
        updateButton.addActionListener(e -> updateEmployee());
        deleteButton.addActionListener(e -> deleteEmployee());
        viewButton.addActionListener(e -> viewEmployees());
    }

    private void addEmployee() {
        String name = nameField.getText();
        String position = positionField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();

        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO employees (name, position, email, phone) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, position);
            stmt.setString(3, email);
            stmt.setString(4, phone);

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Employee added successfully!");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void updateEmployee() {
        String name = nameField.getText();
        String position = positionField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();

        try (Connection conn = DBConnection.getConnection()) {
            String query = "UPDATE employees SET position = ?, email = ?, phone = ? WHERE name = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, position);
            stmt.setString(2, email);
            stmt.setString(3, phone);
            stmt.setString(4, name);

            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "Employee updated successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Employee not found!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void deleteEmployee() {
        String name = nameField.getText();

        try (Connection conn = DBConnection.getConnection()) {
            String query = "DELETE FROM employees WHERE name = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(this, "Employee deleted successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Employee not found!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void viewEmployees() {
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM employees";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            StringBuilder employeeData = new StringBuilder("Employees:\n");
            while (rs.next()) {
                employeeData.append("ID: ").append(rs.getInt("id")).append(", ");
                employeeData.append("Name: ").append(rs.getString("name")).append(", ");
                employeeData.append("Position: ").append(rs.getString("position")).append(", ");
                employeeData.append("Email: ").append(rs.getString("email")).append(", ");
                employeeData.append("Phone: ").append(rs.getString("phone")).append("\n");
            }

            JOptionPane.showMessageDialog(this, employeeData.toString());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
