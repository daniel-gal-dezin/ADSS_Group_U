package DomainLayer;

import java.util.Dictionary;
import java.util.LinkedList;
import java.util.Map;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class Store {
    private Map<String, Category> categoryList;
    private LinkedList<String> periodicalReportList;
    private LocalDate lastPeriodicalReport;
    private LinkedList<String> stockReportList;
    private LocalDate lastStockReport;

    public boolean addItem(String category, String subcategory, String name, int serialNum, String producer, int cost, int soldPrice, int size, String expDate){
        if(!categoryList.containsKey(category)){
            categoryList.put(category, new Category(category));
        }
        Category cat=categoryList.get(category);
        return cat.addItem(subcategory, name, serialNum, categoryList.size()+1, producer, cost, soldPrice, size, expDate);
    }

    public boolean updateDamagedItem(String category, String subcategory, int serialNum, int id){ //The check needs to be conducted once a week. We need to determine the way to implement this process
        return categoryList.get(category).updateDamagedItem(subcategory, serialNum, id);
    }

    public boolean setDiscount(String category, String subcategory, int serialNum, int discount){
        return categoryList.get(category).setDiscount(subcategory, serialNum, discount);
    }

    public double getProductPrice(String category, String subcategory, int serialNum){
        return categoryList.get(category).getProductPrice(subcategory, serialNum);
    }

    public String getPeriodicalReport(String category){
        String report=categoryList.get(category).getPeriodicalReport();
        periodicalReportList.add(report);
        lastPeriodicalReport=LocalDate.now();
        return report;
    }

    public String getStockReport(String category){
        String report=categoryList.get(category).getStockReport();
        stockReportList.add(report);
        lastStockReport=LocalDate.now();
        return report;
    }

    public boolean moveToStore(String category, String subcategory, int serialNum, int id){
        return categoryList.get(category).moveToStore(subcategory, serialNum, id);
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
