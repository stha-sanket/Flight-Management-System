import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.Flight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FlightTest {

    private Flight flight;

    @BeforeEach
    public void setUp() {
        flight = new Flight(1, "BA123", "London", "New York", "2024-12-25", 200, 500.0, "7h", 50.0, 75.0);
    }

    @Test
    public void testGetId() {
        assertEquals(1, flight.getId());
    }

    @Test
    public void testGetFlightNumber() {
        assertEquals("BA123", flight.getFlightNumber());
    }

    @Test
    public void testSetFlightNumber() {
        flight.setFlightNumber("EZ456");
        assertEquals("EZ456", flight.getFlightNumber());
    }

    @Test
    public void testGetDestination() {
        assertEquals("London", flight.getDestination());
    }

    @Test
    public void testSetDestination() {
        flight.setDestination("Paris");
        assertEquals("Paris", flight.getDestination());
    }

    @Test
    public void testGetOrigin() {
        assertEquals("New York", flight.getOrigin());
    }

    @Test
    public void testSetOrigin() {
        flight.setOrigin("Rome");
        assertEquals("Rome", flight.getOrigin());
    }

    @Test
    public void testGetDepartureDate() {
        assertEquals("2024-12-25", flight.getDepartureDate());
    }

    @Test
    public void testSetDepartureDate() {
        flight.setDepartureDate("2024-11-15");
        assertEquals("2024-11-15", flight.getDepartureDate());
    }

    @Test
    public void testGetCapacity() {
        assertEquals(200, flight.getCapacity());
    }

    @Test
    public void testSetCapacity() {
        flight.setCapacity(150);
        assertEquals(150, flight.getCapacity());
    }

    @Test
    public void testGetBasePrice() {
        assertEquals(500.0, flight.getBasePrice());
    }

    @Test
    public void testSetBasePrice() {
        flight.setBasePrice(400.0);
        assertEquals(400.0, flight.getBasePrice());
    }

    @Test
    public void testGetDuration() {
        assertEquals("7h", flight.getDuration());
    }

    @Test
    public void testSetDuration() {
        flight.setDuration("2h");
        assertEquals("2h", flight.getDuration());
    }

    @Test
    public void testIsDeleted() {
        assertFalse(flight.isDeleted());
    }

    @Test
    public void testSetDeleted() {
        flight.setDeleted(true);
        assertTrue(flight.isDeleted());
        flight.setDeleted(false); //Resets flight
    }

    @Test
    public void testAddBooking() {
        Booking booking = new Booking(1, null, flight, "Economy", "Veg", 1, 1); // Create a dummy booking (customer can be null)
        flight.addBooking(booking);
        assertEquals(1, flight.getBookings().size());
        assertTrue(flight.getBookings().contains(booking));
    }

    @Test
    public void testRemoveBooking() {
        Booking booking = new Booking(1, null, flight, "Economy", "Veg", 1, 1);
        flight.addBooking(booking);
        flight.removeBooking(booking);
        assertEquals(0, flight.getBookings().size());
    }

    @Test
    public void testGetAvailableSeats() {
        assertEquals(200, flight.getAvailableSeats()); // Initially all seats are available
        //Add bookings
        for (int i = 0; i < 50; i++) {
             Booking booking = new Booking(1, null, flight, "Economy", "Veg", 1, 1); // Create a dummy booking (customer can be null)
             flight.addBooking(booking);
        }
        assertEquals(150, flight.getAvailableSeats());
    }
}