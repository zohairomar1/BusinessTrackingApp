package ui;

import model.ListOfPurchases;
import model.Purchase;
import persistance.JsonReader;
import persistance.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class PurchaseGUI extends JFrame {

    private ListOfPurchases listOfPurchases;
    private Purchase testPurchase;
    private Purchase testPurchase2;
    private Purchase testPurchase3;
    private Purchase testPurchase4;
    private Purchase testPurchase5;
    private Purchase testPurchase6;

    private JProgressBar progressBar;
    private JDialog loadingDialog;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    public PurchaseGUI() {
        showLoadingScreen();
        initializeListOfPurchases();
        showMainFrame();
        testPurchase = new Purchase(1,"zohair",2,new ArrayList<>(), 10);
        testPurchase2 = new Purchase(2,"gregor",3,new ArrayList<>(), 20);
        testPurchase3 = new Purchase(33,"paul",5,new ArrayList<>(), 30);
        testPurchase4 = new Purchase(44,"drake",7,new ArrayList<>(), 40);
        testPurchase5 = new Purchase(55,"yeat",9,new ArrayList<>(), 20);
        testPurchase6 = new Purchase(66,"travis",10,new ArrayList<>(), 17);
        listOfPurchases.addPurchase(testPurchase);
        listOfPurchases.addPurchase(testPurchase2);
        listOfPurchases.addPurchase(testPurchase3);
        listOfPurchases.addPurchase(testPurchase4);
        listOfPurchases.addPurchase(testPurchase5);
        listOfPurchases.addPurchase(testPurchase6);
    }

    // Initialize ListOfPurchases with the user-provided revenue goal
    private void showMainFrame() {
        // Set up the main frame
        setTitle("Purchase Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create buttons for adding, removing, displaying all purchases, and finding a purchase
        JButton addPurchaseButton = new JButton("Add Purchase");
        JButton removePurchaseButton = new JButton("Remove Purchase");
        JButton displayAllPurchasesButton = new JButton("Display All Purchases");
        JButton findPurchaseButton = new JButton("Find Purchase");
        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");


        // Add action listeners for the buttons
        addPurchaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPurchase();  // Implement this method to handle adding a purchase
            }
        });

        removePurchaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removePurchase();  // Implement this method to handle removing a purchase
            }
        });

        displayAllPurchasesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayAllPurchases();  // Implement this method to handle displaying all purchases
            }
        });

        findPurchaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findPurchase();  // Implement this method to handle finding a specific purchase
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveProgram();  // Implement this method to handle saving the program
            }
        });

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadProgram();  // Implement this method to handle loading the program
            }
        });

        // Create a label for displaying the revenue goal
        JLabel revenueGoalLabel = new JLabel("Revenue Goal: $" + listOfPurchases.getRevenueGoal());

        // Create a panel for buttons with vertical alignment
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(addPurchaseButton);
        buttonPanel.add(removePurchaseButton);
        buttonPanel.add(displayAllPurchasesButton);
        buttonPanel.add(findPurchaseButton);

        // Add components to the main frame
        add(buttonPanel, BorderLayout.CENTER);
        add(revenueGoalLabel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null); // Center the main frame on the screen
        setVisible(true);
    }

    private void findPurchase() {
        // Implement logic to find a specific purchase based on transactionID
        String input = JOptionPane.showInputDialog(this, "Enter Transaction ID to find:");
        if (input != null && !input.isEmpty()) {
            try {
                int transactionID = Integer.parseInt(input);
                Purchase foundPurchase = listOfPurchases.viewSpecificPurchase(transactionID);

                if (foundPurchase != null) {
                    JOptionPane.showMessageDialog(this, "Found Purchase:\n" + foundPurchase.toString());
                } else {
                    JOptionPane.showMessageDialog(this, "No purchase found with Transaction ID: " + transactionID,
                            "Purchase Not Found", JOptionPane.WARNING_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid Transaction ID. Please enter a valid integer.",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Display all purchases in a pop-up
    private void displayAllPurchases() {
        if (listOfPurchases.viewListOfPurchases().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No purchases to display.");
            return;
        }

        // Build the string representation of all purchases
        StringBuilder purchasesText = new StringBuilder();
        for (Purchase purchase : listOfPurchases.viewListOfPurchases()) {
            purchasesText.append(purchase.toString()).append("\n");
        }

        // Display the purchases in a pop-up dialog
        JTextArea textArea = new JTextArea(purchasesText.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JOptionPane.showMessageDialog(this, scrollPane, "All Purchases", JOptionPane.PLAIN_MESSAGE);
    }

    private void initializeListOfPurchases() {
        // Prompt the user to enter the revenue goal
        String revenueGoalString = JOptionPane.showInputDialog("Enter Revenue Goal:");
        int revenueGoal = 0;

        revenueGoal = Integer.parseInt(revenueGoalString);

        // Initialize ListOfPurchases with the user-provided revenue goal
        listOfPurchases = new ListOfPurchases(revenueGoal);
    }

    // Method to handle adding a purchase
    private void addPurchase() {
        // Prompt the user to enter Purchase details
        String transactionIdInput = JOptionPane.showInputDialog("Enter Transaction ID:");
        String customerName = JOptionPane.showInputDialog("Enter Customer Name:");
        String dayOfPurchaseInput = JOptionPane.showInputDialog("Enter Day of Purchase:");
        String itemsBoughtInput = JOptionPane.showInputDialog("Enter Items Bought (comma-separated):");
        String transactionAmountInput = JOptionPane.showInputDialog("Enter Transaction Amount:");

        try {
            // Parse inputs to the appropriate types
            int transactionId = Integer.parseInt(transactionIdInput);
            int dayOfPurchase = Integer.parseInt(dayOfPurchaseInput);
            int transactionAmount = Integer.parseInt(transactionAmountInput);

            // Validate if the transaction ID is unique
            if (!(listOfPurchases.viewSpecificPurchase(transactionId) == null)) {
                JOptionPane.showMessageDialog(this, "Transaction ID must be unique. Please enter a unique integer.");
                return;
            }

            // Parse itemsBought as ArrayList<String>
            ArrayList<String> itemsBought = new ArrayList<>(Arrays.asList(itemsBoughtInput.split(",")));

            // Create a new Purchase object and add it to the list
            Purchase newPurchase = new Purchase(transactionId, customerName,
                    dayOfPurchase,itemsBought, transactionAmount);
            listOfPurchases.addPurchase(newPurchase);

            // Display success message
            JOptionPane.showMessageDialog(this, "Purchase added successfully.");
        } catch (NumberFormatException e) {
            // Handle invalid input types
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter valid data types for Transaction ID, Day of Purchase, and Transaction Amount.");
        }
    }

    private void saveProgram() {
        try {
            jsonWriter = new JsonWriter("program_data.json");
            jsonWriter.open();
            jsonWriter.write(listOfPurchases);
            jsonWriter.close();
            JOptionPane.showMessageDialog(this, "Program saved successfully!");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: Unable to save program.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadProgram() {
        try {
            jsonReader = new JsonReader("program_data.json");
            listOfPurchases = jsonReader.read();
            JOptionPane.showMessageDialog(this, "Program loaded successfully!");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error: Unable to load program.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    // Method to handle removing a purchase
    private void removePurchase() {
        boolean removed;
        // Prompt the user to enter the transaction ID to be removed
        String transactionIdInput = JOptionPane.showInputDialog("Enter Transaction ID to Remove:");

        // Validate user input
        if (transactionIdInput == null || transactionIdInput.trim().isEmpty()) {
            // User canceled or entered an empty string
            return;
        }

        // Parse the input as an integer
        int transactionIdToRemove;
        try {
            transactionIdToRemove = Integer.parseInt(transactionIdInput);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input. Please enter a valid integer for Transaction ID.");
            return;
        }

        // Attempt to remove the purchase with the specified transaction ID
        if (listOfPurchases.viewSpecificPurchase(transactionIdToRemove) == null) {
            removed = false;
        } else {
            listOfPurchases.removePurchase(transactionIdToRemove);
            removed = true;
        }

        // Display the result to the user
        if (removed) {
            JOptionPane.showMessageDialog(this, "Purchase with Transaction ID " + transactionIdToRemove + " removed successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "No purchase found with Transaction ID " + transactionIdToRemove + ".");
        }
    }

    // Enhanced loading screen with a progress bar
    private void showLoadingScreen() {
        // Create a progress bar
        int duration = 40;
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);

        // Create a dialog to display the loading screen
        loadingDialog = new JDialog(this, "Loading", true);
        loadingDialog.setLayout(new BorderLayout());
        loadingDialog.add(new JLabel("Your personal purchase manager is loading..."), BorderLayout.NORTH);
        loadingDialog.add(progressBar, BorderLayout.CENTER);
        loadingDialog.setSize(1000, 100);
        loadingDialog.setLocationRelativeTo(this);
        loadingDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);

        // Animate the progress bar over the specified duration
        int timerDelay = duration / 100; // Update every 1% of the duration
        int steps = 100;
        int increment = progressBar.getMaximum() / steps;

        Timer timer = new Timer(timerDelay, new ActionListener() {
            int progress = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                progress += increment;
                progressBar.setValue(progress);

                if (progress >= progressBar.getMaximum()) {
                    ((Timer) e.getSource()).stop();
                    loadingDialog.dispose();
                }
            }
        });

        timer.start();

        loadingDialog.setVisible(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PurchaseGUI();
            }
        });
    }
}