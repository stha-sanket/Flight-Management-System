package bcu.cmp5332.bookingsystem.model;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    private int id;
    private String name;
    private String phone;
    private String email;
    private String specialRequests;
    private boolean isDeleted;  // New Property
    private List<Booking> bookings; //List of all booking

    public Customer(int id, String name, String phone, String email, String specialRequests) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.specialRequests = specialRequests;
        this.isDeleted = false; // Default: not deleted
        this.bookings = new ArrayList<>(); //Initialize when created
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }


    public String getEmail() {
        return email;
    }

    public String getSpecialRequests() {
        return specialRequests;
    }

    public boolean isDeleted() { //Getter for isDeleted
        return isDeleted;
    }

    public void setDeleted(boolean deleted) { //Setter for isDeleted
        this.isDeleted = deleted;
    }

    //For ShowCustomerWindow
    public List<Booking> getBookings() {
        return bookings;
    }

    //To add the data
    public void addBooking(Booking booking) {
        this.bookings.add(booking);
    }
}