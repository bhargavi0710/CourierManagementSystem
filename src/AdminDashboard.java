import javax.swing.*;
import java.awt.event.*;

public class AdminDashboard extends JFrame {
    private JButton manageEmployeesButton, manageCouriersButton, viewHistoryButton, logoutButton;

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Components
        manageEmployeesButton = new JButton("Manage Employees");
        manageCouriersButton = new JButton("Manage Couriers");
        viewHistoryButton = new JButton("View History");
        logoutButton = new JButton("Logout");

        // Layout
        JPanel panel = new JPanel();
        panel.add(manageEmployeesButton);
        panel.add(manageCouriersButton);
        panel.add(viewHistoryButton);
        panel.add(logoutButton);
        add(panel);

        // ActionListeners
        manageEmployeesButton.addActionListener(e -> new ManageEmployeesForm().setVisible(true));
        manageCouriersButton.addActionListener(e -> new ManageCouriersForm().setVisible(true));
        viewHistoryButton.addActionListener(e -> new ViewHistoryForm().setVisible(true));
        logoutButton.addActionListener(e -> {
            dispose(); // Close admin dashboard
            new LoginForm().setVisible(true); // Show login form again
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AdminDashboard().setVisible(true));
    }
}
