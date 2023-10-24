package persistance;

import model.ListOfPurchases;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;


public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ListOfPurchases lop = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyLOP() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyListOfPurchases.json");
        try {
            ListOfPurchases lop = reader.read();
            assertEquals(10, lop.getRevenueGoal());
            assertEquals(0, lop.viewListOfPurchases().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralLOP() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralListOfPurchase.json");
        try {
            ListOfPurchases lop = reader.read();
            assertEquals(200, lop.getRevenueGoal());
            assertEquals(2, lop.viewListOfPurchases().size());
            assertEquals(60, lop.calculateAverageTransactionSpend());
            assertEquals((float) 120/200 * 100, lop.calculateRevenueProgress());
            assertTrue(lop.viewSpecificPurchase(3).getItemsBought().contains("white"));
            assertFalse(lop.viewSpecificPurchase(1).getItemsBought().contains("white"));
            assertTrue(lop.viewSpecificPurchase(1).getItemsBought().contains("milk"));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
