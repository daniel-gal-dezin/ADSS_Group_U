package DomainLayer;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

public class Subcategory {
    private String name;
    private Map<Integer, Product> productList;

    public Subcategory(String name){
        this.name=name;
        productList=new LinkedHashMap<Integer, Product>();
    }

    public String getName(){
        return this.name;
    }

    public Map<Integer, Product> getProductList(){
        return this.productList;
    }

    public boolean addItem(String name, int serialNum, int aigleNum, int shelfNum, String producer, int cost, int soldPrice, int size, String expDate){
        if(!productList.containsKey(serialNum)){
            productList.put(serialNum, new Product(name, serialNum, aigleNum, shelfNum, producer, cost, soldPrice, size));
        }
        Product pro=productList.get(serialNum);
        return pro.addItem(expDate);
    }

    public boolean setDiscount(int serialNum, int discount){
        return productList.get(serialNum).setDiscount(discount);
    }

    public double getProductPrice(int serialNum){
        return productList.get(serialNum).getProductPrice();
    }

    public boolean moveToStore(int serialNum, int id){
        return productList.get(serialNum).moveToStore(id);
    }
}
