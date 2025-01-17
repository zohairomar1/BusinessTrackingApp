package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ListOfPurchasesTest {
    private ListOfPurchases testListOfPurchases;
    private Purchase testPurchase;
    private Purchase testPurchase2;
    private Purchase testPurchase3;
    private Purchase testPurchase4;
    private Purchase testPurchase5;
    private Purchase testPurchase6;

    @BeforeEach
    void runBefore() {
        testListOfPurchases = new ListOfPurchases(150);
        testPurchase = new Purchase(1,"zohair",2,new ArrayList<>(), 10);
        testPurchase2 = new Purchase(2,"gregor",3,new ArrayList<>(), 20);
        testPurchase3 = new Purchase(33,"paul",5,new ArrayList<>(), 30);
        testPurchase4 = new Purchase(44,"drake",7,new ArrayList<>(), 40);
        testPurchase5 = new Purchase(55,"yeat",9,new ArrayList<>(), 20);
        testPurchase6 = new Purchase(66,"travis",10,new ArrayList<>(), 17);
    }

    @Test
    void testConstructors() {
        assertEquals(150 ,testListOfPurchases.getRevenueGoal());
        assertEquals(10, testPurchase.getTransactionAmount());
        assertEquals(1, testPurchase.getTransactionID());
        assertEquals("zohair", testPurchase.getCustomerName());
        assertEquals(2, testPurchase.getDayOfPurchase());
        assertTrue(testPurchase.getItemsBought().isEmpty());
        testPurchase.getItemsBought().add("eggs");
        assertFalse(testPurchase.getItemsBought().isEmpty());

    }

    @Test
    void testToString() {
        testListOfPurchases.addPurchase(testPurchase);
        testListOfPurchases.addPurchase(testPurchase2);

        assertEquals((testPurchase.toString() + testPurchase2.toString()),testListOfPurchases.toString());
    }

//    @Test
//    void listTest() {
//        ArrayList<Purchase> loppp = new ArrayList<Purchase>();
//
//        loppp.add(testPurchase);
//        loppp.add(testPurchase2);
//
//        assertEquals((testPurchase.toString() + testPurchase2.toString()), testListOfPurchases.listToString(loppp));
//    }

    @Test
    void testSingleAddPurchase() {
        testListOfPurchases.addPurchase(testPurchase);
        assertFalse(testListOfPurchases.listOfPurchases.isEmpty());
        assertEquals(1, testListOfPurchases.listOfPurchases.size());
        assertEquals("zohair",testListOfPurchases.viewSpecificPurchase(1).getCustomerName());
        assertEquals(10,testListOfPurchases.viewSpecificPurchase(1).getTransactionAmount());

    }

    @Test
    void testDoubleAddPurchase() {
        testListOfPurchases.addPurchase(testPurchase);
        testListOfPurchases.addPurchase(testPurchase2);
        assertFalse(testListOfPurchases.listOfPurchases.isEmpty());
        assertEquals(2, testListOfPurchases.listOfPurchases.size());
        assertEquals("zohair",testListOfPurchases.viewSpecificPurchase(1).getCustomerName());
        assertEquals("gregor",testListOfPurchases.viewSpecificPurchase(2).getCustomerName());
        assertEquals(20,testListOfPurchases.viewSpecificPurchase(2).getTransactionAmount());
    }

    @Test
    void testViewListOfMultiplePurchases() {
        testListOfPurchases.addPurchase(testPurchase);
        testListOfPurchases.addPurchase(testPurchase2);
        ArrayList<Purchase> returnedList = testListOfPurchases.viewListOfPurchases();
        assertFalse(returnedList.isEmpty());
        assertEquals(2,returnedList.size());
        assertEquals(testPurchase,returnedList.get(0));
        assertEquals(testPurchase2,returnedList.get(1));
    }

    @Test
    void testViewListOfSinglePurchases() {
        testListOfPurchases.addPurchase(testPurchase);
        ArrayList<Purchase> returnedList = testListOfPurchases.viewListOfPurchases();
        assertFalse(returnedList.isEmpty());
        assertEquals(1,returnedList.size());
        assertEquals(testPurchase,returnedList.get(0));
    }

    @Test
    void testViewListOfNoPurchases() {
        ArrayList<Purchase> returnedList = testListOfPurchases.viewListOfPurchases();
        assertTrue(returnedList.isEmpty());
    }

    @Test
    void testViewSpecificPurchaseThatExists() {
        testListOfPurchases.addPurchase(testPurchase);
        testListOfPurchases.addPurchase(testPurchase2);
        Purchase returnedPurchase = testListOfPurchases.viewSpecificPurchase(1);
        assertEquals("zohair",returnedPurchase.getCustomerName());
        Purchase returnedPurchase2 = testListOfPurchases.viewSpecificPurchase(2);
        assertEquals("gregor",returnedPurchase2.getCustomerName());
        assertEquals(10, returnedPurchase.getTransactionAmount());
        assertEquals(20, returnedPurchase2.getTransactionAmount());
    }

    @Test
    void testViewSpecificPurchaseThatDoesntExist() {
        testListOfPurchases.addPurchase(testPurchase);
        Purchase returnedPurchase = testListOfPurchases.viewSpecificPurchase(2);
        assertNull(returnedPurchase);
    }

    @Test
    void testRemovePurchaseOnceAndTwice() {
        testListOfPurchases.addPurchase(testPurchase);
        testListOfPurchases.addPurchase(testPurchase2);
        testListOfPurchases.removePurchase(1);
        assertEquals(1, testListOfPurchases.listOfPurchases.size());
        Purchase returnedPurchase = testListOfPurchases.viewSpecificPurchase(2);
        assertEquals("gregor",returnedPurchase.getCustomerName());
        testListOfPurchases.removePurchase(2);
        assertTrue(testListOfPurchases.listOfPurchases.isEmpty());
    }

    @Test
    void testFilterMultiplePurchasesBasedOnDay() {
        testListOfPurchases.addPurchase(testPurchase);
        testListOfPurchases.addPurchase(testPurchase2);
        testListOfPurchases.addPurchase(testPurchase3);
        testListOfPurchases.addPurchase(testPurchase4);
        ArrayList<Purchase> filteredList = testListOfPurchases.filterPurchasesBasedOnDay(2,7);
        assertEquals(4,filteredList.size());
        ArrayList<Purchase> filteredList2 = testListOfPurchases.filterPurchasesBasedOnDay(3,6);
        assertEquals(2,filteredList2.size());
        ArrayList<Purchase> filteredList3 = testListOfPurchases.filterPurchasesBasedOnDay(1,2);
        assertEquals(1,filteredList3.size());
        ArrayList<Purchase> filteredList4 = testListOfPurchases.filterPurchasesBasedOnDay(8,9);
        assertEquals(0,filteredList4.size());
        ArrayList<Purchase> filteredList5 = testListOfPurchases.filterPurchasesBasedOnDay(4,6);
        assertEquals(1,filteredList5.size());
    }

    @Test
    void testFilterAPurchaseBasedOnDay() {
        testListOfPurchases.addPurchase(testPurchase2);
        ArrayList<Purchase> filteredList = testListOfPurchases.filterPurchasesBasedOnDay(1,2);
        assertEquals(0,filteredList.size());
        ArrayList<Purchase> filteredList2 = testListOfPurchases.filterPurchasesBasedOnDay(2,3);
        assertEquals(1,filteredList2.size());
        ArrayList<Purchase> filteredList3 = testListOfPurchases.filterPurchasesBasedOnDay(3,4);
        assertEquals(1,filteredList3.size());
        ArrayList<Purchase> filteredList4 = testListOfPurchases.filterPurchasesBasedOnDay(4,5);
        assertEquals(0,filteredList4.size());
    }

    @Test
    void testFilterSingleAndMultiplePurchasesBasedOnAmount() {
        testListOfPurchases.addPurchase(testPurchase);
        ArrayList<Purchase> filteredSingle = testListOfPurchases.filterPurchasesBasedOnAmount(9,10);
        ArrayList<Purchase> filteredSingle2 = testListOfPurchases.filterPurchasesBasedOnAmount(10,11);
        ArrayList<Purchase> filteredSingle3 = testListOfPurchases.filterPurchasesBasedOnAmount(12,13);
        assertEquals(1,filteredSingle.size());
        assertEquals(1,filteredSingle2.size());
        assertEquals(0,filteredSingle3.size());

        testListOfPurchases.addPurchase(testPurchase2);
        testListOfPurchases.addPurchase(testPurchase3);
        testListOfPurchases.addPurchase(testPurchase4);
        ArrayList<Purchase> filteredList = testListOfPurchases.filterPurchasesBasedOnAmount(10,40);
        assertEquals(4,filteredList.size());
        testListOfPurchases.addPurchase(testPurchase5);
        ArrayList<Purchase> filteredList2 = testListOfPurchases.filterPurchasesBasedOnAmount(10,40);
        assertEquals(5,filteredList2.size());
        testListOfPurchases.removePurchase(55);

        ArrayList<Purchase> filteredList3 = testListOfPurchases.filterPurchasesBasedOnAmount(10,25);
        assertEquals(2,filteredList3.size());

        ArrayList<Purchase> filteredList4 = testListOfPurchases.filterPurchasesBasedOnAmount(19,40);
        assertEquals(3,filteredList4.size());

        ArrayList<Purchase> filteredList5 = testListOfPurchases.filterPurchasesBasedOnAmount(20,21);
        assertEquals(1,filteredList5.size());

        ArrayList<Purchase> filteredList6 = testListOfPurchases.filterPurchasesBasedOnAmount(39,41);
        assertEquals(1,filteredList6.size());

    }
    @Test
    void testCalculateRevenueEmpty() {
        assertEquals(0,testListOfPurchases.calculateRevenue());
    }

    @Test
    void testCalculateRevenueSingleAndMultiplePurchases() {
        testListOfPurchases.addPurchase(testPurchase);
        assertEquals(10,testListOfPurchases.calculateRevenue());
        testListOfPurchases.addPurchase(testPurchase2);
        assertEquals(30,testListOfPurchases.calculateRevenue());
        testListOfPurchases.addPurchase(testPurchase3);
        assertEquals(60,testListOfPurchases.calculateRevenue());
        testListOfPurchases.addPurchase(testPurchase4);
        assertEquals(100,testListOfPurchases.calculateRevenue());
    }

    @Test
    void testCalculateRevenueSomeProgress() {
        testListOfPurchases.addPurchase(testPurchase);
        testListOfPurchases.addPurchase(testPurchase2);
        testListOfPurchases.addPurchase(testPurchase3);
        testListOfPurchases.addPurchase(testPurchase4);
        float revenueProgress = testListOfPurchases.calculateRevenueProgress();
        assertEquals(((float) 2/3*100),revenueProgress);
    }

    @Test
    void testCalculateRevenueNoProgress() {
        double revenueProgress = testListOfPurchases.calculateRevenueProgress();
        assertEquals(0,revenueProgress);
    }

    @Test
    void testCalculateRevenueProgressAlmostDone() {
        testListOfPurchases.addPurchase(testPurchase);
        testListOfPurchases.addPurchase(testPurchase2);
        testListOfPurchases.setRevGoal(31);
        float revenueProgress = testListOfPurchases.calculateRevenueProgress();
        assertEquals(((float) 30/31*100),revenueProgress);
    }

    @Test
    void testCalculateRevenueProgressDone() {
        testListOfPurchases.addPurchase(testPurchase);
        testListOfPurchases.addPurchase(testPurchase2);
        testListOfPurchases.setRevGoal(30);
        double revenueProgress = testListOfPurchases.calculateRevenueProgress();
        assertEquals(100,revenueProgress);
    }

    @Test
    void testCalculateAverageTransactionSpendOnePurchase() {
        testListOfPurchases.addPurchase(testPurchase);
        float returnAvgTrans = testListOfPurchases.calculateAverageTransactionSpend();
        assertEquals(10,returnAvgTrans);
    }

    @Test
    void testCalculateAverageTransactionSpendMultiplePurchasesFloat() {
        testListOfPurchases.addPurchase(testPurchase);
        testListOfPurchases.addPurchase(testPurchase2);
        testListOfPurchases.addPurchase(testPurchase3);
        testListOfPurchases.addPurchase(testPurchase4);
        testListOfPurchases.addPurchase(testPurchase5);
        testListOfPurchases.addPurchase(testPurchase6);

        float returnAvgTrans = testListOfPurchases.calculateAverageTransactionSpend();
        assertEquals((float) 137/6,returnAvgTrans);
    }

    @Test
    void testCalculateAverageTransactionSpendMultiplePurchasesWholeAnswer() {
        testListOfPurchases.addPurchase(testPurchase);
        testListOfPurchases.addPurchase(testPurchase2);
        testListOfPurchases.addPurchase(testPurchase3);
        testListOfPurchases.addPurchase(testPurchase4);
        testListOfPurchases.addPurchase(testPurchase5);

        float returnAvgTrans = testListOfPurchases.calculateAverageTransactionSpend();
        assertEquals(24,returnAvgTrans);
    }

    @Test
    void testCalculateAverageTransactionsRequiredToReachRevGoal() {
        testListOfPurchases.addPurchase(testPurchase);
        testListOfPurchases.addPurchase(testPurchase2);
        testListOfPurchases.addPurchase(testPurchase3);
        testListOfPurchases.addPurchase(testPurchase4);
        testListOfPurchases.addPurchase(testPurchase5);

        float returnAvgTransReq = testListOfPurchases.calculateAverageTransactionsRequiredToReachRevGoal();
        assertEquals(1.25,returnAvgTransReq);
    }

    @Test
    void testCalculateAverageTransactionsRequiredToReachRevGoalOneTransaction() {
        testListOfPurchases.addPurchase(testPurchase);

        float returnAvgTransReq = testListOfPurchases.calculateAverageTransactionsRequiredToReachRevGoal();
        assertEquals(14,returnAvgTransReq);
    }

    @Test
    void testCalculateAverageTransactionsRequiredToReachRevGoalOne() {
        testListOfPurchases.addPurchase(testPurchase);
        testListOfPurchases.addPurchase(testPurchase2);
        testListOfPurchases.addPurchase(testPurchase3);
        testListOfPurchases.addPurchase(testPurchase4);
        testListOfPurchases.addPurchase(testPurchase5);
        testListOfPurchases.setRevGoal(144);

        float returnAvgTransReq = testListOfPurchases.calculateAverageTransactionsRequiredToReachRevGoal();
        assertEquals(1,returnAvgTransReq);
    }

    @Test
    void testRemovePurchaseNull() {
        testListOfPurchases.addPurchase(testPurchase);
        testListOfPurchases.addPurchase(testPurchase2);
        testListOfPurchases.addPurchase(testPurchase3);
        testListOfPurchases.removePurchase(999);

        assertEquals(3,testListOfPurchases.listOfPurchases.size());
        assertEquals(testPurchase,testListOfPurchases.listOfPurchases.get(0));
        assertEquals(testPurchase2,testListOfPurchases.listOfPurchases.get(1));
        assertEquals(testPurchase3,testListOfPurchases.listOfPurchases.get(2));
    }
}