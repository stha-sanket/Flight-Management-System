import bcu.cmp5332.bookingsystem.model.Booking;
import bcu.cmp5332.bookingsystem.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    private Customer customer;

    @BeforeEach
    public void setUp() {
        customer = new Customer(1, "John Doe", "123-456-7890", "john.doe@example.com", "None");
    }

    @Test
    public void testGetId() {
        assertEquals(1, customer.getId());
    }

    @Test
    public void testGetName() {
        assertEquals("John Doe", customer.getName());
    }

    @Test
    public void testGetPhone() {
        assertEquals("123-456-7890", customer.getPhone());
    }

    @Test
    public void testGetEmail() {
        assertEquals("john.doe@example.com", customer.getEmail());
    }

    @Test
    public void testGetSpecialRequests() {
        assertEquals("None", customer.getSpecialRequests());
    }

    @Test
    public void testIsDeleted() {
        assertFalse(customer.isDeleted()); // Initially not deleted
    }

    @Test
    public void testSetDeleted() {
        customer.setDeleted(true);
        assertTrue(customer.isDeleted());
        customer.setDeleted(false); //Reset the variable
    }

    @Test
    public void testAddBooking() {
        Booking booking = new Booking(1, customer, null, "Economy", "Veg", 1, 1); // Create a dummy booking (flight can be null for this test)
        customer.addBooking(booking);
        assertEquals(1, customer.getBookings().size());
        assertTrue(customer.getBookings().contains(booking));
    }
}