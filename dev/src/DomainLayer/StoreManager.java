package DomainLayer;

import DataAccessLayer.*;
import ServiceLayer.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
            stores.put(name, new Store(name));
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

    public void loadData(){
        HashMap<Integer, Product> map=new HashMap<>();
        List<ProductDTO> productDTOList=new ProductsMapper().selectAllProducts();
        for (ProductDTO p: productDTOList){
            String storeName=p.getStore();
            if (!stores.containsKey(storeName)){
                stores.put(storeName, new Store(storeName));
            }
            Store currStore=stores.get(storeName);
            String categoryName=p.getCategory();
            Category currCat=currStore.addCategory(categoryName);
            String subcategoryName=p.getSubcategory();
            Subcategory currSubcat=currCat.addSubcategory(subcategoryName);
            Product currProduct=currSubcat.addProduct(p);
            map.put(p.getProductId(), currProduct);
        }
        List<ItemDTO> itemDTOList=new ItemsMapper().selectAllItems();
        for (ItemDTO i: itemDTOList){
            Product p=map.get(i.getProductId());
            p.addItemFromDB(i);
        }
        List<ReportDTO> reportsDTOlist=new ReportsMapper().selectAllReports();
        for (ReportDTO r: reportsDTOlist){
            String store=r.getStore();
            String type=r.getType();
            stores.get(store).addReport(type, r.getReport());
        }
        List<ExpAndDamagedDTO> expDTOlist=new ExpAndDamagedMapper().selectAllExpAndDamaged();
        for (ExpAndDamagedDTO d: expDTOlist){
            String store=d.getStore();
            String type=d.getType();
            String category=d.getCategory();
            String subcategory=d.getSubcategory();
            int serialNumber=d.getSerialNumber();
            int amount=d.getAmount();
            Product p=stores.get(store).getCategory(category).getSubcatList().get(subcategory).getProductList().get(serialNumber);
            if (type.equals("expired")){
                p.setExdto(amount);
            }
            else {
                p.setDamdto(amount);
            }
            stores.get(store).getCategory(category).addToList(type, subcategory, serialNumber, amount);
        }
    }

    public int getProductStock(String category, String subcategory, int serialNumber){
        return currStore.getCategory(category).getSubcatList().get(subcategory).getProductList().get(serialNumber).getStock();
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

    public Category getCategory(String category){
        return currStore.getCategory(category);
    }

    public void closeStore() throws Exception {
        if (currStore == null) {
            throw new Exception("You are not in a store");
        }
        currStore=null;
    }
}
