package Tests;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import DomainLayer.*;
import org.junit.*;

public class StoreTest {
    public Store store=new Store();


    @BeforeEach
    public void setUp() {
        try{
            store.addItem("Dairy", "Milk", "Tnuva Milk 3%", 1, 1, "Tnuva", 5, 8, 1000, "01-07-2024");
        }
        catch(Exception e){}
    }


    @Test
    public void testAddItem1() { //checks if another product already has the same serial number
        assertThrows(Exception.class, ()->{store.addItem("Dairy", "Cheese", "Gauda Tnuva", 1, 1, "Hadas", 15, 20, 500, "20-07-2024");});
    }

    @Test
    public void testAddItem2() { //checks if the date is written correctly
        assertThrows(Exception.class, ()->{store.addItem("Dairy", "Cheese", "Gauda Tnuva", 1, 1, "Hadas", 15, 20, 500, "20/07/2024");});
    }

    @Test
    public void testAddItem3() { //checks if adds correct item
        assertDoesNotThrow(()->{store.addItem("Dairy", "Cheese", "Gauda Tnuva", 3, 1, "Hadas", 15, 20, 500, "20-07-2024");});
        assertTrue(store.productExists("Dairy", "Cheese", 3));
    }

    @Test
    public void testSellItem1(){ //checks if the item exists
        assertThrows(Exception.class, ()->{store.sellItem("Dairy", "Milk", 1, 2);});
    }

    @Test
    public void testSellItem2(){ //checks if sells correct item and deletes the product when the stock is 0
        assertDoesNotThrow(()->{store.sellItem("Dairy", "Milk", 1, 1);});
        Assert.assertFalse(store.productExists("Dairy", "Milk", 1));
    }

    @Test
    public void testDamagedItem(){ //checks if deletes correct item when damaged
        assertDoesNotThrow(()->{store.updateDamagedItem("Dairy", "Milk", 1, 1);});
        Assert.assertFalse(store.productExists("Dairy", "Milk", 1));
    }

    @Test
    public void testStockWarning(){ //checks if it prints a stock warning when the stock reaches the minimum amount
        store.updateMinimumAmount("Dairy", "Milk", 1, 1);
        assertTrue(store.stockWarning("Dairy", "Milk", 1));
    }

    @Test
    public void testSetDiscount(){
        assertDoesNotThrow(()-> {store.setDiscount("Dairy", "Milk", 1, 20);});
        try{
            Double newPrice=store.getProductPrice("Dairy", "Milk", 1);
            assertEquals(newPrice, 6.4, 0);
        }
        catch(Exception e){
            System.out.println("failed");
        }
    }

    @Test
    public void testMoveToStore1(){ //checks if the category of the item we want to move to the store exists
        assertThrows(Exception.class, ()->{store.moveToStore("Drinks", "Juice", 3, 1);});
    }

    @Test
    public void testMoveToStore2(){ //checks if the item we want to move to the store exists
        assertThrows(Exception.class, ()->{store.moveToStore("Dairy", "Milk", 3, 1);});
    }


}
