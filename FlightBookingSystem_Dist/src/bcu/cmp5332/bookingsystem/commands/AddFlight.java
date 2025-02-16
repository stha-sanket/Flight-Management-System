package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import bcu.cmp5332.bookingsystem.model.Flight;

public class AddFlight implements Command {

    private final String flightNumber;
    private final String destination;
    private final String origin;
    private final String departureDate;
    private final int capacity;
    private final double price;
    private final String duration;
    private final double vegMealCost;
    private final double nonVegMealCost;

    public AddFlight(String flightNumber, String destination, String origin, String departureDate, int capacity, double price, String duration, double vegMealCost, double nonVegMealCost) {
        this.flightNumber = flightNumber;
        this.destination = destination;
        this.origin = origin;
        this.departureDate = departureDate;
        this.capacity = capacity;
        this.price = price;
        this.duration = duration;
        this.vegMealCost = vegMealCost;
        this.nonVegMealCost = nonVegMealCost;
    }

    @Override
    public String execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException {
        Flight flight = new Flight(flightBookingSystem.getNextFlightId(), flightNumber, destination, origin, departureDate, capacity, price, duration, vegMealCost, nonVegMealCost);
        flightBookingSystem.addFlight(flight);
        flightBookingSystem.setNextFlightId(flightBookingSystem.getNextFlightId() + 1);
        return "Flight " + flightNumber + " added with ID " + flight.getId();
    }
}