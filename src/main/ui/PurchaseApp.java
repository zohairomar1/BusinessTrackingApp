package ui;

import model.ListOfPurchases;
import model.Purchase;
import persistance.JsonReader;
import persistance.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

// Purchase application
public class PurchaseApp {
    private Scanner obj;
    private ListOfPurchases listOfPurchases;
    private static final DecimalFormat decfor = new DecimalFormat("0.00");
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/listofpurchases.json";


    // EFFECTS: runs the Purchase application
    public PurchaseApp() throws FileNotFoundException {
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
    @SuppressWarnings({"checkstyle:MethodLength", "checkstyle:SuppressWarnings"})
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
        } else if (command.equals("z")) {
            doViewAllPurchases();
        } else if (command.equals("1")) {
            saveListOfPurchases();
        } else if (command.equals("2")) {
            loadListOfPurchases();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes listOfAccounts
    private void init() {
        obj = new Scanner(System.in);
        int returnValue = revGoalChecker();
        obj.useDelimiter("\n");
        listOfPurchases = new ListOfPurchases(returnValue);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\t1 -> save list of purchases to file");
        System.out.println("\t2 -> load list of purchases from file");
        System.out.println("\tz -> view all purchases in the list");
        System.out.println("\ta -> add purchase");
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
    // EFFECTS: allows to see all purchases in the listOfPurchases
    private void doViewAllPurchases() {
        ArrayList<Purchase> allPurchases = listOfPurchases.viewListOfPurchases();
        ArrayList<Purchase> acc = new ArrayList<>();
        if (! (allPurchases.isEmpty())) {
            for (Purchase p : allPurchases) {
                acc.add(p);
            }
            printOutPurchases(acc);
        } else {
            System.out.print("The list is empty and has no purchases in it.");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a Purchase instance to the listOfPurchases
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
    // EFFECTS: removes a purchase based on transaction ID
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
    // EFFECTS: calculates total revenue
    private void doCalculateRevenue() {
        int calculatedRevenue = listOfPurchases.calculateRevenue();
        System.out.print("The total revenue is: " + calculatedRevenue);
    }

    // REQUIRES: revenue < revenueGoal
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

    // REQUIRES: listOfPurchases is not empty
    // EFFECTS: calculates average transactions needed to reach the revenue goal
    private void doAvgTransRequiredForRevGoal() {
        float transReq = listOfPurchases.calculateAverageTransactionsRequiredToReachRevGoal();
        System.out.print("The amount of transactions "
                + "(based on average spending) required to meet the revenue goal is: " + decfor.format(transReq));
    }

    // REQUIRES: listOfPurchases is not empty
    // EFFECTS: prompts user to input day parameters and filters transactions to match the given parameters
    private void doFilterBasedOnDay() {
        System.out.print("Enter the starting day of the filter:  ");
        int start = obj.nextInt();

        System.out.print("Enter the ending day of the filter:  ");
        int end = obj.nextInt();

        ArrayList<Purchase> filteredList = listOfPurchases.filterPurchasesBasedOnDay(start,end);
        if (!(filteredList.isEmpty())) {
            System.out.print("The following purchases matched your filter: \n");
            printOutPurchases(filteredList);
        } else {
            System.out.println("There are no purchases that match your filter.");
        }

    }

    // REQUIRES: listOfPurchases is not empty
    // EFFECTS: prompts user to input amount parameters and filters transactions to match the given parameters
    private void doFilterBasedOnAmount() {
        System.out.print("Enter the starting amount of the filter:  ");
        int amount1 = obj.nextInt();

        System.out.print("Enter the ending amount of the filter:  ");
        int amount2 = obj.nextInt();

        ArrayList<Purchase> filteredList2 = listOfPurchases.filterPurchasesBasedOnAmount(amount1,amount2);
        if (!(filteredList2.isEmpty())) {
            System.out.print("The following purchases matched your filter: \n");
            printOutPurchases(filteredList2);
        } else {
            System.out.println("There are no purchases that match your filter.");
        }

    }

    // EFFECTS: outputs a single purchase based on its fields
    private void printOutPurchase(Purchase p) {
        System.out.println("\n Transaction ID: "
                + p.getTransactionID() + "\n Customer name: " + p.getCustomerName()
                + "\n Day of the month of purchase: "
                + p.getDayOfPurchase() + "\n Items bought: "
                + p.getItemsBought() + "\n Transaction Amount: " + p.getTransactionAmount());
    }

    // EFFECTS: outputs all purchases in a given listOfPurchases
    private void printOutPurchases(ArrayList<Purchase> listOfPurchases) {
        for (Purchase p : listOfPurchases) {
            printOutPurchase(p);
            System.out.print("\n");
        }
    }

    // EFFECTS: user prompt for entering transaction ID and ensures the transaction ID is unique
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

    // EFFECTS: user prompt for entering revenue goal and ensures it is more than 0
    private int revGoalChecker() {
        System.out.print("Enter your revenue goal: ");
        int revGoal1 = obj.nextInt();
        while (revGoal1 == 0) {
            System.out.println("Please enter a revenue goal which is more than 0: ");
            revGoal1 = obj.nextInt();
        }
        if (revGoal1 > 0) {
            return revGoal1;
        }
        return revGoal1;
    }

    // EFFECTS: saves list of purchases to file
    private void saveListOfPurchases() {
        try {
            jsonWriter.open();
            jsonWriter.write(listOfPurchases);
            jsonWriter.close();
            System.out.println("Saved list of purchases with revenue goal of "
                    + listOfPurchases.getRevenueGoal() + " to " + JSON_STORE);
        } catch (FileNotFoundException fnfe) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads list of purchases from file
    private void loadListOfPurchases() {
        try {
            listOfPurchases = jsonReader.read();
            System.out.println("Loaded list of purchases with revenue goal of "
                    + listOfPurchases.getRevenueGoal() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
