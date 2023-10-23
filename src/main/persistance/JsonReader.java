package persistance;

import model.Purchase;
import model.ListOfPurchases;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.json.*;
import java.util.ArrayList;



public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ListOfPurchases read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseListOfPurchases(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines( Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses ListOfPurchases from JSON object and returns it
    private ListOfPurchases parseListOfPurchases(JSONObject jsonObject) {
        int transactionID = jsonObject.getInt("transactionID");
        ListOfPurchases lop = new ListOfPurchases(transactionID);
        addPurchases(lop, jsonObject);
        return lop;
    }

    // MODIFIES: lop
    // EFFECTS: parses purchases from JSON object and adds them to ListOfPurchases
    private void addPurchases(ListOfPurchases lop, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("listOfPurchases");
        for (Object json : jsonArray) {
            JSONObject nextPurchase = (JSONObject) json;
            addPurchase(lop, nextPurchase);
        }
    }

    // MODIFIES: lop
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    private void addPurchase(ListOfPurchases lop, JSONObject jsonObject) {
        int transactionID = jsonObject.getInt("transactionID");
        String customerName  = jsonObject.getString("customerName");
        int dayOfPurchase  = jsonObject.getInt("dayOfPurchase");
        ArrayList<String> itemsBought  = (ArrayList<String>) jsonObject.get("itemsBought");
        int transactionAmount  = jsonObject.getInt("transactionAmount");
        Purchase purchase = new Purchase(transactionID,customerName,dayOfPurchase,itemsBought,transactionAmount);
        lop.addPurchase(purchase);
    }
}
