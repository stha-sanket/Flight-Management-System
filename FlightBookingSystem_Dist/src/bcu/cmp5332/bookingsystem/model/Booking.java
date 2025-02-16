package bcu.cmp5332.bookingsystem.model;

public class Booking {
    private int id;
    private Customer customer;
    private Flight flight;
    private boolean isCancelled;

    private String seatClass;
    private String mealPreference;
    private int seatNumber; //New Seat
    private int numberOfBags; //New bags

    public Booking(int id, Customer customer, Flight flight, String seatClass, String mealPreference, int seatNumber, int numberOfBags) {
        this.id = id;
        this.customer = customer;
        this.flight = flight;
        this.isCancelled = false;
        this.seatClass = seatClass;
        this.mealPreference = mealPreference;
        this.seatNumber = seatNumber;
        this.numberOfBags = numberOfBags;
    }

    public int getId() {
        return id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Flight getFlight() {
        return flight;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public String getSeatClass() {
        return seatClass;
    }

    public void setSeatClass(String seatClass) {
        this.seatClass = seatClass;
    }

    public String getMealPreference() {
        return mealPreference;
    }

    public void setMealPreference(String mealPreference) {
        this.mealPreference = mealPreference;
    }

     public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public int getNumberOfBags() {
        return numberOfBags;
    }

    public void setNumberOfBags(int numberOfBags) {
        this.numberOfBags = numberOfBags;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", customer=" + customer.getName() +
                ", flight=" + flight.getFlightNumber() +
                ", isCancelled=" + isCancelled +
                '}';
    }
}	