package DomainLayer;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Category {
    private String name;
    private Map<String, Subcategory> subcatList;
    private Map<Product, Item> expList;
    private Map<Product, Item> damagedList;

    public Category(String name){
        this.name=name;
        subcatList=new LinkedHashMap<String,Subcategory>();
        expList=new LinkedHashMap<Product, Item>();
        damagedList=new LinkedHashMap<Product, Item>();
    }

    public boolean addItem(String subcategory, int size, String expDate, int price){
        if(!subcatList.containsKey(subcategory)){
            subcatList.put(subcategory, new Subcategory(size, expDate, price));
        }
    }
}
