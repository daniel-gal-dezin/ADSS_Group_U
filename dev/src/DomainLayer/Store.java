package DomainLayer;

import java.util.Dictionary;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Store {
    private Map<String, Category> categoryList;
    private Map<Integer, String> serialNumCheck;
    private LinkedList<String> periodicalReportList;
    private LocalDate lastPeriodicalReport;
    private LinkedList<String> stockReportList;
    private LocalDate lastStockReport;

    public Store(){
        this.categoryList=new LinkedHashMap<String, Category>();
        this.serialNumCheck=new LinkedHashMap<Integer, String>();
        this.periodicalReportList=new LinkedList<>();
        this.lastPeriodicalReport=LocalDate.now();
        this.stockReportList=new LinkedList<>();
        this.lastStockReport=LocalDate.now();
    }

    public boolean addItem(String category, String subcategory, String name, int serialNum, int id, String producer, int cost, int soldPrice, int size, String expDate) throws Exception{
        if(!categoryList.containsKey(category)){
            categoryList.put(category, new Category(category));
        }
        Category cat=categoryList.get(category);
        String categoryAndSub=category+"-"+subcategory;
        String[] date=expDate.split("-");
        if (date.length!=3 || date[0].length()!=2 || date[1].length()!=2 || date[2].length()!=4){
            throw new Exception("Expired date invalid. should be dd-mm-yyyy.");
        }
        if (serialNumCheck.containsKey(serialNum)){
            if (!serialNumCheck.get(serialNum).equals(categoryAndSub)){
                throw new Exception("Serial number invalid.");
            }
        }
        else{ 
            serialNumCheck.put(serialNum, categoryAndSub);
        }
        boolean b=cat.addItem(subcategory, name, serialNum, id, categoryList.size()+1, producer, cost, soldPrice, size, expDate);
        if(!b){
            throw new Exception("Id already exists.");
        }
        else{
            return true;
        }
    }

    public boolean updateDamagedItem(String category, String subcategory, int serialNum, int id) throws Exception{ //The check needs to be conducted once a week. We need to determine the way to implement this process
        Category c=categoryList.get(category);
        if (c==null){
            throw new Exception("Category does not exist.");
        }
        boolean success=c.updateDamagedItem(subcategory, serialNum, id);
        if (!success){
            throw new Exception("Item does not exist.");
        }
        return true;
    }

    public boolean setDiscount(String category, String subcategory, int serialNum, int discount) throws Exception{
        Category c=categoryList.get(category);
        if (c==null){
            throw new Exception("Category does not exist.");
        }
        boolean success=c.setDiscount(subcategory, serialNum, discount);
        if (!success){
            throw new Exception("Product does not exist.");
        }
        return true;
    }

    public double getProductPrice(String category, String subcategory, int serialNum) throws Exception{
        Category c=categoryList.get(category);
        if (c==null){
            throw new Exception("Category does not exist.");
        }
        double price=c.getProductPrice(subcategory, serialNum);
        if (price==-1){
            throw new Exception("Product does not exist.");
        }
        return price;
    }

    public String getPeriodicalReport(String category) throws Exception{
        Category c=categoryList.get(category);
        if (c==null){
            throw new Exception("Category does not exist.");
        }
        String report=c.getPeriodicalReport();
        periodicalReportList.add(report);
        lastPeriodicalReport=LocalDate.now();
        return report;
    }

    public String getStockReport(String category) throws Exception{
        Category c=categoryList.get(category);
        if (c==null){
            throw new Exception("Category does not exist.");
        }
        String report=c.getStockReport();
        stockReportList.add(report);
        lastStockReport=LocalDate.now();
        return report;
    }

    public boolean moveToStore(String category, String subcategory, int serialNum, int id) throws Exception{
        Category c=categoryList.get(category);
        if (c==null){
            throw new Exception("Category does not exist.");
        }
        boolean success=c.moveToStore(subcategory, serialNum, id);
        if (!success){
            throw new Exception("Item does not exist.");
        }
        return true;
    }

    
    public Category getCategory(String category){
        return categoryList.get(category);
    }

    public String openStore(){
        long daysBetweenPeriodicalReport=ChronoUnit.DAYS.between(lastPeriodicalReport,LocalDate.now());
        long daysBetweenStockReport=ChronoUnit.DAYS.between(lastStockReport,LocalDate.now());
        if (daysBetweenPeriodicalReport>=7){
            return "You need to produce a periodical report.";
        }
        if (daysBetweenStockReport>=7){
            return "You need to produce a stock report.";
        }
        return "";
    }
}
