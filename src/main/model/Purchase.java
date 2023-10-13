package model;


import java.util.ArrayList;

public class Purchase {
    private String customerName;
    private int dayOfPurchase;
    private int transactionID;
    private ArrayList<String> itemsBought;
    private int transactionAmount;

    public Purchase(int transactionID, String customerName, int dayOfPurchase,
                    ArrayList<String> itemsBought, int transactionAmount) {
        this.transactionID = transactionID;
        this.customerName = customerName;
        this.dayOfPurchase = dayOfPurchase;
        this.itemsBought = itemsBought;
        this.transactionAmount = transactionAmount;
    }

    public int getTransactionID() {
        return transactionID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getDayOfPurchase() {
        return dayOfPurchase;
    }

    public ArrayList<String> getItemsBought() {
        return itemsBought;
    }

    public int getTransactionAmount() {
        return transactionAmount;
    }

}
