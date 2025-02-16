package bcu.cmp5332.bookingsystem.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWindow extends JFrame implements ActionListener {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private String username = "admin";
    private String password = "123";
    private MainWindow mainWindow;

    public LoginWindow() {
        initialize();
    }

    private void initialize() {
        setTitle("Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        // No image icon is set, simply remove this line
        // setIconImage(new ImageIcon(getClass().getResource("/path/to/icon.png")).getImage()); 

        // Set background color
        getContentPane().setBackground(new Color(34, 45, 50));

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(34, 45, 50));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Create Labels
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        usernameField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        passwordField = new JPasswordField(20);

        loginButton = new JButton("Login");
        loginButton.setBackground(new Color(65, 105, 225));  // Royal blue button
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createLineBorder(new Color(65, 105, 225), 2));
        loginButton.addActionListener(this);
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect for the login button
        loginButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(100, 149, 237)); // Lighter blue on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                loginButton.setBackground(new Color(65, 105, 225)); // Original color
            }
        });

        // Positioning components on the panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        panel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        panel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, gbc);

        add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String enteredUsername = usernameField.getText();
            String enteredPassword = new String(passwordField.getPassword());

            if (enteredUsername.isEmpty() || enteredPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in both fields.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (enteredUsername.equals(username) && enteredPassword.equals(password)) {
                JOptionPane.showMessageDialog(this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                // Close the login window and open the main application window
                this.dispose();
                if (getMainWindow() != null) {
                    getMainWindow().setVisible(true);
                } else {
                    System.out.println("Error on login window.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Getter method for MainWindow
    public MainWindow getMainWindow() {
        return mainWindow;
    }

    // Setter method for MainWindow
    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginWindow loginWindow = new LoginWindow();
            loginWindow.setVisible(true);
        });
    }
}
