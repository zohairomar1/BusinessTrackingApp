package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistance.Writable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Represents a List of Purchases, having a revenue goal
public class ListOfPurchases implements Writable {
    List<Purchase> listOfPurchases; // stores the list of Purchase
    int revGoal; // stores the revenue goal


    // REQUIRES: revGoal > 0
    // EFFECTS: list of Purchases is initialized and the revenue goal is set to revGoal
    public ListOfPurchases(int revGoal) {

        listOfPurchases = new ArrayList<>();
        this.revGoal = revGoal;
        EventLog.getInstance().logEvent(new Event("A list of purchases was initialized with a revenue goal"
                + " of " + revGoal));
    }

    // MODIFIES: this
    // EFFECTS: adds a purchase to the list of purchases
    public void addPurchase(Purchase p) {
        listOfPurchases.add(p);
        EventLog.getInstance().logEvent(new Event("The following purchase was added: \n" + p.toString()));
    }

    // REQUIRES: (listOfPurchases.size() > 0)
    // MODIFIES: this
    // EFFECTS: returns the list of Purchases
    public ArrayList<Purchase> viewListOfPurchases() {
        ArrayList<Purchase> acc = new ArrayList<>();
        for (Purchase p : listOfPurchases) {
            acc.add(p);
        }
        EventLog.getInstance().logEvent(new Event("All purchases from from the list were outputted."
                + listOfPurchases.toString()));
        return acc;
    }

    // MODIFIES: this
    // EFFECTS: returns the specific purchase based on transactionID inputted
    public Purchase viewSpecificPurchase(int transactionID) {
        for (Purchase p : listOfPurchases) {
            if (p.getTransactionID() == transactionID) {
                EventLog.getInstance().logEvent(new Event("The specific purchase correlated "
                        + "with transaction ID " + transactionID + " was outputted " + p.toString()));
                return p;
            }
        }
        return null;
    }

    // REQUIRES: listOfPurchases has to contain the transaction ID
    // MODIFIES: this
    // EFFECTS: returns the specific purchase based on transactionID inputted
    public void removePurchase(int transactionID) {
        Purchase getter = this.viewSpecificPurchase(transactionID);
        if (getter != null) {
            EventLog.getInstance().logEvent(new Event("The following purchase was removed from the list: \n"
                    + getter.toString()));
            listOfPurchases.remove(getter);
        }
    }

    // REQUIRES: listOfPurchases is not empty
    // MODIFIES: this
    // EFFECTS: filters the list of purchases based on user input of day
    public ArrayList<Purchase> filterPurchasesBasedOnDay(int start, int end) {
        ArrayList<Purchase> acc2 = new ArrayList<>();
        for (Purchase p : listOfPurchases) {
            if ((p.getDayOfPurchase() >= start) && (p.getDayOfPurchase() <= end)) {
                acc2.add(p);
            }
        }

        String eventAcc2 = "";

        for (Purchase pp: acc2) {
            eventAcc2 += pp.toString();
            eventAcc2 += "\n";
        }
        EventLog.getInstance().logEvent(new Event("The following purchases were filtered between "
                + "starting day " + start + " and ending day " + end + ": \n"  + eventAcc2));
        return acc2;
    }

    // REQUIRES: listOfPurchases is not empty
    // MODIFIES: this
    // EFFECTS: filters the list of purchases based on user input of amount
    public ArrayList<Purchase> filterPurchasesBasedOnAmount(int amount1, int amount2) {
        ArrayList<Purchase> acc3 = new ArrayList<>();
        for (Purchase p : listOfPurchases) {
            if ((p.getTransactionAmount() >= amount1) && (p.getTransactionAmount() <= amount2)) {
                acc3.add(p);
            }
        }

        String eventAcc = "";

        for (Purchase pp: acc3) {
            eventAcc += pp.toString();
            eventAcc += "\n";
        }

        EventLog.getInstance().logEvent(new Event("The following purchases were filtered between "
                + "starting amount " + amount1 + " and ending amount " + amount2 + ": \n"  + eventAcc));
        return acc3;
    }

    public int getRevenueGoal() {
        return revGoal;
    }

    public void setRevGoal(int revGoal) {
        this.revGoal = revGoal;
        EventLog.getInstance().logEvent(new Event("The revenue goal was set to $" + revGoal));
    }

    // REQUIRES: revenue < revenueGoal
    // EFFECTS: returns the revenue progress
    public float calculateRevenueProgress() {
        int spending = this.calculateRevenue();

        EventLog.getInstance().logEvent(new Event("The revenue progress was calculated as: $"
                + (((float) spending / revGoal) * 100)));
        return (((float) spending / revGoal) * 100);
    }

    // REQUIRES: listOfPurchases is not empty
    // EFFECTS: returns the amount on transactions on average required to reach revenue goal
    public float calculateAverageTransactionsRequiredToReachRevGoal() {

        int remainingRevenue = revGoal - this.calculateRevenue();
        float avgTransaction = this.calculateAverageTransactionSpend();
        EventLog.getInstance().logEvent(new Event("The amount of transactions on average required "
                + "to reach revenue goal was calculated as: $"
                + (remainingRevenue / avgTransaction)));
        return (remainingRevenue / avgTransaction);
    }

    // EFFECTS: calculates the revenue of the list of purchases
    public int calculateRevenue() {
        int acc4 = 0;
        for (Purchase p : listOfPurchases) {
            acc4 = acc4 + p.getTransactionAmount();
        }
        EventLog.getInstance().logEvent(new Event("The revenue was calculated as: $"
                + acc4));
        return acc4;
    }

    // EFFECTS: calculate the average amount spent per transaction in list
    public float calculateAverageTransactionSpend() {
        int totalSpending = this.calculateRevenue();
        EventLog.getInstance().logEvent(new Event("The average transaction spending was calculated as: $"
                + (float) totalSpending / (listOfPurchases.size())));
        return (float) totalSpending / (listOfPurchases.size());
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("revGoal", revGoal);
        json.put("listOfPurchases", purchasesToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray purchasesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Purchase p : listOfPurchases) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }

    // EFFECTS: returns list of purchases as string
    @Override
    public String toString() {
        String stringAcc = "";

        for (Purchase p : listOfPurchases) {
            stringAcc += (p.toString());
        }

        return stringAcc;
    }

    public List<Purchase> getPurchases() {
        return Collections.unmodifiableList(listOfPurchases);
    }

}
