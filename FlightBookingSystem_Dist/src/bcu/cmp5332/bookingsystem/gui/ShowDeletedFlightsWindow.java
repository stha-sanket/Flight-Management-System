package bcu.cmp5332.bookingsystem.gui;

import bcu.cmp5332.bookingsystem.data.FlightBookingSystemData;
import bcu.cmp5332.bookingsystem.model.Flight;
import bcu.cmp5332.bookingsystem.model.FlightBookingSystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class ShowDeletedFlightsWindow extends JFrame implements ActionListener {

    private final FlightBookingSystem fbs;
    private final FlightBookingSystemData data;
    private JTable deletedFlightTable;
    private DefaultTableModel tableModel;
    private JButton restoreFlightButton;

    public ShowDeletedFlightsWindow(FlightBookingSystem fbs, FlightBookingSystemData data) {
        this.fbs = fbs;
        this.data = data;
        initialize();
    }

    private void initialize() {
        setTitle("List of Deleted Flights");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Set the background color of the frame
        getContentPane().setBackground(new Color(245, 245, 245));  // Light gray background

        // Layout setup
        setLayout(new BorderLayout(10, 10));

        // Table Model
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Flight Number");
        tableModel.addColumn("Destination");
        tableModel.addColumn("Origin");
        tableModel.addColumn("Departure Date");
        tableModel.addColumn("Capacity");
        tableModel.addColumn("Price");
        tableModel.addColumn("Duration");

        deletedFlightTable = new JTable(tableModel);
        deletedFlightTable.setFont(new Font("Arial", Font.PLAIN, 14)); // Set font for readability
        deletedFlightTable.setRowHeight(25);
        deletedFlightTable.setSelectionBackground(new Color(0, 123, 255)); // Blue background on selection

        // Add alternating row colors for better readability
        deletedFlightTable.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (row % 2 == 0) {
                    cell.setBackground(new Color(240, 240, 240)); // Light gray for even rows
                } else {
                    cell.setBackground(Color.WHITE); // White for odd rows
                }
                return cell;
            }
        });

        JScrollPane scrollPane = new JScrollPane(deletedFlightTable);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        restoreFlightButton = new JButton("Restore Flight");
        restoreFlightButton.setFont(new Font("Arial", Font.BOLD, 14));
        restoreFlightButton.setBackground(new Color(0, 123, 255)); // Blue background for restore button
        restoreFlightButton.setForeground(Color.WHITE);  // White text
        restoreFlightButton.setFocusPainted(false);
        restoreFlightButton.setPreferredSize(new Dimension(160, 40));  // Make button larger

        restoreFlightButton.addActionListener(this);
        buttonPanel.add(restoreFlightButton);

        loadFlights();

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadFlights() {
        tableModel.setRowCount(0);

        List<Flight> deletedFlights = fbs.getFlights().stream().filter(Flight::isDeleted).toList();

        if (deletedFlights.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No deleted flights found.", "Information", JOptionPane.INFORMATION_MESSAGE);
        }

        for (Flight flight : deletedFlights) {
            Object[] rowData = {flight.getId(), flight.getFlightNumber(), flight.getDestination(), flight.getOrigin(),
                    flight.getDepartureDate(), flight.getCapacity(), flight.getPrice(), flight.getDuration()};
            tableModel.addRow(rowData);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == restoreFlightButton) {
            int selectedRow = deletedFlightTable.getSelectedRow();
            if (selectedRow >= 0) {
                int flightId = (int) tableModel.getValueAt(selectedRow, 0);
                Flight flight = fbs.getFlightById(flightId);
                if (flight != null) {
                    int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to restore this flight?", "Restore Flight", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (confirm == JOptionPane.YES_OPTION) {
                        flight.setDeleted(false);
                        try {
                            data.store(fbs); // Save Flight to file
                            loadFlights(); // Reload new state of the list
                            JOptionPane.showMessageDialog(this, "Flight restored successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(this, "Error saving data.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Flight not found.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a flight to restore.", "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
