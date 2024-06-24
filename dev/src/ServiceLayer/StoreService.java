package ServiceLayer;
import DomainLayer.ProductCounter;
import DomainLayer.Store;
import DomainLayer.StoreManager;

public class StoreService {
    StoreManager stores;

    public StoreService(StoreManager stores){
        this.stores=stores;
    }

    public String setStore(String name){
        try{
            stores.setStore(name);
        }
        catch (Exception e) {
            return new Response(e.getMessage()).toJson();
        }
        return new Response().toJson();
    }

    public String createStore(String name){
        try{
            stores.newStore(name);
        }
        catch (Exception e) {
            return new Response(e.getMessage()).toJson();
        }
        return new Response().toJson();
    }

    public String loadData(){
        try{
            stores.loadData();
        }
        catch (Exception e) {
            return new Response(e.getMessage()).toJson();
        }
        return new Response().toJson();
    }

    public String removeExpItems(){
        try{
            stores.removeExpItems();
        }
        catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return new Response().toJson();
    }
    
    public String addItem(String category, String subcategory, String name, int serialNum, int id, String producer, int cost, int soldPrice, int size, String expDate){
        try{
            stores.addItem(category, subcategory, name, serialNum, id, producer, cost, soldPrice, size, expDate);
        }
        catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return new Response().toJson();
    }

    public String sellItem(String category, String subcategory, int serialNum, int id){
        try{
            stores.sellItem(category, subcategory, serialNum, id);
        }
        catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return new Response().toJson();
    }

    public boolean stockWarning(String category, String subcategory, int serialNum){
        return stores.productExists(category, subcategory, serialNum);
    }

    public boolean productExists(String category, String subcategory, int serialNum){
        return stores.productExists(category, subcategory, serialNum);
    }

    public String updateMinimumAmount(String category, String subcategory, int serialNum, int amount){
        try{
            stores.updateMinimumAmount(category, subcategory, serialNum, amount);
        }
        catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return new Response().toJson();
    }

    public String updateDamagedItem(String category, String subcategory, int serialNum, int id){ 
        try{
            stores.updateDamagedItem(category, subcategory, serialNum, id);
        }
        catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return new Response().toJson();
    }

    public String setDiscount(String category, String subcategory, int serialNum, int discount){
        try{
            stores.setDiscount(category, subcategory, serialNum, discount);
        }
        catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return new Response().toJson();
    }

    public String getProductPrice(String category, String subcategory, int serialNum){
        Response r = new Response();
        try{
            r.ReturnValue = Double.toString(stores.getProductPrice(category, subcategory, serialNum));
        }
        catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return r.toJson();
    }

    public String getPeriodicalReport(String category){
        Response r = new Response();
        try{
            r.ReturnValue = stores.getPeriodicalReport(category);
        }
        catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return r.toJson();
    }

    public String getStockReport(String category){
        Response r = new Response();
        try{
            r.ReturnValue = stores.getStockReport(category);
        }
        catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return r.toJson();
    }

    public String moveToStore(String category, String subcategory, int serialNum, int id){
        try{
            stores.moveToStore(category, subcategory, serialNum, id);
        }
        catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return new Response().toJson();
    }

    public String printAllReports(){
        Response r = new Response();
        try{
            r.ReturnValue = stores.printAllReports();
        }
        catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return r.toJson();
    }

    public String printLowStock(){
        Response r = new Response();
        try{
            r.ReturnValue = stores.printLowStock();
        }
        catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return r.toJson();
    }

    public String openStore(){
        try{
            stores.openStore();
        }
        catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return new Response().toJson();
    }

    public String closeStore(){
        try{
            stores.closeStore();
        }
        catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return new Response().toJson();
    }

}
