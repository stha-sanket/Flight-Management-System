package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class FlightDataManager implements DataManager {

    private static final String FLIGHTS_FILE_PATH = "resources/data/flights.txt";
    private static final String DELETED_FLIGHTS_FILE_PATH = "resources/data/deletedFlights.txt";

    @Override
    public void loadData(FlightBookingSystem fbs) throws IOException {
        loadFlightsFromFile(fbs, FLIGHTS_FILE_PATH, false);
        loadFlightsFromFile(fbs, DELETED_FLIGHTS_FILE_PATH, true);
    }

    private void loadFlightsFromFile(FlightBookingSystem fbs, String filePath, boolean isDeleted) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split("::");
                if (values.length != 10) {
                    continue; // Skip malformed lines
                }
                try {
                    int id = Integer.parseInt(values[0]);
                    String flightNumber = values[1];
                    String destination = values[2];
                    String origin = values[3];
                    String departureDate = values[4];
                    int capacity = Integer.parseInt(values[5]);
                    double price = Double.parseDouble(values[6]);
                    String duration = values[7];
                    double vegMealCost = Double.parseDouble(values[8]);
                    double nonVegMealCost = Double.parseDouble(values[9]);

                    Flight flight = new Flight(id, flightNumber, destination, origin, departureDate, capacity, price, duration, vegMealCost, nonVegMealCost);
                    flight.setDeleted(isDeleted);
                    fbs.addFlight(flight);

                    if (id >= fbs.getNextFlightId()) {
                        fbs.setNextFlightId(id + 1);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing flight data: " + line);
                } catch (DateTimeParseException e) {
                    System.err.println("Error parsing date: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            // If file is not found, do nothing
            // This is to allow the system to start with no data
             if (!filePath.equals(FLIGHTS_FILE_PATH)) {
                System.err.println("Deleted flights file not found. Starting with empty list.");
            }
        }
    }

    @Override
    public void saveData(FlightBookingSystem fbs) throws IOException {
        saveFlightsToFile(fbs, FLIGHTS_FILE_PATH, false);
        saveFlightsToFile(fbs, DELETED_FLIGHTS_FILE_PATH, true);
    }

    private void saveFlightsToFile(FlightBookingSystem fbs, String filePath, boolean isDeleted) throws IOException {
        List<Flight> flightsToSave = new ArrayList<>();
        for (Flight flight : fbs.getFlights()) {
            if (flight.isDeleted() == isDeleted) {
                flightsToSave.add(flight);
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Flight flight : flightsToSave) {
                String line = flight.getId() + "::" +
                        flight.getFlightNumber() + "::" +
                        flight.getDestination() + "::" +
                        flight.getOrigin() + "::" +
                        flight.getDepartureDate() + "::" +
                        flight.getCapacity() + "::" +
                        flight.getBasePrice() + "::" +
                        flight.getDuration() + "::" +
                        flight.getVegMealCost() + "::" +
                        flight.getNonVegMealCost();
                bw.write(line);
                bw.newLine();
            }
        }
    }
}