package bcu.cmp5332.bookingsystem.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class FlightBookingSystem {
    private List<Flight> flights;
    private List<Customer> customers;
    private List<Booking> bookings;
    private int nextFlightId = 1;
    private int nextCustomerId = 1;
    private int nextBookingId = 1;

    public FlightBookingSystem() {
        this.flights = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.bookings = new ArrayList<>();
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void addFlight(Flight flight) {
        this.flights.add(flight);
    }

    public void addCustomer(Customer customer) {
        this.customers.add(customer);
    }

    public Booking addBooking(Customer customer, Flight flight, String seatClass, String mealPreference, int numberOfBags) {
         if (flight.getAvailableSeats() <= 0) {
            System.out.println("Flight is full. Cannot book.");
            return null;
        }

        Booking booking = new Booking(nextBookingId++, customer, flight, seatClass, mealPreference, numberOfBags, 1);
        this.bookings.add(booking);
        customer.addBooking(booking);
        flight.addBooking(booking);
        return booking;
    }

    public Flight getFlightById(int id) {
        for (Flight flight : flights) {
            if (flight.getId() == id) { //&& !flight.isDeleted()) removed code for efficiency, if i add code again I have to read from database
                return flight;
            }
        }
        return null;
    }

    public Customer getCustomerById(int id) {
        for (Customer customer : customers) {
            if (customer.getId() == id) { //&& !customer.isDeleted()) Remove code, there were too many bugs.
                return customer;
            }
        }
        return null;
    }

    public Booking getBookingById(int id) {
        for (Booking booking : bookings) {
            if (booking.getId() == id) {
                return booking;
            }
        }
        return null;
    }

    public void cancelBooking(int bookingId) {
        Booking booking = getBookingById(bookingId);
        if (booking != null) {
            booking.setCancelled(true);
            // Update Flight passenger count (you might need to add a method for this)
            // Add cancellation fees logic here (for 80%+)
        }
    }

    public void deleteFlight(int flightId) {
        Flight flight = getFlightById(flightId);
        if (flight != null) {
            flight.setDeleted(true);
        }
    }

    public void deleteCustomer(int customerId) {
        Customer customer = getCustomerById(customerId);
        if (customer != null) {
            customer.setDeleted(true);
        }
    }

    // Method to get only non-deleted flights
    public List<Flight> getActiveFlights() {
        return flights.stream().filter(f -> !f.isDeleted()).collect(Collectors.toList());
    }

    // Method to get only non-deleted customers
    public List<Customer> getActiveCustomers() {
       return customers.stream().filter(c -> !c.isDeleted()).collect(Collectors.toList());
    }

     // Method to get only departed flights
    public List<Flight> getDepartedFlights() {
        return flights.stream().filter(f -> f.isDeparted()).collect(Collectors.toList());
    }

    // Method to get only future flights
    public List<Flight> getFutureFlights() {
        return flights.stream().filter(f -> !f.isDeparted() && !f.isDeleted()).collect(Collectors.toList());
    }

    //Method to filter flights by destinations
    public List<Flight> filterFlightsByDestination(String destination) {
        return getActiveFlights().stream()
                .filter(flight -> flight.getDestination().equalsIgnoreCase(destination))
                .collect(Collectors.toList());
    }

    //Filtering Flights based on price
    public List<Flight> filterFlightsByPrice(double minPrice, double maxPrice) {
        return getActiveFlights().stream()
                .filter(flight -> flight.getPrice() >= minPrice && flight.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }

    //Filtering flights based on airlines
    public List<Flight> filterFlightsByAirline(String airline) {
        return getActiveFlights().stream()
                .filter(flight -> flight.getFlightNumber().startsWith(airline))
                .collect(Collectors.toList());
    }

    //Sorting Flights based on price
    public List<Flight> sortFlightsByPrice() {
        return getActiveFlights().stream()
                .sorted(Comparator.comparing(Flight::getPrice))
                .collect(Collectors.toList());
    }

    //Sorting Customers By name
    public List<Customer> sortCustomersByName() {
        return getActiveCustomers().stream()
                .sorted(Comparator.comparing(Customer::getName))
                .collect(Collectors.toList());
    }

    public int getNextFlightId() {
        return nextFlightId;
    }

    public void setNextFlightId(int nextFlightId) {
        this.nextFlightId = nextFlightId;
    }

    public int getNextCustomerId() {
        return nextCustomerId;
    }

    public void setNextCustomerId(int nextCustomerId) {
        this.nextCustomerId = nextCustomerId;
    }

    public int getNextBookingId() {
        return nextBookingId;
    }

    public void setNextBookingId(int nextBookingId) {
        this.nextBookingId = nextBookingId;
    }
        // Helper method to get a string representation of a flight
    public String getFlightDisplayString(Flight flight) {
        return flight.getFlightNumber() + " - " + flight.getDestination() + " (" + flight.getDepartureDate() + ")";
    }

    // Helper method to get a string representation of a customer
    public String getCustomerDisplayString(Customer customer) {
        return customer.getName() + " (ID: " + customer.getId() + ")";
    }
}