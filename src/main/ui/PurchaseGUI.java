package ui;

import model.Event;
import model.EventLog;
import model.ListOfPurchases;
import model.Purchase;
import persistance.JsonReader;
import persistance.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

// Class representing the Purchase GUI interface
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
    int step = 1;

    // Sets up loading screen, initializing list of purchases shows the loading and main screen
    public PurchaseGUI() {
        showLoadingScreen();
        initializeListOfPurchases();
        showSplashScreen();
        showMainFrame();

        // examples for TA
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


    // EFFECTS: loads up the mainframe of the GUI program
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

    // EFFECTS: shows the revenue goal at the bottom of GUI program
    private void revenueLabel(JLabel revenueGoalLabel) {
        add(revenueGoalLabel, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null); // centering
        setVisible(true);
    }

    // EFFECTS: sets up buttons
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

    // EFFECTS: help set up buttons
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

    // EFFECTS: help set up other buttons
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

    // EFFECTS: sets up the finding purchase button
    private void findButtonAlone(JButton findPurchaseButton) {
        findPurchaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findPurchase();
            }
        });
    }

    // EFFECTS: sets up save and load, calculate revenue buttons
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


    // EFFECTS: creates a panel for buttons with vertical alignment
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

    // EFFECTS: sets up the mainframe
    private void setUpMainFrame() {
        setTitle("Purchase Manager");
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitProgram();
            }
        });
        setLayout(new BorderLayout());
    }

    private void exitProgram() {
        printLog(EventLog.getInstance());
        System.exit(0);
    }

    public static void printLog(EventLog el) {
        for (Event e : el) {
            System.out.println(e.toString());
            System.out.println("\n");
        }
    }

    // EFFECTS: shows the purchases filtered based on parameter days given on user input
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

    // EFFECTS: shows the purchases filtered based on parameter amounts given on user input
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

    // EFFECTS: shows the calculated average transactions required to reach the revenue goal
    private void calcAvgTransToRevGoal() {
        float averageTransactionsToReachGoal = listOfPurchases.calculateAverageTransactionsRequiredToReachRevGoal();

        JOptionPane.showMessageDialog(this,
                "Average Transactions to Reach Revenue Goal: " + averageTransactionsToReachGoal,
                "Average Transactions to Reach Revenue Goal",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // EFFECTS: shows the calculated revenue of the list of purchases
    private void calculateRevenue() {
        int totalRevenue = listOfPurchases.calculateRevenue();

        JOptionPane.showMessageDialog(this,
                "Total Revenue: $" + totalRevenue,
                "Revenue Calculation",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // EFFECTS: shows the calculated average transaction amount of the list of purchases
    private void calcAvgTrans() {
        float averageTransactionAmount = listOfPurchases.calculateAverageTransactionSpend();

        JOptionPane.showMessageDialog(this,
                "Average Transaction Amount: $" + averageTransactionAmount,
                "Average Transaction Amount",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // EFFECTS: shows the purchase if found based on transaction id user input
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

    // EFFECTS: shows the splash loading screen to user
    private void showSplashScreen() {
        splashScreen = new JWindow();
        JLabel splashLabel = new JLabel();

        // load up image
        ImageIcon imageIcon = new ImageIcon("resources/img.png");
        int imageWidth = imageIcon.getIconWidth();
        int imageHeight = imageIcon.getIconHeight();

        // make icon with initial empty state
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

    // EFFECTS: helper method to set up splash screen timer
    private void splashTimer(int timerDelay, int imageWidth, int steps, int imageHeight,
                        ImageIcon imageIcon, JLabel splashLabel) {
        Timer timer = new Timer(timerDelay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // width calc of portion to reveal
                int visibleWidth = (step * imageWidth) / steps;

                // image create with visible portion
                BufferedImage visibleImage = new BufferedImage(visibleWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
                Graphics g = visibleImage.getGraphics();
                g.drawImage(imageIcon.getImage(), 0, 0, visibleWidth, imageHeight, null);

                // update label with visible image
                splashLabel.setIcon(new ImageIcon(visibleImage));

                step++;

                // stop when image is full revealed
                if (step >= steps) {
                    ((Timer) e.getSource()).stop();
                }
            }
        });

        timer.setInitialDelay(0);
        timer.start();
    }

    // EFFECTS: display all purchases in a pop-up
    private void displayAllPurchases() {
        if (listOfPurchases.viewListOfPurchases().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No purchases to display.");
            return;
        }

        StringBuilder purchasesText = new StringBuilder();
        for (Purchase purchase : listOfPurchases.viewListOfPurchases()) {
            purchasesText.append(purchase.toString()).append("\n");
        }

        JTextArea textArea = new JTextArea(purchasesText.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JOptionPane.showMessageDialog(this, scrollPane, "All Purchases", JOptionPane.PLAIN_MESSAGE);
    }

    // EFFECTS: asks user input for revenue goal and initializes list of purchases
    private void initializeListOfPurchases() {
        String revenueGoalString = JOptionPane.showInputDialog("Enter Revenue Goal:");
        int revenueGoal = 0;

        revenueGoal = Integer.parseInt(revenueGoalString);
        listOfPurchases = new ListOfPurchases(revenueGoal);
    }

    // EFFECTS: adding a purchase to lop based on user input
    private void addPurchase() {
        String transactionIdInput = JOptionPane.showInputDialog("Enter Transaction ID:");
        String customerName = JOptionPane.showInputDialog("Enter Customer Name:");
        String dayOfPurchaseInput = JOptionPane.showInputDialog("Enter Day of Purchase:");
        String itemsBoughtInput = JOptionPane.showInputDialog("Enter Items Bought (comma-separated):");
        String transactionAmountInput = JOptionPane.showInputDialog("Enter Transaction Amount:");

        try {
            int transactionId = Integer.parseInt(transactionIdInput);
            int dayOfPurchase = Integer.parseInt(dayOfPurchaseInput);
            int transactionAmount = Integer.parseInt(transactionAmountInput);

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

    // EFFECTS: saves the list of purchases
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

    // EFFECTS: loads the list of purchases
    private void loadProgram() {
        try {
            jsonReader = new JsonReader("program_data.json");
            listOfPurchases = jsonReader.read();
            JOptionPane.showMessageDialog(this, "Program loaded successfully!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error: Unable to load program.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    // EFFECTS: gets transaction id to remove purchase
    private void removePurchase() {
        boolean removed;
        String transactionIdInput = JOptionPane.showInputDialog("Enter Transaction ID to Remove:");
        if (transactionIdInput == null || transactionIdInput.trim().isEmpty()) {
            return;
        }

        int transactionIdToRemove;
        try {
            transactionIdToRemove = Integer.parseInt(transactionIdInput);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Invalid input. Please enter a valid integer for Transaction ID.");
            return;
        }

        if (listOfPurchases.viewSpecificPurchase(transactionIdToRemove) == null) {
            removed = false;
        } else {
            listOfPurchases.removePurchase(transactionIdToRemove);
            removed = true;
        }

        removeOutput(removed, transactionIdToRemove);
    }

    // EFFECTS: outputs the purchase removed, otherwise
    private void removeOutput(boolean removed, int transactionIdToRemove) {
        if (removed) {
            JOptionPane.showMessageDialog(this,
                    "Purchase with Transaction ID " + transactionIdToRemove + " removed successfully.");
        } else {
            JOptionPane.showMessageDialog(this,
                    "No purchase found with Transaction ID " + transactionIdToRemove + ".");
        }
    }

    // EFFECTS: shows the loading screen to the user
    private void showLoadingScreen() {
        int duration = 2000;
        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);


        loadingDialog = new JDialog(this, "Loading", true);
        loadingDialog.setLayout(new BorderLayout());
        loadingDialog.add(new JLabel("Your personal purchase manager is loading..."), BorderLayout.NORTH);
        loadingDialog.add(progressBar, BorderLayout.CENTER);
        loadingDialog.setSize(1000, 100);
        loadingDialog.setLocationRelativeTo(this);
        loadingDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);


        loadingTimer(duration);
    }

    // EFFECTS: sets up the timer for loading screen for specific duration
    private void loadingTimer(int duration) {
        int timerDelay = duration / 100;
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