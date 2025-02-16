import bcu.cmp5332.bookingsystem.model.Flight;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class FlightDynamicPricingTest {

    @Test
    public void testDynamicPricing() {
        // Set up a base flight
        Flight flight = new Flight(1, "BA123", "London", "New York", null, 200, 500.0, "7h", 50.0, 75.0);

        // Test close departure date (less than 7 days)
        LocalDate closeDate = LocalDate.now().plusDays(3);
        flight.setDepartureDate(closeDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        assertTrue(flight.getPrice() >= 500.0 * 1.5); // 50% increase

        // Test medium departure date (less than 30 days)
        LocalDate mediumDate = LocalDate.now().plusDays(15);
        flight.setDepartureDate(mediumDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        assertTrue(flight.getPrice() >= 500.0 * 1.2 && flight.getPrice() < 500.0 * 1.5); // 20% increase

        //Test far departure date (more than 30 days)
        LocalDate farDate = LocalDate.now().plusDays(40);
        flight.setDepartureDate(farDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

         //Assuming it returns the base price if its past the date:
        assertEquals(flight.getBasePrice(), flight.getPrice());

        // Test low seat availability
        flight.setCapacity(100); //set capacity
        //Setting bookings
        for (int i = 0; i < 80; i++) {
            flight.getBookings().add(null); // add null bookings
        }
    }
}