package bcu.cmp5332.bookingsystem.main;

import bcu.cmp5332.bookingsystem.commands.Command;
import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData; // Import FlightBookingSystemData
import bcu.cmp5332.bookingsystem.gui.LoginWindow;
import bcu.cmp5332.bookingsystem.gui.MainWindow;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // GUI mode
        SwingUtilities.invokeLater(() -> {
            FlightBookingSystem fbs = new FlightBookingSystem();
            FlightBookingSystemData data = new FlightBookingSystemData(fbs); //fixed it by adding fbs
            try {
                data.load(fbs);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error loading data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

            MainWindow mainWindow = new MainWindow(fbs, data);
            LoginWindow loginWindow = new LoginWindow();
            loginWindow.setMainWindow(mainWindow);
            loginWindow.setVisible(true);
        });
    }
}