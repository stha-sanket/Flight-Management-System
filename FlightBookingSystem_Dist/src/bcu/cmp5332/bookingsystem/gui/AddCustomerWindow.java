package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.commands.AddCustomer;
import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AddCustomerWindow extends JFrame implements ActionListener {

    private final FlightBookingSystem fbs;
    private final FlightBookingSystemData data;
    private final MainWindow mainWindow;
    private JTextField nameField = new JTextField(20);
    private JTextField phoneField = new JTextField(20);
    private JTextField emailField = new JTextField(20);
    private JTextArea specialRequestsArea = new JTextArea(5, 20); // Added Special Request Area
    private JButton addButton = new JButton("Add");
    private JButton cancelButton = new JButton("Cancel");

    public AddCustomerWindow(FlightBookingSystem fbs, FlightBookingSystemData data, MainWindow mainWindow) {
        this.fbs = fbs;
        this.data = data;
        this.mainWindow = mainWindow;
        initialize();
    }

    private void initialize() {
        setTitle("Add New Customer");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Fill the components horizontally

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(emailField, gbc);

        // Special Request
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Special Requests:"), gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        JScrollPane scrollPane = new JScrollPane(specialRequestsArea); //Add Scrollable
        panel.add(scrollPane, gbc);
        specialRequestsArea.setPreferredSize(new Dimension(200, 75));

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(addButton, gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(cancelButton, gbc);

        // Set custom button colors
        addButton = createStyledButton(addButton, new Color(40, 167, 69)); // Green Button
        cancelButton = createStyledButton(cancelButton, new Color(220, 53, 69)); // Red Button

        addButton.addActionListener(this);
        cancelButton.addActionListener(this);

        add(panel);
    }

    private JButton createStyledButton(JButton button, Color bgColor) {
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setPreferredSize(new Dimension(150, 40));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false); // Remove the focus border
        button.setToolTipText("Click to " + button.getText().toLowerCase());
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            try {
                String name = nameField.getText();
                String phone = phoneField.getText();
                String email = emailField.getText();
                String specialRequests = specialRequestsArea.getText();

                if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                AddCustomer command = new AddCustomer(name, phone, email, specialRequests);
                String result = command.execute(fbs);

                data.store(fbs);

                JOptionPane.showMessageDialog(this, "Customer added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();

            } catch (FlightBookingSystemException ex) { //ADD exception
                JOptionPane.showMessageDialog(this, "Error saving data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == cancelButton) {
            dispose();
        }
    }
}
