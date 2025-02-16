package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import java.io.IOException;

public class DeleteFlightAction {

    public static void deleteFlight(FlightBookingSystem fbs, FlightBookingSystemData data, JFrame parent) {
        String flightIdStr = JOptionPane.showInputDialog(parent, "Enter Flight ID to delete:");
        if (flightIdStr != null && !flightIdStr.isEmpty()) {
            try {
                int flightId = Integer.parseInt(flightIdStr);
                fbs.deleteFlight(flightId);
                data.store(fbs);
                JOptionPane.showMessageDialog(parent, "Flight deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(parent, "Invalid Flight ID format.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(parent, "Error saving data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
