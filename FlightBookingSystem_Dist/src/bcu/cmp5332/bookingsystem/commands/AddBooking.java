package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

import java.util.List;

public class AddBooking implements Command {

    private int customerId;
    private int flightId;
    private String seatClass;
    private String mealPreference;
    private int numberOfBags;

    public AddBooking(int customerId, int flightId, String seatClass, String mealPreference, int numberOfBags) {
        this.customerId = customerId;
        this.flightId = flightId;
        this.seatClass = seatClass;
        this.mealPreference = mealPreference;
        this.numberOfBags = numberOfBags;
    }

    @Override
    public String execute(FlightBookingSystem fbs) throws FlightBookingSystemException {
        Customer customer = fbs.getCustomerById(customerId);
        if (customer == null) {
            throw new FlightBookingSystemException("Customer with ID " + customerId + " not found.");
        }

        Flight flight = fbs.getFlightById(flightId);
        if (flight == null) {
            throw new FlightBookingSystemException("Flight with ID " + flightId + " not found.");
        }

        //Booking booking = new Booking(fbs.getNextBookingId(), customer, flight, seatClass, mealPreference, 1, numberOfBags);
        //fbs.addBooking(booking); You don't call it anymore

        fbs.addBooking(customer,flight,seatClass,mealPreference,numberOfBags); //Implements here

        fbs.setNextBookingId(fbs.getNextBookingId() + 1);

        return "Booking created for customer " + customerId + " on flight " + flightId + ".\n";
    }
}