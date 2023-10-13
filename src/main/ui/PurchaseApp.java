package ui;

import model.ListOfPurchases;
import model.Purchase;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class PurchaseApp {
    private Scanner obj;
    private ListOfPurchases listOfPurchases;
    private Purchase purchase;
    private static final DecimalFormat decfor = new DecimalFormat("0.00");

    // EFFECTS: runs the Purchase application
    public PurchaseApp() {
        runPurchase();
    }

    // MODIFIES: this
    // EFFECTS: processes the user input
    public void runPurchase() {
        boolean keepGoing = true;
        String com = null;

        init();
        while (keepGoing) {
            displayMenu();
            com = obj.next();
            com = com.toLowerCase();

            if (com.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(com);
            }
        }

        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            doAddPurchase();
        } else if (command.equals("b")) {
            doViewPurchase();
        } else if (command.equals("c")) {
            doRemovePurchase();
        } else if (command.equals("d")) {
            doCalculateRevenue();
        } else if (command.equals("e")) {
            doCalculateRevenueProgress();
        } else if (command.equals("f")) {
            doAvgTransaction();
        } else if (command.equals("g")) {
            doAvgTransRequiredForRevGoal();
        } else if (command.equals("h")) {
            doFilterBasedOnDay();
        } else if (command.equals("i")) {
            doFilterBasedOnAmount();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes listOfAccounts and Account

    private void init() {
        obj = new Scanner(System.in);
        System.out.print("Please enter a revenue goal: ");
        int revGoals = obj.nextInt();
        obj.useDelimiter("\n");
        listOfPurchases = new ListOfPurchases(revGoals);
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add purchase");
        // view list of purhcase
        System.out.println("\tb -> view purchase");
        System.out.println("\tc -> remove purchase");
        System.out.println("\td -> calculate revenue");
        System.out.println("\te -> calculate revenue progress");
        System.out.println("\tf -> calculate average transaction amount");
        System.out.println("\tg -> calculate how many transactions on average to reach revenue goal");
        System.out.println("\th -> filter based on day");
        System.out.println("\ti -> filter based on amount");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: adds a purchase to the listOfPurchases


    private void doAddPurchase() {
        int filteredTransaction = transChecker();

        System.out.print("Enter the customer name: ");
        String name = obj.next();

        System.out.print("Enter the day of the month of the purchase: ");
        int day = obj.nextInt();

        System.out.print("Enter the items bought separated by commas (e.g. i1, i2, i3): ");
        String items = obj.next();
        String[] individualItems = items.split("[,]", 0);

        ArrayList<String> itemsList = new ArrayList<>();
        for (String item: individualItems) {
            itemsList.add(item);
        }

        System.out.print("Enter the transaction amount: ");
        int transactionAmount = obj.nextInt();

        Purchase newPurchase = new Purchase(filteredTransaction,name,day,itemsList,transactionAmount);
        listOfPurchases.addPurchase(newPurchase);
        System.out.println("Added this purchase to the list of purchases");
    }

    // MODIFIES: this
    // EFFECTS: views specific purchase based on transaction ID

    private void doViewPurchase() {
        System.out.print("Enter the transaction ID to view the purchase: ");
        int enteredTransactionID = obj.nextInt();

        Purchase specificPurchase = listOfPurchases.viewSpecificPurchase(enteredTransactionID);
        if (!(specificPurchase == null)) {
            printOutPurchase(specificPurchase);
        } else {
            System.out.print("Sorry, the transaction you are looking for does not exist!");
        }

    }

    // MODIFIES: this
    // EFFECTS: removes a purchase based on transaction id

    private void doRemovePurchase() {
        System.out.print("Enter the transaction ID of the purchase you would like to remove: ");
        int removingTransactionID = obj.nextInt();

        Purchase removingPurchase = listOfPurchases.viewSpecificPurchase(removingTransactionID);
        if (removingPurchase != null) {
            System.out.print("The following purchase has been removed! \n");
            printOutPurchase(removingPurchase);
            listOfPurchases.removePurchase(removingTransactionID);
        } else {
            System.out.print("Sorry, the transaction you are looking to remove does not exist!");
        }
    }

    // MODIFIES: this
    // EFFECTS: calculated total revenue

    private void doCalculateRevenue() {
        int calculatedRevenue = listOfPurchases.calculateRevenue();
        System.out.print("The total revenue is: " + calculatedRevenue);
    }

    // MODIFIES: this
    // EFFECTS: calculates % of revGoal completed

    private void doCalculateRevenueProgress() {
        float calculatedRevenueProgress = listOfPurchases.calculateRevenueProgress();
        System.out.print("The current revenue progress is: " + decfor.format(calculatedRevenueProgress) + "%");
    }

    // MODIFIES: this
    // EFFECTS: calculates average spent in transactions

    private void doAvgTransaction() {
        float avgTrans = listOfPurchases.calculateAverageTransactionSpend();
        System.out.print("The average spending per transaction is: " + decfor.format(avgTrans));
    }

    // MODIFIES: this
    // EFFECTS: calculates average transactions needed to reach the revenue goal

    private void doAvgTransRequiredForRevGoal() {
        float transReq = listOfPurchases.calculateAverageTransactionsRequiredToReachRevGoal();
        System.out.print("The amount of transactions "
                + "(based on average spending) required to meet the revenue goal is: " + decfor.format(transReq));

    }

    // MODIFIES: this
    // EFFECTS: filters transactions to match time parameters

    private void doFilterBasedOnDay() {

    }

    // MODIFIES: this
    // EFFECTS: filters transactions to match time parameters

    private void doFilterBasedOnAmount() {

    }

    private void printOutPurchase(Purchase p) {
        System.out.println("The purchase has the following details: \n Transaction ID: "
                + p.getTransactionID() + "\n Customer name: " + p.getCustomerName()
                + "\n Day of the month of purchase: "
                + p.getDayOfPurchase() + "\n Items bought: "
                + p.getItemsBought() + "\n Transaction Amount: " + p.getTransactionAmount());
    }

    private int transChecker() {
        System.out.print("Enter the transaction ID: ");
        int transID = obj.nextInt();
        while (listOfPurchases.viewSpecificPurchase(transID) != null) {
            System.out.println("Please enter a unique transaction ID:");
            transID = obj.nextInt();
        }
        if (listOfPurchases.viewSpecificPurchase(transID) == null) {
            return transID;
        }
        return transID;
    }
}
