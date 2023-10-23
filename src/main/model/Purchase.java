package model;


import org.json.JSONObject;
import persistance.Writable;
import java.util.ArrayList;

// Represents a business' inputted purchase, having its unique transaction ID which has corresponding details.
public class Purchase implements Writable {
    private final String customerName; // stores customer's name of Purchase
    private final int dayOfPurchase; // stores the day of Purchase
    private final int transactionID; // stores the transactionID of Purchase
    private final ArrayList<String> itemsBought; // stores a list of items bought in Purchase
    private final int transactionAmount; // stores the amount of money spent in Purchase

    /*
     * REQUIRES: transactionID is a unique int value, not previously existing in listOfPurchases
     * EFFECTS: each information (e.g. transactionID) is set to the corresponding parameter (e.g. int transactionID)
     * which is based on user-input.
     */

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

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("transactionID", transactionID);
        json.put("customerName", customerName);
        json.put("dayOfPurchase", dayOfPurchase);
        json.put("itemsBought", itemsBought);
        json.put("transactionAmount", transactionAmount);
        return json;
    }
}
