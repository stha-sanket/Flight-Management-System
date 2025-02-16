package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.model.Customer;

public class AddCustomer implements Command {

    private final String name;
    private final String phone;
    private final String email;
    private final String specialRequests;

    public AddCustomer(String name, String phone, String email, String specialRequests) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.specialRequests = specialRequests;
    }

    @Override
    public String execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        Customer customer = new Customer(flightBookingSystem.getNextCustomerId(), name, phone, email, specialRequests);
        flightBookingSystem.addCustomer(customer);
        flightBookingSystem.setNextCustomerId(flightBookingSystem.getNextCustomerId() + 1);
        return "Customer " + name + " added with ID " + customer.getId();
    }
}