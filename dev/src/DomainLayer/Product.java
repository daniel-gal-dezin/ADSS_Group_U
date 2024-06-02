package DomainLayer;
import java.util.Date;
import java.util.Dictionary;
import java.util.LinkedList;
import java.util.Map;

public class Product {
    private int serialNum;
    private int stock;
    private Place inStore;
    private Place inStorage;
    private String producer;
    private Date expDate;
    private int cost;
    private int soldPrice;
    private int size;
    private int discount;
    private Map<Integer, Item> itemList;
}
