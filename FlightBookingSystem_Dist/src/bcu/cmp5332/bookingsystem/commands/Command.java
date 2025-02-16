package bcu.cmp5332.bookingsystem.commands;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

public interface Command {
    String execute(FlightBookingSystem flightBookingSystem) throws FlightBookingSystemException;
}