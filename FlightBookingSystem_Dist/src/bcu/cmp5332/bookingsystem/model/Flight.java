package bcu.cmp5332.bookingsystem.model;

import bcu.cmp5332.bookingsystem.main.FlightBookingSystemException;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Flight {
    private int id;
    private String flightNumber;
    private String destination;
    private String origin;
    private String departureDate;
    private int capacity;
    private double basePrice;
    private String duration;
    private boolean isDeleted;
    private double vegMealCost;
    private double nonVegMealCost;

    private List<Booking> bookings;
    private List<Customer> waitingList; // New

    public Flight(int id, String flightNumber, String destination, String origin, String departureDate, int capacity, double basePrice, String duration, double vegMealCost, double nonVegMealCost) {
        this.id = id;
        this.flightNumber = flightNumber;
        this.destination = destination;
        this.origin = origin;
        this.departureDate = departureDate;
        this.capacity = capacity;
        this.basePrice = basePrice;
        this.duration = duration;
        this.vegMealCost = vegMealCost;
        this.nonVegMealCost = nonVegMealCost;
        this.bookings = new ArrayList<>();
        this.waitingList = new ArrayList<>(); //New
        this.isDeleted = false;
    }

    public int getId() {
        return id;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(double basePrice) {
        this.basePrice = basePrice;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void addBooking(Booking booking) {
        this.bookings.add(booking);
    }

    public void removeBooking(Booking booking) {
        this.bookings.remove(booking);
    }

    public int getAvailableSeats() {
        return capacity - bookings.size();
    }

     public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    //Dynamic Pricing Logic
    public double getPrice() {
         try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate departure = LocalDate.parse(this.departureDate, formatter);
            LocalDate now = LocalDate.now();
            long daysToDeparture = ChronoUnit.DAYS.between(now, departure);

            double price = basePrice;

            // Price increases as departure date approaches
            if (daysToDeparture < 7) {
                price *= 1.5; // 50% increase if less than 7 days
            } else if (daysToDeparture < 30) {
                price *= 1.2; // 20% increase if less than 30 days
            }

            // Price increases as fewer seats are available
            double seatRatio = (double) getAvailableSeats() / capacity;
            if (seatRatio < 0.2) {
                price *= 1.8; // 80% increase if less than 20% seats
            } else if (seatRatio < 0.5) {
                price *= 1.3; // 30% increase if less than 50% seats
            }

            return price;

        } catch (DateTimeParseException e) {
            System.err.println("Invalid date format: " + this.departureDate);
            return basePrice; // Return base price in case of error
        }
    }

    @Override
    public String toString() {
        return "Flight{" +
                "id=" + id +
                ", flightNumber='" + flightNumber + '\'' +
                ", destination='" + destination + '\'' +
                ", origin='" + origin + '\'' +
                ", departureDate='" + departureDate + '\'' +
                ", capacity=" + capacity +
                ", price=" + basePrice +
                ", duration='" + duration + '\'' +
                '}';
    }

    public double getVegMealCost() {
        return vegMealCost;
    }

    public void setVegMealCost(double vegMealCost) {
        this.vegMealCost = vegMealCost;
    }

    public double getNonVegMealCost() {
        return nonVegMealCost;
    }

    public void setNonVegMealCost(double nonVegMealCost) {
        this.nonVegMealCost = nonVegMealCost;
    }

    public List<Customer> getWaitingList() {
        return waitingList;
    }

    public void addWaitingList(Customer customer) {
         this.waitingList.add(customer);
    }

    public void removeWaitingList(Customer customer) {
         this.waitingList.remove(customer);
    }
    public boolean isDeparted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate departureDate = LocalDate.parse(this.departureDate, formatter);
            return departureDate.isBefore(LocalDate.now());
        } catch (DateTimeParseException e) {
            System.err.println("Invalid date format: " + this.departureDate);
            return false; // Assume not departed if date is invalid
        }
    }
}