package DomainLayer;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Category {
    private String name;
    private Map<String, Subcategory> subcatList;
    private Map<Product, Integer> expList;
    private Map<Product, Integer> damagedList;

    public Category(String name){
        this.name=name;
        subcatList=new LinkedHashMap<String,Subcategory>();
        expList=new LinkedHashMap<Product, Integer>();
        damagedList=new LinkedHashMap<Product, Integer>();
    }

    public boolean addItem(String subcategory, String name, int serialNum, int aigleNum, String producer, int cost, int soldPrice, int size, String expDate){
        if(!subcatList.containsKey(subcategory)){
            subcatList.put(subcategory, new Subcategory(subcategory));
        }
        Subcategory subcat=subcatList.get(subcategory);
        return subcat.addItem(name, serialNum, aigleNum, subcatList.size()+1, producer, cost, soldPrice, size, expDate);
    }

    public boolean updateDamagedItem(String subcategory, int serialNum, int id){
        Product pro=subcatList.get(subcategory).getProductList().get(serialNum);
        pro.removeItem(id);
        if (!damagedList.containsKey(pro)){
            damagedList.put(pro, 1);
        }
        else{
            damagedList.put(pro, damagedList.get(pro)+1);
        }
        return true;
    }

    public boolean setDiscount(String subcategory, int serialNum, int discount){
        return subcatList.get(subcategory).setDiscount(serialNum, discount);
    }

    public double getProductPrice(String subcategory, int serialNum){
        return subcatList.get(subcategory).getProductPrice(serialNum);
    }

    public String getPeriodicalReport(){
        String report="Periodical Report for Category " + name + " : \n Expired Items:";
        for (Product p: expList.keySet()){
            report=report + p.getName() + ", serial number: " + p.getSerialNum() + ", amount: " + expList.get(p) + " \n";
        } 
        report=report+ "Damaged Items:";
        for (Product p: damagedList.keySet()){
            report=report + p.getName() + ", serial number: " + p.getSerialNum() + ", amount: " + damagedList.get(p) + " \n";
        } 
        this.expList=new LinkedHashMap<Product, Integer>();
        this.damagedList=new LinkedHashMap<Product, Integer>();
        return report;
    }

    public String getStockReport(){
        String report="Stock Report for Category " + name + ": \n";
        for (Subcategory sub: subcatList.values()){
            report=report + sub.getName() + " \n";
            for (Product p: sub.getProductList().values()){
                report=report + p.getName() + ", serial number: " + p.getSerialNum() + ", size: " + p.getSize() + ", stock: " + p.getStock() + ", place: " + p.getPlace();
            }
        } 
        return report;
    }

    public boolean moveToStore(String subcategory, int serialNum, int id){
        return subcatList.get(subcategory).moveToStore(serialNum, id);
    }
}
