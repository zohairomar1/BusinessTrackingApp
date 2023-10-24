package persistance;

import model.ListOfPurchases;
import model.Purchase;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            ListOfPurchases lop = new ListOfPurchases(30);
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            ListOfPurchases lop = new ListOfPurchases(200);
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyListOfPurchases.json");
            writer.open();
            writer.write(lop);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyListOfPurchases.json");
            lop = reader.read();
            assertEquals(200, lop.getRevenueGoal());
            assertEquals(0, lop.viewListOfPurchases().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralListOfPurchases() {
        try {
            ListOfPurchases lop = new ListOfPurchases(200);
            ArrayList<String> loib = new ArrayList<>();
            loib.add("bread");
            loib.add("greens");
            lop.addPurchase((new Purchase(105,"bro",4,loib,50)));

            ArrayList<String> loib2 = new ArrayList<>();
            loib2.add("shoes");
            lop.addPurchase(new Purchase(55,"bro2",7,loib2,80));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralListOfPurchases.json");

            writer.open();
            writer.write(lop);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyListOfPurchases.json");
            lop = reader.read();
            assertEquals(200, lop.getRevenueGoal());
//            List<Purchase> purchases = lop.getPurchases();
//            assertEquals(2,purchases.size());
//            checkPurchase(105,"bro",4, loib,50,purchases.get(0));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
