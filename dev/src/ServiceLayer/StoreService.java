package ServiceLayer;
import DomainLayer.Store;
public class StoreService {
    Store store;
    
    public StoreService(Store store){
        this.store=store;
    }

    public String removeExpItems(){
        try{
            store.removeExpItems();
        }
        catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return new Response().toJson();
    }
    
    public String addItem(String category, String subcategory, String name, int serialNum, int id, String producer, int cost, int soldPrice, int size, String expDate){
        try{
            store.addItem(category, subcategory, name, serialNum, id, producer, cost, soldPrice, size, expDate);
        }
        catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return new Response().toJson();
    }

    public String sellItem(String category, String subcategory, int serialNum, int id){
        try{
            store.sellItem(category, subcategory, serialNum, id);
        }
        catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return new Response().toJson();
    }

    public boolean stockWarning(String category, String subcategory, int serialNum){
        return store.productExists(category, subcategory, serialNum);
    }

    public boolean productExists(String category, String subcategory, int serialNum){
        return store.productExists(category, subcategory, serialNum);
    }

    public String updateMinimumAmount(String category, String subcategory, int serialNum, int amount){
        try{
            store.updateMinimumAmount(category, subcategory, serialNum, amount);
        }
        catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return new Response().toJson();
    }

    public String updateDamagedItem(String category, String subcategory, int serialNum, int id){ 
        try{
            store.updateDamagedItem(category, subcategory, serialNum, id);
        }
        catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return new Response().toJson();
    }

    public String setDiscount(String category, String subcategory, int serialNum, int discount){
        try{
            store.setDiscount(category, subcategory, serialNum, discount);
        }
        catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return new Response().toJson();
    }

    public String getProductPrice(String category, String subcategory, int serialNum){
        Response r = new Response();
        try{
            r.ReturnValue = Double.toString(store.getProductPrice(category, subcategory, serialNum));
        }
        catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return r.toJson();
    }

    public String getPeriodicalReport(String category){
        Response r = new Response();
        try{
            r.ReturnValue = store.getPeriodicalReport(category);
        }
        catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return r.toJson();
    }

    public String getStockReport(String category){
        Response r = new Response();
        try{
            r.ReturnValue = store.getStockReport(category);
        }
        catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return r.toJson();
    }

    public String moveToStore(String category, String subcategory, int serialNum, int id){
        try{
            store.moveToStore(category, subcategory, serialNum, id);
        }
        catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return new Response().toJson();
    }

    public String printAllReports(){
        Response r = new Response();
        try{
            r.ReturnValue = store.printAllReports();
        }
        catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return r.toJson();
    }

    public String openStore(){
        try{
            store.openStore();
        }
        catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return new Response().toJson();
    }

}
