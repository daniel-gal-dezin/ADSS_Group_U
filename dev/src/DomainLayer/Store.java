package DomainLayer;

import java.util.Dictionary;
import java.util.LinkedList;
import java.util.Map;

public class Store {
    private Map<String, Category> categoryList;
    private LinkedList<PeriodicalReport> periodicalReportList;
    private LinkedList<StockReport> stockReportList;

    public boolean addItem(String category, String subcategory, int serialNum, int size, String expDate, int price){
        if(!categoryList.containsKey(category)){
            categoryList.put(category, new Category(category));
        }
        Category cat=categoryList.get(category);
        return cat.addItem(subcategory, serialNum, size, expDate, price);
    }

    public boolean updateDamagedItem(String category, String subcategory, int serialNum, int id){
        return true;
    }

    public boolean updateDiscount(int discount, String category, String subcategory, int serialNum){
        return true;
    }

    public String getPeriodicalReport(String category){
        return "";
    }

    public String getStockReport(String category){
        return "";
    }

    public boolean moveToStore(String category, String subcategory, int serialNum, int count){
        return true;
    }
    public Category getCategory(String category){
        return categoryList.get(category);
    }
}
