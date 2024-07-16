package DomainLayer;

import DataAccessLayer.ProductDTO;

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

    public Product addProduct(ProductDTO p){
        if (!productList.containsKey(p.getSerialNumber())){
            productList.put(p.getSerialNumber(), new Product(p));
        }
        return productList.get(p.getSerialNumber());
    }

    public boolean addItem(String store, String category, String name, int serialNum, int id, int aigleNum, int shelfNum, String producer, int cost, int soldPrice, int size, String expDate) throws Exception {
        if(!productList.containsKey(serialNum)){
            Product newPro=new Product(store, category, this.name, name, serialNum, aigleNum, shelfNum, producer, cost, soldPrice, size);
            productList.put(serialNum, newPro);
            newPro.getPdto().persist();
        }
        Product pro=productList.get(serialNum);
        return pro.addItem(id, expDate);
    }

    public boolean sellItem(int serialNum, int id) throws Exception {
        Product pro=productList.get(serialNum);
        if (pro==null){
            return false;
        }
        Item item=pro.removeItem(id);
        if(item==null)
            return false;
        try {
            pro.getPdto().decreaseStock();
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        return true;
    }

    public int stockWarning(int serialNum){
        return productList.get(serialNum).stockWarning();
    }

    public boolean productExists(int serialNum){
        Product pro=productList.get(serialNum);
        if (pro==null){
            return false;
        }
        return true;
    }

    public boolean updateMinimumAmount(int serialNum, int amount) throws Exception {
        return productList.get(serialNum).updateMinimumAmount(amount);
    }

    public boolean updateDamagedItem(int serialNum, int id) throws Exception {
        Product pro=productList.get(serialNum);
        if (pro==null){
            return false;
        }
        try {
            pro.getPdto().decreaseStock();
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }
        Item item=pro.removeItem(id);
        if(item==null)
            return false;
        return true;
    }

    public boolean setDiscount(int serialNum, int discount) throws Exception {
        Product pro=productList.get(serialNum);
        if (pro==null){
            return false;
        }
        return pro.setDiscount(discount);
    }

    public double getProductPrice(int serialNum){
        Product pro=productList.get(serialNum);
        if (pro==null){
            return -1;
        }
        return pro.getProductPrice();
    }

    public boolean moveToStore(int serialNum, int id) throws Exception {
        Product pro=productList.get(serialNum);
        if (pro==null){
            return false;
        }
        return pro.moveToStore(id);
    }
}
