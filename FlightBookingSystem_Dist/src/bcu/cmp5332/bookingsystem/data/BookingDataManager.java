package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class BookingDataManager implements DataManager {

    private static final String BOOKINGS_FILE_PATH = "resources/data/bookings.txt";
    private static final String DELIMITER = "::"; // Define the new delimiter

    @Override
    public void loadData(FlightBookingSystem fbs) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(BOOKINGS_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(DELIMITER); // Use "::" delimiter
                if (values.length != 7) { // Expecting 7 values
                    continue; // Skip malformed lines
                }
                try {
                    int id = Integer.parseInt(values[0]);
                    int customerId = Integer.parseInt(values[1]);
                    int flightId = Integer.parseInt(values[2]);
                    boolean isCancelled = Boolean.parseBoolean(values[3]);
                    String seatClass = values[4];
                    String mealPreference = values[5];
                    int numberOfBags = Integer.parseInt(values[6]); // New

                    Customer customer = fbs.getCustomerById(customerId);
                    Flight flight = fbs.getFlightById(flightId);

                    if (customer == null || flight == null) {
                        System.err.println("Invalid customer or flight ID in booking data: " + line);
                        continue;
                    }

                    Booking booking = new Booking(id, customer, flight, seatClass, mealPreference, 1, numberOfBags); // Update Booking
                    booking.setCancelled(isCancelled);

                    // Manually add the booking to the system, customer, and flight
                    fbs.addBooking(customer, flight, seatClass, mealPreference, numberOfBags); // ADD THIS CODE

                    if (id >= fbs.getNextBookingId()) {
                        fbs.setNextBookingId(id + 1);
                    }

                } catch (NumberFormatException e) {
                    System.err.println("Error parsing booking data: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            // If file is not found, do nothing
            // This is to allow the system to start with no data
        }
    }

    @Override
    public void saveData(FlightBookingSystem fbs) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(BOOKINGS_FILE_PATH))) {
            for (Booking booking : fbs.getBookings()) {
                String line = booking.getId() + DELIMITER +
                        booking.getCustomer().getId() + DELIMITER +
                        booking.getFlight().getId() + DELIMITER +
                        booking.isCancelled() + DELIMITER +
                        booking.getSeatClass() + DELIMITER +
                        booking.getMealPreference() + DELIMITER +
                        booking.getNumberOfBags(); // Update bags
                bw.write(line);
                bw.newLine();
            }
        }
    }
}
