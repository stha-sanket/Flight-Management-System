package bcu.cmp5332.bookingsystem.data;

import bcu.cmp5332.bookingsystem.model.Customer;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CustomerDataManager implements DataManager {

    private static final String CUSTOMERS_FILE_PATH = "resources/data/customers.txt";

    @Override
    public void loadData(FlightBookingSystem fbs) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(CUSTOMERS_FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split("::");
                if (values.length != 6) { //Changed length to 6 because you added isDeleted.
                    continue; // Skip malformed lines
                }
                try {
                    int id = Integer.parseInt(values[0]);
                    String name = values[1];
                    String phone = values[2];
                    String email = values[3];
                    String specialRequests = values[4];
                    boolean isDeleted = Boolean.parseBoolean(values[5]); //Getting if the customer is deleted.

                    Customer customer = new Customer(id, name, phone, email, specialRequests);
                    customer.setDeleted(isDeleted); //Setting if customer is deleted or not.

                    fbs.addCustomer(customer);
                    if (id >= fbs.getNextCustomerId()) {
                        fbs.setNextCustomerId(id + 1);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Error parsing customer data: " + line);
                }
            }
        } catch (FileNotFoundException e) {
            //If file is not found, do nothing
            //This is to allow the system to start with no data
        }
    }

    @Override
    public void saveData(FlightBookingSystem fbs) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CUSTOMERS_FILE_PATH))) {
            for (Customer customer : fbs.getCustomers()) {
                String line = customer.getId() + "::" +
                        customer.getName() + "::" +
                        customer.getPhone() + "::" +
                        customer.getEmail() + "::" +
                        customer.getSpecialRequests() + "::" +
                        customer.isDeleted(); // added isDeleted to the storage
                bw.write(line);
                bw.newLine();
            }
        }
    }
}