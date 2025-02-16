package bcu.cmp5332.bookingsystem.model;

public class Seat {
    private int seatNumber;
    private String seatClass;
    private boolean isBooked;

    public Seat(int seatNumber, String seatClass) {
        this.seatNumber = seatNumber;
        this.seatClass = seatClass;
        this.isBooked = false;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public String getSeatClass() {
        return seatClass;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }
}