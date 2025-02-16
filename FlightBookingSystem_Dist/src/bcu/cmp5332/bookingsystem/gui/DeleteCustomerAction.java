package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import java.io.IOException;

public class DeleteCustomerAction {

    public static void deleteCustomer(FlightBookingSystem fbs, FlightBookingSystemData data, JFrame parent) {
        String customerIdStr = JOptionPane.showInputDialog(parent, "Enter Customer ID to delete:");
        if (customerIdStr != null && !customerIdStr.isEmpty()) {
            try {
                int customerId = Integer.parseInt(customerIdStr);
                Customer customerToDelete = fbs.getCustomerById(customerId);

                if (customerToDelete != null) {
                    customerToDelete.setDeleted(true);  // Set deleted to true.
                    data.store(fbs);

                    JOptionPane.showMessageDialog(parent, "Customer deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(parent, "Customer not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(parent, "Invalid Customer ID format.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(parent, "Error saving data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
