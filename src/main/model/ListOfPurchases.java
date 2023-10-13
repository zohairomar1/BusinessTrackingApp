package model;

import java.util.ArrayList;

public class ListOfPurchases {
    ArrayList<Purchase> listOfPurchases;
    int revGoal;

    public ListOfPurchases(int revGoal) {

        listOfPurchases = new ArrayList<>();
        this.revGoal = revGoal;
    }

    public void addPurchase(Purchase p) {
        listOfPurchases.add(p);
    }

    public ArrayList<Purchase> viewListOfPurchases() {
        ArrayList<Purchase> acc = new ArrayList<>();
        for (Purchase p : listOfPurchases) {
            acc.add(p);
        }
        return acc;
    }

    public Purchase viewSpecificPurchase(int transactionID) {
        for (Purchase p : listOfPurchases) {
            if (p.getTransactionID() == transactionID) {
                return p;
            }
        }
        return null;
    }

    public void removePurchase(int transactionID) {
        Purchase getter = this.viewSpecificPurchase(transactionID);
        if (getter != null) {
            listOfPurchases.remove(getter);
        }
    }

    public ArrayList<Purchase> filterPurchasesBasedOnDay(int start, int end) {
        ArrayList<Purchase> acc2 = new ArrayList<>();
        for (Purchase p : listOfPurchases) {
            if ((p.getDayOfPurchase() >= start) && (p.getDayOfPurchase() <= end)) {
                acc2.add(p);
            }
        }
        return acc2;
    }

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

    public float calculateRevenueProgress() {
        int spending = this.calculateRevenue();
        return (((float) spending / revGoal) * 100);
    }

    public float calculateAverageTransactionsRequiredToReachRevGoal() {

        int remainingRevenue = revGoal - this.calculateRevenue();
        float avgTransaction = this.calculateAverageTransactionSpend();
        return (remainingRevenue / avgTransaction); // how many transactions required on avg to get to rev goal
    }

    public int calculateRevenue() {
        int acc4 = 0;
        for (Purchase p : listOfPurchases) {
            acc4 = acc4 + p.getTransactionAmount();
        }
        return acc4;
    }
// calculate the average amount spent per transaction in list

    public float calculateAverageTransactionSpend() {
        int totalSpending = this.calculateRevenue();
        return (float) totalSpending / (listOfPurchases.size());
    }

}
