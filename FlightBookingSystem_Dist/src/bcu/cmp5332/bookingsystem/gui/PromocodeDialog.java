package bcu.cmp5332.bookingsystem.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PromocodeDialog extends JDialog implements ActionListener {

    private JTextField promocodeField = new JTextField(10);
    private JButton applyButton = new JButton("Apply");
    private JButton cancelButton = new JButton("Cancel");
    private String promocode = null;

    public PromocodeDialog(JFrame parent) {
        super(parent, "Apply Promocode", true);
        setLayout(new BorderLayout(10, 10));

        // Create a panel for the input fields and buttons
        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setBackground(new Color(240, 240, 240)); // Set background color for input panel

        JLabel label = new JLabel("Enter Promocode:");
        label.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font for label

        inputPanel.add(label);
        inputPanel.add(promocodeField);

        // Set button colors and styles
        applyButton.setBackground(new Color(51, 153, 255)); // Blue background for apply button
        applyButton.setForeground(Color.WHITE); // White text
        applyButton.setFocusPainted(false); // Remove focus border
        applyButton.setFont(new Font("Arial", Font.BOLD, 14)); // Set font for apply button
        
        cancelButton.setBackground(new Color(255, 51, 51)); // Red background for cancel button
        cancelButton.setForeground(Color.WHITE); // White text
        cancelButton.setFocusPainted(false); // Remove focus border
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14)); // Set font for cancel button

        // Button panel with some spacing
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(240, 240, 240)); // Set background color for button panel
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(applyButton);
        buttonPanel.add(cancelButton);

        applyButton.addActionListener(this);
        cancelButton.addActionListener(this);

        // Set background color for the dialog
        getContentPane().setBackground(new Color(240, 240, 240));

        // Add the input panel and button panel to the dialog
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setSize(300, 150); // Set size of the dialog
        setLocationRelativeTo(parent); // Center the dialog relative to parent window
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == applyButton) {
            promocode = promocodeField.getText();
            if (!promocode.equals("123456789") && !promocode.equals("122222222")) {
                // Show an error message if the promocode is invalid
                JOptionPane.showMessageDialog(this, "Wrong promocode", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                dispose(); // Close the dialog if the promocode is valid
            }
        } else if (e.getSource() == cancelButton) {
            promocode = null;
            dispose(); // Close the dialog without applying a promocode
        }
    }

    public String getPromocode() {
        return promocode;
    }
}
