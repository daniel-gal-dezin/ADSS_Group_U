package Tests;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import DomainLayer.*;
import org.junit.*;

public class StoreTest {
    public static StoreManager stores=new StoreManager();


    @BeforeAll
    public static void set(){
        try{
            stores.newStore("hadas");
        }
        catch(Exception e){}
    }

    @BeforeEach
    public void setUp() {
        try{
            stores.setStore("hadas");
            stores.addItem("Dairy", "Milk", "Tnuva Milk 3%", 1, 1, "Tnuva", 5, 8, 1000, "01-07-2025");
            stores.updateMinimumAmount("Dairy", "Milk", 1, 3);
        }
        catch(Exception e){}
    }


    @Test
    public void testAddItem1() { //checks if another product already has the same serial number
        assertThrows(Exception.class, ()->{stores.addItem("Dairy", "Cheese", "Gauda Tnuva", 1, 1, "Hadas", 15, 20, 500, "20-07-2024");});
    }

    @Test
    public void testAddItem2() { //checks if the date is written correctly
        assertThrows(Exception.class, ()->{stores.addItem("Dairy", "Cheese", "Gauda Tnuva", 2, 1, "Hadas", 15, 20, 500, "20/07/2024");});
    }

    @Test
    public void testAddItem3() { //checks if adds correct item
        assertDoesNotThrow(()->{stores.addItem("Dairy", "Cheese", "Gauda Tnuva", 2, 1, "Hadas", 15, 20, 500, "20-07-2024");});
        boolean answer=stores.productExists("Dairy", "Cheese", 2);
        Assert.assertEquals(true, answer);
    }

    @Test
    public void testSellItem1(){ //checks if the item exists
        assertThrows(Exception.class, ()->{stores.sellItem("Dairy", "Milk", 1, 2);});
    }

    //fix
    @Test
    public void testSellItem2(){ //checks if sells correct item and the stock is 0
        assertDoesNotThrow(()->{stores.sellItem("Dairy", "Milk", 1, 1);});
        Assert.assertEquals(0, stores.getCategory("Dairy").getSubcatList().get("Milk").getProductList().get(1).getStock());
    }

    @Test
    public void testDamagedItem(){ //checks if deletes correct item when damaged
        assertDoesNotThrow(()->{stores.updateDamagedItem("Dairy", "Milk", 1, 1);});
        Assert.assertEquals(0, stores.getProductStock("Dairy", "Milk", 1));
    }

    @Test
    public void testFailDamagedItem(){ //checks if failed- product does not exist
        assertThrows(Exception.class, () -> {stores.updateDamagedItem("Dairy", "Milk", 4, 3);});
    }

    @Test
    public void testStockWarning(){ //checks if it prints a stock warning when the stock reaches the minimum amount
        assertDoesNotThrow(()->{stores.updateMinimumAmount("Dairy", "Cheese", 2, 2);});
        try{
            assertEquals(1, stores.stockWarning("Dairy", "Cheese", 2));
        }
        catch(Exception e){
            System.out.println("failed");
        }
    }

    @Test
    public void testFailStockWarning(){ //checks if failed- product does not exist
        assertThrows(Exception.class, () -> {stores.stockWarning("Dairy", "Cheese", 5);});
    }

    @Test
    public void testSetDiscount(){
        assertDoesNotThrow(()-> {stores.setDiscount("Dairy", "Milk", 1, 20);});
        try{
            Double newPrice=stores.getProductPrice("Dairy", "Milk", 1);
            assertEquals(newPrice, 6.4, 0);
        }
        catch(Exception e){
            System.out.println("failed");
        }
    }

    @Test
    public void testFailSetDiscount(){ //checks if failed- product does not exist
        assertThrows(Exception.class, () -> {stores.setDiscount("Dairy", "Milk", 4, 30);});
    }

    @Test
    public void testMoveToStore1(){ //checks if the category of the item we want to move to the store exists
        assertThrows(Exception.class, ()->{stores.moveToStore("Drinks", "Juice", 3, 1);});
    }

    @Test
    public void testMoveToStore2(){ //checks if the item we want to move to the store exists
        assertThrows(Exception.class, ()->{stores.moveToStore("Dairy", "Milk", 3, 1);});
    }

    @Test
    public void testStoreDoesNotExist(){ //checks if it blocks you from entering a store that does not exist
        assertDoesNotThrow(() -> {stores.closeStore();});
        assertThrows(Exception.class, ()->{stores.setStore("Roni");});
    }

    @Test
    public void testSetStore(){ //checks enter another store
        assertDoesNotThrow(() -> {stores.closeStore();});
        assertDoesNotThrow(() -> {stores.newStore("Roni");});
        assertDoesNotThrow(() -> {stores.setStore("Roni");});
    }

    @Test
    public void printPeriodicalReport(){ //checks if prints correctly
        try {
            String expect="Periodical Report for Category Dairy : \n" + "Expired Items: \n" + "Damaged Items: \n" + "Tnuva Milk 3%, serial number: 1, amount: 1 \n";
            assertDoesNotThrow(() -> {stores.updateDamagedItem("Dairy", "Milk", 1, 1);});
            Assert.assertEquals(expect, stores.getPeriodicalReport("Dairy"));
        }
        catch (Exception e){
            System.out.println("failed");
        }
    }

    @Test
    public void printFailPeriodicalReport(){ //checks if failed- category does not exist
        assertThrows(Exception.class, () -> {stores.getPeriodicalReport("Fruits");});
    }


    @Test
    public void printStockReport(){ //checks if prints correctly
        try {
            String expect="Stock Report for Category Dairy:\n" + "Milk: \n" + "Tnuva Milk 3%, serial number: 1, size: 1000, stock: 1, place: Aigle: 2, Shelf: 2 \n" + "\n" + "Cheese: \n" + "Gauda Tnuva, serial number: 2, size: 500, stock: 1, place: Aigle: 2, Shelf: 3 \n";
            Assert.assertEquals(expect, stores.getStockReport("Dairy"));
        }
        catch (Exception e){
            System.out.println("failed");
        }
    }

    @Test
    public void printFailStockReport(){ //checks if failed- category does not exist
        assertThrows(Exception.class, () -> {stores.getStockReport("Fruits");});
    }

    @Test
    public void printLowStockReport(){ //checks if prints correctly
        try {
            String expect="\nCategory: Dairy Sub-category: Milk Name: Tnuva Milk 3% Serial Number: 1 Amount missing: 2";
            Assert.assertEquals(expect, stores.printLowStock());
        }
        catch (Exception e){
            System.out.println("failed");
        }
    }


}
