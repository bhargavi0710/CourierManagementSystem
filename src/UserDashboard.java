import javax.swing.*;
import java.awt.event.*;

public class UserDashboard extends JFrame {
    private JButton addCourierButton, trackCourierButton, logoutButton;

    public UserDashboard(String username) {
        setTitle("User Dashboard");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Components
        addCourierButton = new JButton("Add Courier");
        trackCourierButton = new JButton("Track Courier");
        logoutButton = new JButton("Logout");

        // Layout
        JPanel panel = new JPanel();
        panel.add(addCourierButton);
        panel.add(trackCourierButton);
        panel.add(logoutButton);
        add(panel);

        // ActionListeners
        addCourierButton.addActionListener(e -> new AddCourierForm(username).setVisible(true));
        trackCourierButton.addActionListener(e -> new TrackCourierForm().setVisible(true));
        logoutButton.addActionListener(e -> {
            dispose(); // Close user dashboard
            new LoginForm().setVisible(true); // Show login form again
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UserDashboard("user").setVisible(true));
    }
}
