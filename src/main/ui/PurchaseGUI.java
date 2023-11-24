package ui;

import model.ListOfPurchases;
import model.Purchase;
import persistance.JsonReader;
import persistance.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class PurchaseGUI extends JFrame {

    private ListOfPurchases listOfPurchases;
//    private Purchase testPurchase;
//    private Purchase testPurchase2;
//    private Purchase testPurchase3;
//    private Purchase testPurchase4;
//    private Purchase testPurchase5;
//    private Purchase testPurchase6;

    private JProgressBar progressBar;
    private JDialog loadingDialog;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private JWindow splashScreen;
    int step = 1; // Current step

    public PurchaseGUI() {
        showLoadingScreen();
        initializeListOfPurchases();
        showSplashScreen();
        showMainFrame();

//        testPurchase = new Purchase(1,"zohair",2,new ArrayList<>(), 10);
//        testPurchase2 = new Purchase(2,"gregor",3,new ArrayList<>(), 20);
//        testPurchase3 = new Purchase(33,"paul",5,new ArrayList<>(), 30);
//        testPurchase4 = new Purchase(44,"drake",7,new ArrayList<>(), 40);
//        testPurchase5 = new Purchase(55,"yeat",9,new ArrayList<>(), 20);
//        testPurchase6 = new Purchase(66,"travis",10,new ArrayList<>(), 17);
//        listOfPurchases.addPurchase(testPurchase);
//        listOfPurchases.addPurchase(testPurchase2);
//        listOfPurchases.addPurchase(testPurchase3);
//        listOfPurchases.addPurchase(testPurchase4);
//        listOfPurchases.addPurchase(testPurchase5);
//        listOfPurchases.addPurchase(testPurchase6);
    }

    // loads up the mainframe of the GUI program
    private void showMainFrame() {
        setUpMainFrame();

        JButton addPurchaseButton = new JButton("Add Purchase");
        JButton removePurchaseButton = new JButton("Remove Purchase");
        JButton displayAllPurchasesButton = new JButton("Display All Purchases");
        JButton findPurchaseButton = new JButton("Find Purchase");
        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");
        JButton calcRevenueButton = new JButton("Calculate revenue");
        JButton calcAvgTransButton = new JButton("Calculate average transaction amount");
        JButton calcAvgTransToRevGoalButton =
                new JButton("Calculate average transactions required to reach revenue goal");
        JButton filterOnAmountButton = new JButton("Filter based on amount");
        JButton filterOnDayButton = new JButton("Filter based on day");

        buttonFunction(addPurchaseButton, removePurchaseButton,
                displayAllPurchasesButton, findPurchaseButton, saveButton, loadButton,
                calcRevenueButton, calcAvgTransButton, calcAvgTransToRevGoalButton,
                filterOnDayButton, filterOnAmountButton);
        JLabel revenueGoalLabel = new JLabel("Revenue Goal: $" + listOfPurchases.getRevenueGoal());
        setUpButtonPanel(saveButton, loadButton, addPurchaseButton,
                removePurchaseButton, displayAllPurchasesButton, calcRevenueButton,
                calcAvgTransButton, calcAvgTransToRevGoalButton, filterOnDayButton,
                filterOnAmountButton);
        revenueLabel(revenueGoalLabel);
    }

    private void revenueLabel(JLabel revenueGoalLabel) {
        add(revenueGoalLabel, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null); // centering
        setVisible(true);
    }

    private void buttonFunction(JButton addPurchaseButton, JButton removePurchaseButton,
                                JButton displayAllPurchasesButton, JButton findPurchaseButton,
                                JButton saveButton, JButton loadButton,
                                JButton calcRevenueButton, JButton calcAvgTransButton,
                                JButton calcAvgTransToRevGoalButton, JButton filterOnDayButton,
                                JButton filterOnAmountButton) {
        buttonFunction1(addPurchaseButton, removePurchaseButton,
                displayAllPurchasesButton, findPurchaseButton, saveButton, loadButton, calcRevenueButton);
        calcAvgTransButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcAvgTrans();
            }
        });
        calcAvgTransToRevGoalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcAvgTransToRevGoal();
            }
        });
        filterButtons(filterOnDayButton, filterOnAmountButton);
    }

    private void filterButtons(JButton filterOnDayButton, JButton filterOnAmountButton) {
        filterOnDayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterOnDay();
            }
        });
        filterOnAmountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterOnAmount();
            }
        });
    }

    private void buttonFunction1(JButton addPurchaseButton, JButton removePurchaseButton,
                                 JButton displayAllPurchasesButton, JButton findPurchaseButton,
                                 JButton saveButton, JButton loadButton, JButton calcRevenueButton) {
        addPurchaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addPurchase();
            }
        });
        removePurchaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removePurchase();
            }
        });
        displayAllPurchasesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayAllPurchases();
            }
        });
        findButtonAlone(findPurchaseButton);
        buttonFunction2(saveButton, loadButton, calcRevenueButton);
    }

    private void findButtonAlone(JButton findPurchaseButton) {
        findPurchaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findPurchase();
            }
        });
    }

    private void buttonFunction2(JButton saveButton, JButton loadButton, JButton calcRevenueButton) {
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveProgram();
            }
        });
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadProgram();
            }
        });
        calcRevenueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateRevenue();
            }
        });
    }


    // Create a panel for buttons with vertical alignment
    private void setUpButtonPanel(JButton saveButton, JButton loadButton,
                                  JButton addPurchaseButton, JButton removePurchaseButton,
                                  JButton displayAllPurchasesButton, JButton calcRevenueButton,
                                  JButton calcAvgTransButton, JButton calcAvgTransToRevGoalButton,
                                  JButton filterOnDayButton, JButton filterOnAmountButton) {

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(addPurchaseButton);
        buttonPanel.add(removePurchaseButton);
        buttonPanel.add(displayAllPurchasesButton);
        buttonPanel.add(calcRevenueButton);
        buttonPanel.add(calcAvgTransButton);
        buttonPanel.add(calcAvgTransToRevGoalButton);
        buttonPanel.add(filterOnDayButton);
        buttonPanel.add(filterOnAmountButton);

        add(buttonPanel, BorderLayout.CENTER);
    }

    private void setUpMainFrame() {
        setTitle("Purchase Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    private void filterOnDay() {
        int startDay = Integer.parseInt(JOptionPane.showInputDialog("Enter start day:"));
        int endDay = Integer.parseInt(JOptionPane.showInputDialog("Enter end day:"));
        ArrayList<Purchase> filteredPurchases = listOfPurchases.filterPurchasesBasedOnDay(startDay, endDay);
        StringBuilder message = new StringBuilder("Filtered Purchases Based on Day:\n");

        for (Purchase purchase : filteredPurchases) {
            message.append(purchase.toString()).append("\n");
        }
        JOptionPane.showMessageDialog(this,
                message,
                "Filtered Purchases Based on Day",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void filterOnAmount() {
        int startDay = Integer.parseInt(JOptionPane.showInputDialog("Enter a starting amount: "));
        int endDay = Integer.parseInt(JOptionPane.showInputDialog("Enter an ending amount: "));
        ArrayList<Purchase> filteredPurchases = listOfPurchases.filterPurchasesBasedOnDay(startDay, endDay);
        StringBuilder message = new StringBuilder("Filtered Purchases Based on Amount:\n");

        for (Purchase purchase : filteredPurchases) {
            message.append(purchase.toString()).append("\n");
        }

        JOptionPane.showMessageDialog(this, message,
                "Filtered Purchases based on Amount",
                JOptionPane.INFORMATION_MESSAGE);
    }


    private void calcAvgTransToRevGoal() {
        float averageTransactionsToReachGoal = listOfPurchases.calculateAverageTransactionsRequiredToReachRevGoal();

        JOptionPane.showMessageDialog(this,
                "Average Transactions to Reach Revenue Goal: " + averageTransactionsToReachGoal,
                "Average Transactions to Reach Revenue Goal",
                JOptionPane.INFORMATION_MESSAGE);
    }


    private void calculateRevenue() {
        int totalRevenue = listOfPurchases.calculateRevenue();

        JOptionPane.showMessageDialog(this,
                "Total Revenue: $" + totalRevenue,
                "Revenue Calculation",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void calcAvgTrans() {
        float averageTransactionAmount = listOfPurchases.calculateAverageTransactionSpend();

        JOptionPane.showMessageDialog(this,
                "Average Transaction Amount: $" + averageTransactionAmount,
                "Average Transaction Amount",
                JOptionPane.INFORMATION_MESSAGE);
    }


    private void findPurchase() {
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

    private void showSplashScreen() {
        splashScreen = new JWindow();
        JLabel splashLabel = new JLabel();

        // loading image
        ImageIcon imageIcon = new ImageIcon("resources/img.png");
        int imageWidth = imageIcon.getIconWidth();
        int imageHeight = imageIcon.getIconHeight();

        // icon with initial empty state
        BufferedImage emptyImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        splashLabel.setIcon(new ImageIcon(emptyImage));

        splashScreen.getContentPane().add(splashLabel, BorderLayout.CENTER);
        splashScreen.setSize(512, 512);
        splashScreen.setLocationRelativeTo(null);
        splashScreen.setVisible(true);
        
        int timerDelay = 15;
        int steps = imageWidth;
        splashTimer(timerDelay, imageWidth, steps, imageHeight, imageIcon, splashLabel);
    }

    private void splashTimer(int timerDelay, int imageWidth, int steps, int imageHeight,
                        ImageIcon imageIcon, JLabel splashLabel) {
        Timer timer = new Timer(timerDelay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Calculate the width of the portion to reveal
                int visibleWidth = (step * imageWidth) / steps;

                // Create an image with the visible portion
                BufferedImage visibleImage = new BufferedImage(visibleWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
                Graphics g = visibleImage.getGraphics();
                g.drawImage(imageIcon.getImage(), 0, 0, visibleWidth, imageHeight, null);

                // Update the label with the visible image
                splashLabel.setIcon(new ImageIcon(visibleImage));

                // Increment the step
                step++;

                // Stop the timer when the image is fully revealed
                if (step >= steps) {
                    ((Timer) e.getSource()).stop();
                }
            }
        });

        // Start the timer
        timer.setInitialDelay(0);
        timer.start();
    }


    private void closeSplashScreen() {
        splashScreen.dispose();
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
            // parsing inputs
            int transactionId = Integer.parseInt(transactionIdInput);
            int dayOfPurchase = Integer.parseInt(dayOfPurchaseInput);
            int transactionAmount = Integer.parseInt(transactionAmountInput);

            // transactionID validation
            if (!(listOfPurchases.viewSpecificPurchase(transactionId) == null)) {
                JOptionPane.showMessageDialog(this, "Transaction ID must be unique. Please enter a unique integer.");
                return;
            }
            ArrayList<String> itemsBought = new ArrayList<>(Arrays.asList(itemsBoughtInput.split(",")));
            Purchase newPurchase = new Purchase(transactionId, customerName,
                    dayOfPurchase,itemsBought, transactionAmount);
            listOfPurchases.addPurchase(newPurchase);
            JOptionPane.showMessageDialog(this, "Purchase added successfully.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Invalid input. Please enter valid data types for Transaction ID, "
                            + "Day of Purchase, and Transaction Amount.");
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
            JOptionPane.showMessageDialog(this, "Error: Unable to save program.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadProgram() {
        try {
            jsonReader = new JsonReader("program_data.json");
            listOfPurchases = jsonReader.read();
            JOptionPane.showMessageDialog(this, "Program loaded successfully!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error: Unable to load program.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    // Method to handle removing a purchase
    private void removePurchase() {
        boolean removed;
        String transactionIdInput = JOptionPane.showInputDialog("Enter Transaction ID to Remove:");
        if (transactionIdInput == null || transactionIdInput.trim().isEmpty()) {
            return;
        }

        // Parse the input as an integer
        int transactionIdToRemove;
        try {
            transactionIdToRemove = Integer.parseInt(transactionIdInput);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Invalid input. Please enter a valid integer for Transaction ID.");
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
        removeOutput(removed, transactionIdToRemove);
    }

    private void removeOutput(boolean removed, int transactionIdToRemove) {
        if (removed) {
            JOptionPane.showMessageDialog(this,
                    "Purchase with Transaction ID " + transactionIdToRemove + " removed successfully.");
        } else {
            JOptionPane.showMessageDialog(this,
                    "No purchase found with Transaction ID " + transactionIdToRemove + ".");
        }
    }

    // Enhanced loading screen with a progress bar
    private void showLoadingScreen() {
        // Create a progress bar
        int duration = 2000;
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


        loadingTimer(duration);
    }

    private void loadingTimer(int duration) {
        int timerDelay = duration / 100; // update 1% of the duration
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