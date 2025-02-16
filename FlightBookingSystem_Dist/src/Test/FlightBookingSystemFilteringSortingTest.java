import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FlightBookingSystemFilteringSortingTest {

    private FlightBookingSystem fbs;

    @BeforeEach
    public void setUp() {
        fbs = new FlightBookingSystem();

        // Add some sample flights for testing
        fbs.addFlight(new Flight(1, "BA123", "New York", "London", "2024-12-25", 200, 500.0, "7h", 50.0, 75.0));
        fbs.addFlight(new Flight(2, "EZ456", "Rome", "Paris", "2024-11-15", 150, 300.0, "2h", 40.0, 60.0));
        fbs.addFlight(new Flight(3, "FR789", "New York", "Dublin", "2024-12-10", 180, 400.0, "6h", 45.0, 70.0));
        fbs.addFlight(new Flight(4, "BA007", "New York", "London", "2024-12-20", 190, 600.0, "7h", 55.0, 80.0));
    }

    @Test
    public void testFilterFlightsByDestination() {
        List<Flight> londonFlights = fbs.filterFlightsByDestination("New York");
        assertEquals(2, londonFlights.size());
        londonFlights.forEach(flight -> assertEquals("New York", flight.getDestination())); //Checks the destionations are equal

        List<Flight> parisFlights = fbs.filterFlightsByDestination("Paris");
        assertEquals(0, parisFlights.size());
    }

    @Test
    public void testFilterFlightsByPrice() {
        List<Flight> priceFilteredFlights = fbs.filterFlightsByPrice(400.0, 600.0);
        assertEquals(3, priceFilteredFlights.size()); //Flights within price range

        List<Flight> noFlights = fbs.filterFlightsByPrice(700.0, 800.0); //no flights for these values
        assertEquals(0, noFlights.size());
    }

    @Test
    public void testFilterFlightsByAirline() {
        List<Flight> baFlights = fbs.filterFlightsByAirline("BA");
        assertEquals(2, baFlights.size());

        List<Flight> frFlights = fbs.filterFlightsByAirline("FR");
        assertEquals(1, frFlights.size()); //One BA flight
    }

    @Test
    public void testSortFlightsByPrice() {
        List<Flight> sortedFlights = fbs.sortFlightsByPrice();
        assertEquals(4, sortedFlights.size());
        assertEquals(300.0, sortedFlights.get(0).getBasePrice()); // Check the cheapest flight is first

        // Verify the sorting order
        for (int i = 0; i < sortedFlights.size() - 1; i++) {
            assertTrue(sortedFlights.get(i).getBasePrice() <= sortedFlights.get(i + 1).getBasePrice());
        }
    }

    @Test
    public void testSortCustomersByName() {
        fbs.addCustomer(new Customer(1, "Charlie Brown", "123", "a@a.com", "none"));
        fbs.addCustomer(new Customer(2, "Alice Smith", "456", "b@b.com", "none"));
        fbs.addCustomer(new Customer(3, "Bob Johnson", "789", "c@c.com", "none"));

        List<Customer> sortedCustomers = fbs.sortCustomersByName();

        assertEquals("Alice Smith", sortedCustomers.get(0).getName());
        assertEquals("Bob Johnson", sortedCustomers.get(1).getName());
        assertEquals("Charlie Brown", sortedCustomers.get(2).getName());
    }
}