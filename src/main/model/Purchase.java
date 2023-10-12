package model;


import java.util.ArrayList;

public class Purchase {
    private String customerName;
    private int timeOfPurchase;
    private int transactionID;
    private ArrayList<String> itemsBought;
    private int transactionAmount;

    public Purchase(int transactionID, String customerName, int timeOfPurchase, ArrayList<String> itemsBought, int transactionAmount) {
        this.transactionID = transactionID;
        this.customerName = customerName;
        this.timeOfPurchase = timeOfPurchase;
        this.itemsBought = itemsBought;
        this.transactionAmount = transactionAmount;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getTimeOfPurchase() {
        return timeOfPurchase;
    }

    public ArrayList<String> getItemsBought() {
        return itemsBought;
    }

    public int getTransactionAmount() {
        return transactionAmount;
    }

}
