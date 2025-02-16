package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.model.Flight;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class ShowCustomerWindow extends JFrame implements ActionListener {

    private final FlightBookingSystem fbs;
    private final Customer customer;
    private JTextArea detailsArea;
    private JButton cancelButton;
    private FlightBookingSystemData data;

    public ShowCustomerWindow(FlightBookingSystem fbs, Customer customer) {
        this.fbs = fbs;
        this.customer = customer;
        initialize();
    }

    private void initialize() {
        setTitle("Customer Details");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set the background color of the frame
        getContentPane().setBackground(new Color(245, 245, 245));  // Light gray background

        // Layout setup
        setLayout(new BorderLayout(10, 10));

        // Create a text area for displaying customer details
        detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setFont(new Font("Arial", Font.PLAIN, 14));
        detailsArea.setBackground(new Color(255, 255, 255));  // White background for text area
        detailsArea.setForeground(new Color(0, 0, 0));  // Black text for readability
        detailsArea.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));  // Light border

        JScrollPane scrollPane = new JScrollPane(detailsArea);

        // Button to cancel booking
        cancelButton = new JButton("Cancel Booking");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setBackground(new Color(255, 69, 58)); // Red background for cancel
        cancelButton.setForeground(Color.WHITE);  // White text
        cancelButton.setFocusPainted(false);
        cancelButton.setPreferredSize(new Dimension(160, 40));  // Make button larger

        cancelButton.addActionListener(this);

        // Panel for the cancel button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 245, 245));  // Same background color as window
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(cancelButton);

        // Adding components to the window
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        displayCustomerDetails();  // Display the details when the window opens
    }

    private void displayCustomerDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(customer.getId()).append("\n");
        sb.append("Name: ").append(customer.getName()).append("\n");
        sb.append("Phone: ").append(customer.getPhone()).append("\n");
        sb.append("Email: ").append(customer.getEmail()).append("\n\n");
        sb.append("Bookings:\n");

        List<Booking> bookings = fbs.getBookings();
        if (bookings.isEmpty()) {
            sb.append("No bookings found for this customer.\n");
        } else {
            for (Booking booking : bookings) {
                if (booking.getCustomer().getId() == customer.getId()) {
                    Flight flight = booking.getFlight();
                    sb.append("  - Booking ID: ").append(booking.getId())
                            .append(", Flight: ").append(flight.getFlightNumber())
                            .append(", Price: $").append(flight.getPrice())
                            .append(", Cancelled: ").append(booking.isCancelled() ? "Yes" : "No")
                            .append("\n");
                }
            }
        }

        detailsArea.setText(sb.toString());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancelButton) {
            String bookingIdStr = JOptionPane.showInputDialog(this, "Enter Booking ID to cancel:");
            if (bookingIdStr != null && !bookingIdStr.isEmpty()) {
                try {
                    int bookingId = Integer.parseInt(bookingIdStr);
                    Booking booking = fbs.getBookingById(bookingId);

                    if (booking != null) {
                        Flight flight = booking.getFlight();
                        double flightPrice = flight.getPrice();
                        double refundAmount = flightPrice * 0.9;
                        fbs.cancelBooking(bookingId);

                        // Store updated data
                        FlightBookingSystemData data = new FlightBookingSystemData(fbs);
                        data.store(fbs);

                        String message = String.format("Booking cancelled successfully.\nRefund Amount: $%.2f\nCompensation Fee (10%%): $%.2f", refundAmount, flightPrice * 0.1);
                        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);

                        displayCustomerDetails();  // Refresh the displayed details
                    } else {
                        JOptionPane.showMessageDialog(this, "Booking not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid Booking ID format.", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Error saving data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
