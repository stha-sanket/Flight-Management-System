package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.model.Customer;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class FlightBookingSystemData {
    private final String dataDir = "resources/data/";
    private final FlightDataManager flightDataManager = new FlightDataManager();
    private final CustomerDataManager customerDataManager = new CustomerDataManager();
    private final BookingDataManager bookingDataManager = new BookingDataManager();
    private List<Flight> flights;
    public FlightBookingSystem fbs; //Add FlightBookingSystem fbs into the constructor

    public FlightBookingSystemData(FlightBookingSystem fbs) { //Create FlightBookingSystemData Object
        this.fbs = fbs; //Set fbs into FlightBookingSystemData
    }
    public void load(FlightBookingSystem fbs) throws IOException {
        flightDataManager.loadData(fbs);
        customerDataManager.loadData(fbs);
        bookingDataManager.loadData(fbs);
        this.flights = fbs.getFlights();
    }

    public void store(FlightBookingSystem fbs) throws IOException {
        flightDataManager.saveData(fbs);
        customerDataManager.saveData(fbs);
        bookingDataManager.saveData(fbs);
        this.flights = fbs.getFlights();
    }
    // Method to get active customers
    public List<Customer> getActiveCustomers() {
        return fbs.getCustomers().stream()
                .filter(customer -> !customer.isDeleted())
                .collect(Collectors.toList());
    }

    // Method to get deleted customers
    public List<Customer> getDeletedCustomers() {
        return fbs.getCustomers().stream()
                .filter(customer -> customer.isDeleted())
                .collect(Collectors.toList());
    }
}