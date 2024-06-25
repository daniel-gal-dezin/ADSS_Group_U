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
                    try {
                        p.getPdto().decreaseStock();
                    } catch (Exception ex) {
                        throw new Exception(ex.getMessage());
                    }
                    if(expList.containsKey(p)){
                        try
                        {
                            p.getExdto().increaseAmount(removedItems);
                        }
                        catch (Exception ex)
                        {
                            throw new Exception (ex.getMessage());
                        }
                        expList.put(p, expList.get(p)+removedItems);
                    }
                    else{
                        try
                        {
                            p.setExdto();
                            p.getExdto().insert();
                            p.getExdto().increaseAmount(removedItems);
                        }
                        catch (Exception ex)
                        {
                            throw new Exception (ex.getMessage());
                        }
                        expList.put(p, removedItems);
                    }
                }
            }
        }
        return true;
     }

     public void addToList(String type, String subcategory, int serialNumber, int amount){
        Product p=subcatList.get(subcategory).getProductList().get(serialNumber);
        if (type.equals("expired")){
            expList.put(p, amount);
        }
        else {
            damagedList.put(p, amount);
        }
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
            try
            {
                pro.setDamdto();
                pro.getDamdto().insert();
                pro.getDamdto().increaseAmount(1);
            }
            catch (Exception ex)
            {
                throw new Exception (ex.getMessage());
            }
            damagedList.put(pro, 1);
        }
        else{
            try
            {
                pro.getDamdto().increaseAmount(1);
            }
            catch (Exception ex)
            {
                throw new Exception (ex.getMessage());
            }
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

    public String getPeriodicalReport() throws Exception {

        String report="Periodical Report for Category " + name + " : \nExpired Items: \n";
        for (Product p: expList.keySet()){
            report=report + p.getName() + ", serial number: " + p.getSerialNum() + ", amount: " + expList.get(p) + " \n";
            if (p.getExdto()!=null) {
                try {
                    p.getExdto().remove();
                } catch (Exception ex) {
                    throw new Exception(ex.getMessage());
                }
            }
        } 
        report=report+ "Damaged Items: \n";
        for (Product p: damagedList.keySet()){
            report=report + p.getName() + ", serial number: " + p.getSerialNum() + ", amount: " + damagedList.get(p) + " \n";
            if (p.getDamdto()!=null) {
                try {
                    p.getDamdto().remove();
                } catch (Exception ex) {
                    throw new Exception(ex.getMessage());
                }
            }
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
