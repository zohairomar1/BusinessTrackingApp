package persistance;

import model.Purchase;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {

    protected void checkPurchase(int transactionID, String customerName, int dayOfPurchase,
                                 ArrayList<String> itemsBought,
                                 int transactionAmount, Purchase purchase) {
        assertEquals(transactionID, purchase.getTransactionID());
        assertEquals(customerName, purchase.getCustomerName());
        assertEquals(dayOfPurchase, purchase.getDayOfPurchase());
        assertEquals(itemsBought, purchase.getItemsBought());
        assertEquals(transactionAmount, purchase.getTransactionAmount());
    }
}
