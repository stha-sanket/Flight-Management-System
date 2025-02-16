package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.model.Customer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

public class ViewDeletedCustomersWindow extends JFrame {

    private JTable deletedCustomersTable;
    private DefaultTableModel tableModel;
    private final FlightBookingSystemData data;

    public ViewDeletedCustomersWindow(FlightBookingSystemData data) {
        this.data = data;
        initialize();
    }

    private void initialize() {
        setTitle("Deleted Customers");
        setSize(700, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Set background color
        getContentPane().setBackground(new Color(240, 248, 255));  // Light blue background

        // Table Model
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Phone");
        tableModel.addColumn("Email");

        deletedCustomersTable = new JTable(tableModel);

        // Customize table appearance
        deletedCustomersTable.setFont(new Font("Arial", Font.PLAIN, 14));  // Change font
        deletedCustomersTable.setRowHeight(25);  // Set row height for better readability
        deletedCustomersTable.setSelectionBackground(new Color(173, 216, 230));  // Light blue selection
        deletedCustomersTable.setSelectionForeground(Color.BLACK);  // Black text on selection
        
        // Customize the table header
        JTableHeader tableHeader = deletedCustomersTable.getTableHeader();
        tableHeader.setBackground(new Color(70, 130, 180));  // SteelBlue background for header
        tableHeader.setForeground(Color.WHITE);  // White text for header
        tableHeader.setFont(new Font("Arial", Font.BOLD, 16));  // Bold header font

        // Scroll Pane customization
        JScrollPane scrollPane = new JScrollPane(deletedCustomersTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  // Add padding around the table

        // Load Customers into Table
        loadCustomers(data.getDeletedCustomers());

        // Add components to the window
        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadCustomers(List<Customer> customers) {
        tableModel.setRowCount(0); // Clear existing data

        for (Customer customer : customers) {
            Object[] rowData = {customer.getId(), customer.getName(), customer.getPhone(), customer.getEmail()};
            tableModel.addRow(rowData);
        }
    }
}
