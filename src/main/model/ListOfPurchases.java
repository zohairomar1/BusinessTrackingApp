package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistance.Writable;

import java.util.ArrayList;

// Represents a List of Purchases, having a revenue goal
public class ListOfPurchases implements Writable {
    ArrayList<Purchase> listOfPurchases; // stores the list of Purchase
    int revGoal; // stores the revenue goal


    // REQUIRES: revGoal > 0
    // EFFECTS: list of Purchases is initialized and the revenue goal is set to revGoal
    public ListOfPurchases(int revGoal) {

        listOfPurchases = new ArrayList<>();
        this.revGoal = revGoal;
    }

    // MODIFIES: this
    // EFFECTS: adds a purchase to the list of purchases
    public void addPurchase(Purchase p) {
        listOfPurchases.add(p);
    }

    // REQUIRES: (listOfPurchases.size() > 0)
    // MODIFIES: this
    // EFFECTS: returns the list of Purchases
    public ArrayList<Purchase> viewListOfPurchases() {
        ArrayList<Purchase> acc = new ArrayList<>();
        for (Purchase p : listOfPurchases) {
            acc.add(p);
        }
        return acc;
    }

    // REQUIRES: listOfPurchases has to contain the transaction ID
    // MODIFIES: this
    // EFFECTS: returns the specific purchase based on transactionID inputted
    public Purchase viewSpecificPurchase(int transactionID) {
        for (Purchase p : listOfPurchases) {
            if (p.getTransactionID() == transactionID) {
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
        return acc3;
    }

    public int getRevenueGoal() {
        return revGoal;
    }

    public void setRevGoal(int revGoal) {
        this.revGoal = revGoal;
    }

    // REQUIRES: revenue < revenueGoal
    // EFFECTS: returns the revenue progress
    public float calculateRevenueProgress() {
        int spending = this.calculateRevenue();
        return (((float) spending / revGoal) * 100);
    }

    // REQUIRES: listOfPurchases is not empty
    // EFFECTS: returns the amount on transactions on average required to reach revenue goal
    public float calculateAverageTransactionsRequiredToReachRevGoal() {

        int remainingRevenue = revGoal - this.calculateRevenue();
        float avgTransaction = this.calculateAverageTransactionSpend();
        return (remainingRevenue / avgTransaction);
    }

    // EFFECTS: calculates the revenue of the list of purchases
    public int calculateRevenue() {
        int acc4 = 0;
        for (Purchase p : listOfPurchases) {
            acc4 = acc4 + p.getTransactionAmount();
        }
        return acc4;
    }

    // EFFECTS: calculate the average amount spent per transaction in list
    public float calculateAverageTransactionSpend() {
        int totalSpending = this.calculateRevenue();
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

}
