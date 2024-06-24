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

    public boolean removeExpItems() throws Exception {
        for (Subcategory sub: subcatList.values()){
            for(Product p: sub.getProductList().values()){
                int removedItems=p.removeExpItems();
                if(removedItems>0){
                    if(expList.containsKey(p)){
                        expList.put(p, expList.get(p)+removedItems);
                    }
                    else{
                        expList.put(p, removedItems);
                    }
                }
            }
        }
        return true;
     }

     public String getName(){
        return this.name;
     }

    public Subcategory addSubcategory(String subcategoryName){
        if (!subcatList.containsKey(subcategoryName)){
            subcatList.put(subcategoryName, new Subcategory(subcategoryName));
        }
        return subcatList.get(subcategoryName);
    }

    public boolean addItem(String store, String subcategory, String name, int serialNum, int id, int aigleNum, String producer, int cost, int soldPrice, int size, String expDate) throws Exception {
        if(!subcatList.containsKey(subcategory)){
            subcatList.put(subcategory, new Subcategory(subcategory));
        }
        Subcategory subcat=subcatList.get(subcategory);
        return subcat.addItem(store, this.name, name, serialNum, id, aigleNum, subcatList.size()+1, producer, cost, soldPrice, size, expDate);
    }

    public boolean sellItem(String subcategory, int serialNum, int id) throws Exception {
        Subcategory sub=subcatList.get(subcategory);
        if(sub==null){
            return false;
        }
        return sub.sellItem(serialNum, id);
    }

    public int stockWarning(String subcategory, int serialNum){
        return subcatList.get(subcategory).stockWarning(serialNum);
    }

    public boolean productExists(String subcategory, int serialNum){
        Subcategory sub=subcatList.get(subcategory);
        if (sub==null){
            return false;
        }
        return sub.productExists(serialNum);
    }

    public boolean updateMinimumAmount(String subcategory, int serialNum, int amount) throws Exception {
        return subcatList.get(subcategory).updateMinimumAmount(serialNum, amount);
    }

    public boolean updateDamagedItem(String subcategory, int serialNum, int id) throws Exception {
        Subcategory sub=subcatList.get(subcategory);
        if(sub==null){
            return false;
        }
        Product pro=sub.getProductList().get(serialNum);
        if (pro==null){
            return false;
        }
        if (!damagedList.containsKey(pro)){
            damagedList.put(pro, 1);
        }
        else{
            damagedList.put(pro, damagedList.get(pro)+1);
        }
        return sub.updateDamagedItem(serialNum, id);
    }

    public boolean setDiscount(String subcategory, int serialNum, int discount) throws Exception {
        Subcategory sub=subcatList.get(subcategory);
        if(sub==null){
            return false;
        }
        return sub.setDiscount(serialNum, discount); 
    }

    public double getProductPrice(String subcategory, int serialNum){
        Subcategory sub=subcatList.get(subcategory);
        if(sub==null){
            return -1;
        }
        return sub.getProductPrice(serialNum);
    }

    public String getPeriodicalReport(){
        String report="Periodical Report for Category " + name + " : \nExpired Items: \n";
        for (Product p: expList.keySet()){
            report=report + p.getName() + ", serial number: " + p.getSerialNum() + ", amount: " + expList.get(p) + " \n";
        } 
        report=report+ "Damaged Items: \n";
        for (Product p: damagedList.keySet()){
            report=report + p.getName() + ", serial number: " + p.getSerialNum() + ", amount: " + damagedList.get(p) + " \n";
        } 
        this.expList=new LinkedHashMap<Product, Integer>();
        this.damagedList=new LinkedHashMap<Product, Integer>();
        return report;
    }

    public String getStockReport(){
        String report="Stock Report for Category " + name + ":";
        for (Subcategory sub: subcatList.values()){
            report=report + "\n" + sub.getName() + ": \n";
            for (Product p: sub.getProductList().values()){
                report=report + p.getName() + ", serial number: " + p.getSerialNum() + ", size: " + p.getSize() + ", stock: " + p.getStock() + ", place: " + p.getPlace() + " \n";
            }
        } 
        return report;
    }

    public boolean moveToStore(String subcategory, int serialNum, int id) throws Exception {
        Subcategory sub=subcatList.get(subcategory);
        if(sub==null){
            return false;
        }
        return sub.moveToStore(serialNum, id);
    }

    public Map<String, Subcategory> getSubcatList(){
        return subcatList;
    }
}
