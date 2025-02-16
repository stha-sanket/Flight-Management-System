package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.commands.AddBooking;
import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class IssueBookingWindow extends JFrame implements ActionListener {

    private final FlightBookingSystem fbs;
    private final FlightBookingSystemData data;
    private JComboBox<String> customerCombo;
    private JComboBox<String> flightCombo;
    private JComboBox<String> seatClassCombo = new JComboBox<>(new String[]{"Economy", "Business", "FirstClass"});
    private JComboBox<String> mealPreferenceCombo = new JComboBox<>(new String[]{"Veg", "Non-Veg"});
    private JLabel priceLabel = new JLabel("Price: ");
    private JButton issueButton = new JButton("Issue Booking");
    private JButton cancelButton = new JButton("Cancel");
    private JTextField numberOfBagsField = new JTextField("0", 5); // Bags
    private JButton applyPromoButton = new JButton("Apply Promocode"); // AddPromo
    private Flight selectedFlight;
    private Customer selectedCustomer;
    private String promocode;

    public IssueBookingWindow(FlightBookingSystem fbs, FlightBookingSystemData data) {
        this.fbs = fbs;
        this.data = data;
        initialize();
    }
    private void loadCustomers() {
        customerCombo.removeAllItems();  // Clear existing items
        customerCombo.addItem("");  // Add blank option

        List<Customer> customers = fbs.getActiveCustomers();
        for (Customer customer : customers) {
            customerCombo.addItem(fbs.getCustomerDisplayString(customer));  // Add customers to combo box
        }
    }

    private void loadFlights() {
        flightCombo.removeAllItems();  // Clear existing items
        flightCombo.addItem("");  // Add blank option

        List<Flight> flights = fbs.getActiveFlights();
        for (Flight flight : flights) {
            flightCombo.addItem(fbs.getFlightDisplayString(flight));  // Add flights to combo box
        }
    }
    private void initialize() {
        setTitle("Issue Booking");
        setSize(500, 600); // Adjusted size
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout()); // Use GridBagLayout for flexibility
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding between components

        // Initialize Customer Combo
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Customer:"), gbc);
        customerCombo = new JComboBox<>();
        customerCombo.addItem("");  // Add blank option
        loadCustomers();  // Populate customer list after adding blank option
        gbc.gridx = 1;
        panel.add(customerCombo, gbc);

        // Initialize Flight Combo
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(new JLabel("Flight:"), gbc);
        flightCombo = new JComboBox<>();
        flightCombo.addItem("");  // Add blank option
        loadFlights();  // Populate flight list after adding blank option
        gbc.gridx = 1;
        panel.add(flightCombo, gbc);


        // Seat Class Combo
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(new JLabel("Seat Class:"), gbc);
        seatClassCombo = new JComboBox<>(new String[]{"", "Economy", "Business", "FirstClass"});
        gbc.gridx = 1;
        panel.add(seatClassCombo, gbc);

        // Meal Preference Combo
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(new JLabel("Meal Preference:"), gbc);
        mealPreferenceCombo = new JComboBox<>(new String[]{"", "Veg", "Non-Veg"});
        gbc.gridx = 1;
        panel.add(mealPreferenceCombo, gbc);

        // Number of Bags Field
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(new JLabel("Number of Bags:"), gbc);
        gbc.gridx = 1;
        panel.add(numberOfBagsField, gbc);

        // Price Label
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(new JLabel("Price:"), gbc);
        gbc.gridx = 1;
        panel.add(priceLabel, gbc);

        // Buttons (Issue, Cancel, Apply Promo)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Align buttons properly
        buttonPanel.add(issueButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(applyPromoButton);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2; // Take up 2 columns
        panel.add(buttonPanel, gbc);
     // Change button colors
        issueButton.setBackground(new Color(0, 123, 255)); // Blue color for issueButton
        issueButton.setForeground(Color.WHITE); // White text color for issueButton
        cancelButton.setBackground(new Color(220, 53, 69)); // Red color for cancelButton
        cancelButton.setForeground(Color.WHITE); // White text color for cancelButton
        applyPromoButton.setBackground(new Color(28, 200, 138)); // Green color for applyPromoButton
        applyPromoButton.setForeground(Color.WHITE); // White text color for applyPromoButton

        issueButton.addActionListener(this);
        cancelButton.addActionListener(this);
        seatClassCombo.addActionListener(this);
        mealPreferenceCombo.addActionListener(this);
        customerCombo.addActionListener(this);
        flightCombo.addActionListener(this);
        numberOfBagsField.addActionListener(this);
        applyPromoButton.addActionListener(this);

        add(panel);

        // Initial price update
        updatePrice();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == issueButton) {
            try {
                if (selectedCustomer == null || selectedFlight == null) {
                    JOptionPane.showMessageDialog(this, "Please select a customer and a flight.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String seatClass = (String) seatClassCombo.getSelectedItem();
                String mealPreference = (String) mealPreferenceCombo.getSelectedItem();
                int numberOfBags = Integer.parseInt(numberOfBagsField.getText()); // New code

                if (selectedFlight.getAvailableSeats() <= 0) {
                    JOptionPane.showMessageDialog(this, "Flight is full. Cannot book.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                AddBooking command = new AddBooking(selectedCustomer.getId(), selectedFlight.getId(), seatClass, mealPreference, numberOfBags);
                String result = command.execute(fbs);
                data.store(fbs);

                JOptionPane.showMessageDialog(this, "Booking issued successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                dispose();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid number format.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException | FlightBookingSystemException ex) {
                JOptionPane.showMessageDialog(this, "Error saving data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == cancelButton) {
            dispose();
        } else if (e.getSource() == applyPromoButton) {
            PromocodeDialog promocodeDialog = new PromocodeDialog(this);
            promocodeDialog.setVisible(true);

            promocode = promocodeDialog.getPromocode();
            updatePrice();
        } else if (e.getSource() == customerCombo) {
             List<Customer> activeCustomers = fbs.getActiveCustomers();
                 //The if statement code
                if (customerCombo.getSelectedItem() != null && !customerCombo.getSelectedItem().toString().isEmpty()) {
                  int selectedIndex = customerCombo.getSelectedIndex() - 1;
                    if (selectedIndex >= 0 && selectedIndex < activeCustomers.size()) {
                        selectedCustomer = activeCustomers.get(selectedIndex);
                        updatePrice();
                    }
                }
        } else if (e.getSource() == flightCombo) {
            List<Flight> activeFlights = fbs.getActiveFlights();
                if (flightCombo.getSelectedItem() != null && !flightCombo.getSelectedItem().toString().isEmpty()) {
                    int selectedIndex = flightCombo.getSelectedIndex() - 1;
                    if (selectedIndex >= 0 && selectedIndex < activeFlights.size()) {
                        selectedFlight = activeFlights.get(selectedIndex);
                        updatePrice();
                    }
                }
        } else {
            updatePrice(); // Update price based on selection
        }
    }
    private void updatePrice() {
        try {
             if (selectedFlight == null) {
                priceLabel.setText("Price: Select a flight");
                return;
            }

            String seatClass = (String) seatClassCombo.getSelectedItem();
            String mealPreference = (String) mealPreferenceCombo.getSelectedItem();
            int numberOfBags = Integer.parseInt(numberOfBagsField.getText()); // Get number of bags

            double basePrice = selectedFlight.getPrice();
            double mealCost = 0.0;

             if ("Veg".equals(mealPreference)) {
                    mealCost = selectedFlight.getVegMealCost();
                } else if ("Non-Veg".equals(mealPreference)) {
                    mealCost = selectedFlight.getNonVegMealCost();
                }

            double seatClassMultiplier = 1.0;

            switch (seatClass) {
                case "Business":
                    seatClassMultiplier = 2.0;
                    mealCost *= 2;
                    break;
                case "FirstClass":
                    seatClassMultiplier = 4.0;
                    mealCost *= 4;
                    break;
                default:
                    seatClassMultiplier = 1.0;
                    break;
            }

             double baggageFee = 0.0;

            if (numberOfBags > 2) {
                baggageFee = (numberOfBags - 2) * 20.0;
            }

            // Promocode logic
            double discount = 0.0;

            if (promocode != null) {
                if (promocode.equals("123456789")) {
                    discount = 0.5; // 50% discount
                } else if (promocode.equals("122222222")) {
                    discount = 1.0; // 100% discount
                }
            }

            double totalPrice = (basePrice * seatClassMultiplier + mealCost + baggageFee) * (1 - discount);

            String priceDetails = String.format(
                "<html>Price Breakdown:<br>" +
                "Original Price: %.2f<br>" +
                "Seat Class (Multiplier: %.1f): +%.2f<br>" +
                "Meal: +%.2f<br>" +
                "Baggage Fee: +%.2f<br>" +
                "Discount: %.2f%% <br>" +
                "Total Price: %.2f</html>",
                basePrice,
                seatClassMultiplier, basePrice * (seatClassMultiplier - 1),
                mealCost,
                baggageFee,
                discount * 100,
                totalPrice
            );

            priceLabel.setText(priceDetails);

        } catch (NumberFormatException ex) {
            priceLabel.setText("Price: Invalid Flight ID");
        }
    }

    // Getter Methods

    public Customer getSelectedCustomer() {
        return selectedCustomer;
    }

    public Flight getSelectedFlight() {
        return selectedFlight;
    }

    public String getSeatClass() {
        return (String) seatClassCombo.getSelectedItem();
    }

    public String getMealPreference() {
        return (String) mealPreferenceCombo.getSelectedItem();
    }

    public int getNumberOfBags() {
        try {
            return Integer.parseInt(numberOfBagsField.getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}