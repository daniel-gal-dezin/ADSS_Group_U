package DomainLayer;

import ServiceLayer.Response;

import java.util.HashMap;

public class StoreManager {
    public HashMap<String, Store> stores;
    public Store currStore;

    public StoreManager(){
        stores = new HashMap<>();
        currStore=null;
    }

    public void setStore(String name) throws Exception {
        if(!stores.containsKey(name)){
            throw new Exception("This store does not exists");
        }
        else{
            currStore=stores.get(name);
        }
    }

    public void newStore(String name) throws Exception {
        if(stores.containsKey(name)){
            throw new Exception("This store already exists");
        }
        else{
            stores.put(name, new Store());
        }
    }

    public Store getStore(String name) throws Exception {
        if(!stores.containsKey(name)){
            throw new Exception("This store does not exist");
        }
        else {
            return stores.get(name);
        }
    }

    public boolean removeExpItems() throws Exception {
        if(currStore==null){
            throw new Exception("You are not in a store");
        }
        return currStore.removeExpItems();
    }

    public boolean addItem(String category, String subcategory, String name, int serialNum, int id, String producer, int cost, int soldPrice, int size, String expDate) throws Exception {
        if(currStore==null){
            throw new Exception("You are not in a store");
        }
        return currStore.addItem(category, subcategory, name, serialNum, id, producer, cost, soldPrice, size, expDate);
    }

    public boolean sellItem(String category, String subcategory, int serialNum, int id) throws Exception {
        if (currStore == null) {
            throw new Exception("You are not in a store");
        }
        return currStore.sellItem(category, subcategory, serialNum, id);
    }

    public int stockWarning(String category, String subcategory, int serialNum) throws Exception {
        if (currStore == null) {
            throw new Exception("You are not in a store");
        }
        return currStore.stockWarning(category, subcategory, serialNum);
    }

    public boolean productExists(String category, String subcategory, int serialNum) {
        if (currStore == null) {
            return false;
        }
        return currStore.productExists(category, subcategory, serialNum);
    }

    public boolean updateMinimumAmount(String category, String subcategory, int serialNum, int amount) throws Exception {
        if (currStore == null) {
            throw new Exception("You are not in a store");
        }
        return currStore.updateMinimumAmount(category, subcategory, serialNum, amount);
    }

    public boolean updateDamagedItem(String category, String subcategory, int serialNum, int id) throws Exception {
        if (currStore == null) {
            throw new Exception("You are not in a store");
        }
        return currStore.updateDamagedItem(category, subcategory, serialNum, id);
    }

    public boolean setDiscount(String category, String subcategory, int serialNum, int discount) throws Exception {
        if (currStore == null) {
            throw new Exception("You are not in a store");
        }
        return currStore.setDiscount(category, subcategory, serialNum, discount);
    }

    public double getProductPrice(String category, String subcategory, int serialNum) throws Exception {
        if (currStore == null) {
            throw new Exception("You are not in a store");
        }
        return currStore.getProductPrice(category, subcategory, serialNum);
    }

    public String getPeriodicalReport(String category) throws Exception {
        if (currStore == null) {
            throw new Exception("You are not in a store");
        }
        return currStore.getPeriodicalReport(category);
    }

    public String getStockReport(String category) throws Exception {
        if (currStore == null) {
            throw new Exception("You are not in a store");
        }
        return currStore.getStockReport(category);
    }

    public boolean moveToStore(String category, String subcategory, int serialNum, int id) throws Exception {
        if (currStore == null) {
            throw new Exception("You are not in a store");
        }
        return currStore.moveToStore(category, subcategory, serialNum, id);
    }

    public String printAllReports() throws Exception {
        if (currStore == null) {
            throw new Exception("You are not in a store");
        }
        return currStore.printAllReports();
    }

    public String printLowStock() throws Exception {
        if (currStore == null) {
            throw new Exception("You are not in a store");
        }
        return currStore.getLowStock();
    }

    public String openStore() throws Exception {
        if (currStore == null) {
            throw new Exception("You are not in a store");
        }
        return currStore.openStore();
    }

    public void closeStore() throws Exception {
        if (currStore == null) {
            throw new Exception("You are not in a store");
        }
        currStore=null;
    }
}
