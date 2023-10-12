package model;

import java.util.ArrayList;
import java.util.List;

public class ListOfPurchases {

    List<Purchase> listOfPurchases = new ArrayList<>();
    double revGoal;

    public ListOfPurchases() {

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

    public boolean removePurchase(int transactionID) {
        Purchase getter = this.viewSpecificPurchase(transactionID);
        this.remove(getter);
    }

    public ArrayList<Purchase> filterPurchasesBasedOnTime(int start, int end) {
        ArrayList<Purchase> acc2 = new ArrayList<>();
        for (Purchase p : listOfPurchases) {
            if ((p.getTimeOfPurchase() >= start) && (p.getTimeOfPurchase() <= end)) {
                acc2.add(p);
            }
        }
        return acc2;
    }

    public ArrayList<Purchase> filterPurchasesBasedOnAmount(int amount) {
        ArrayList<Purchase> acc3 = new ArrayList<>();
        for (Purchase p : listOfPurchases) {
            if (p.getTimeOfPurchase() >= amount) {
                acc3.add(p);
            }
        }
        return acc3;
    }

    public void setRevenueGoal(int revenueGoal) {
        this.revGoal = revenueGoal;
    }

    public double calculateRevenueProgress() {
        int spending = this.calculateTotalSpending();
        return ((spending / revGoal)*100);
    }

    public void calculateAverageTransactionsRequiredToReachRevGoal() {
        double remainingRevenue =
    }

    public double calculateRevenue() {
        double acc4 = 0;
        for (Purchase p : listOfPurchases) {
            acc4.add(p);
        }
        return acc4;
    }

    public int calculateAverageTransactionSpend() {
        int size = this.size();
        int totalSpending = this.calculateTotalSpending();
        return (totalSpending / size);
    }

}
