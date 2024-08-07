package DomainLayer;

import DataAccessLayer.ReportDTO;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Store {
    private String name;
    private Map<String, Category> categoryList;
    private Map<Integer, String> serialNumCheck;
    private LinkedList<String> periodicalReportList;
    private LocalDate lastPeriodicalReport;
    private LinkedList<String> stockReportList;
    private LocalDate lastStockReport;
    private Map<Product, ProductForLists> lowStock;

    public boolean open;

    public Store(String name){
        this.name=name;
        this.categoryList=new LinkedHashMap<String, Category>();
        this.serialNumCheck=new LinkedHashMap<Integer, String>();
        this.periodicalReportList=new LinkedList<>();
        this.lastPeriodicalReport=LocalDate.now();
        this.stockReportList=new LinkedList<>();
        this.lastStockReport=LocalDate.now();
        this.lowStock=new LinkedHashMap<>();
    }


     public boolean removeExpItems() throws Exception {
        for (Category c: categoryList.values()){
            c.removeExpItems();
        }
        for (Category c: categoryList.values()){
            for (Subcategory s: c.getSubcatList().values()){
                for (Product p: s.getProductList().values()){
                    int ans = stockWarning(c.getName(), s.getName(), p.getSerialNum());
                    if (ans != -1) {
                        if (lowStock.containsKey(p)) {
                            lowStock.remove(p);
                        }
                        lowStock.put(p, new ProductForLists(c.getName(), s.getName(), p));
                    }
                }
            }
        }
        return true;
     }

    public boolean addItem(String category, String subcategory, String name, int serialNum, int id, String producer, int cost, int soldPrice, int size, String expDate) throws Exception{
        if(!categoryList.containsKey(category)){
            categoryList.put(category, new Category(category));
        }
        Category cat=categoryList.get(category);
        String check=category+"-"+subcategory+"-"+name;
        String[] date=expDate.split("-");
        if (date.length!=3 || date[0].length()!=2 || date[1].length()!=2 || date[2].length()!=4){
            throw new Exception("Expired date invalid. should be dd-mm-yyyy.");
        }
        if (serialNumCheck.containsKey(serialNum)){
            if (!serialNumCheck.get(serialNum).equals(check)){
                throw new Exception("Serial number invalid.");
            }
        }
        else{ 
            serialNumCheck.put(serialNum, check);
        }
        boolean b=cat.addItem(this.name, subcategory, name, serialNum, id, categoryList.size()+1, producer, cost, soldPrice, size, expDate);
        if(!b){
            throw new Exception("Id already exists.");
        }
        else{
            removeExpItems();
            Product p=cat.getSubcatList().get(subcategory).getProductList().get(serialNum);
            if(stockWarning(category, subcategory, serialNum)==-1){
                lowStock.remove(p);
            }
            return true;
        }
    }

    public boolean sellItem(String category, String subcategory, int serialNum, int id) throws Exception{
        Category c=categoryList.get(category);
        if (c==null){
            throw new Exception("Category does not exist.");
        }
        boolean success=c.sellItem(subcategory, serialNum, id);
        if (!success){
            throw new Exception("Item does not exist.");
        }
        Product p=c.getSubcatList().get(subcategory).getProductList().get(serialNum);
        if(success){
            int ans = stockWarning(category, subcategory, serialNum);
            if (ans != -1) {
                if (lowStock.containsKey(p)) {
                    lowStock.remove(p);
                }
                lowStock.put(p, new ProductForLists(category, subcategory, p));
            }
        }
        return true;
    }

    public int stockWarning(String category, String subcategory, int serialNum){
        return categoryList.get(category).stockWarning(subcategory, serialNum);
    }

    public boolean productExists(String category, String subcategory, int serialNum){
        Category c=categoryList.get(category);
        if (c==null){
            return false;
        }
        return c.productExists(subcategory, serialNum);
    }

    public boolean updateMinimumAmount(String category, String subcategory, int serialNum, int amount) throws Exception {
        return categoryList.get(category).updateMinimumAmount(subcategory, serialNum, amount);
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
        else {
            Product p=c.getSubcatList().get(subcategory).getProductList().get(serialNum);
            int ans = stockWarning(category, subcategory, serialNum);
            if (ans != -1) {
                if (lowStock.containsKey(p)) {
                    lowStock.remove(p);
                }
                lowStock.put(p, new ProductForLists(category, subcategory, p));
            }
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
        ReportDTO rpdto=new ReportDTO(name, "periodical", report);
        try
        {
            rpdto.addReport();
        }
        catch (Exception ex)
        {
            throw new Exception(ex.getMessage());
        }
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
        ReportDTO rpdto=new ReportDTO(name, "stock", report);
        try
        {
            rpdto.addReport();
        }
        catch (Exception ex)
        {
            throw new Exception(ex.getMessage());
        }
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

    public void addReport(String type, String report){
        if (type.equals("stock")){
            stockReportList.add(report);
        }
        else{
            periodicalReportList.add(report);
        }
    }

    public Category getCategory(String category){
        return categoryList.get(category);
    }

    public String printAllReports(){
        String output="All Reports: \n";
        for(String s: periodicalReportList){
            output=output+s+"\n";
        }
        for(String s: stockReportList){
            output=output+s+"\n";
        }
        return output;
    }

    public Category addCategory(String categoryName){
        if (!categoryList.containsKey(categoryName)){
            categoryList.put(categoryName, new Category(categoryName));
        }
        return categoryList.get(categoryName);
    }

    public String openStore(){
        open = true;
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

    public String getLowStock(){
        String output="";
        for (ProductForLists p : lowStock.values()) {
            output = output + "\n" + p.getOutput();
        }
        return output;
    }

    public void closeStore(){
        open = false;
    }
}
