package PresentationLayer;

import DomainLayer.Store;
import ServiceLayer.StoreService;
import java.util.Scanner;

public class StoreInterface {
    private StoreService sr;
    private boolean open;
    private boolean recoverData;

    public StoreInterface(StoreService stores){
        this.sr=stores;
        this.open=false;
        this.recoverData=false;
    }

    public void mainLoop(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome To Our System! \n");
        while(!open){
            System.out.println("Would you like to open a new store? yes/no");
            String input = scanner.nextLine();
            if(input.equals("no")){
                System.out.println("What is the name of your store?");
                String name = scanner.nextLine();
                String output=sr.setStore(name);
                if(output.length()==0){
                    open=true;
                    sr.removeExpItems();
                }
                else{
                    System.out.println(output);
                }
            }
            else if(input.equals("yes")){
                System.out.println("What is the name of the new store?");
                String name = scanner.nextLine();
                String output=sr.createStore(name);
                if(output.length()==0) {
                    sr.setStore(name);
                    open = true;
                    sr.removeExpItems();
                    recoverData=false;
                }
                else{
                    System.out.println(output);
                }
            }


            while(open){
                System.out.println(sr.openStore());
                if(!recoverData){
                    System.out.println("Select a number of one of the following options: \n 1- Add item. \n 2- Sell item. \n 3- Update damaged item. \n 4- Set discount. \n 5- Get product price. \n 6- Get periodical report. \n 7- Get stock report. \n 8- Move to store. \n 9- Get all previous reports. \n 10- Print Low Stock Products. \n 11- Close store. \n 12- Recover initial data.");
                }
                else
                    System.out.println("Select a number of one of the following options: \n 1- Add item. \n 2- Sell item. \n 3- Update damaged item. \n 4- Set discount. \n 5- Get product price. \n 6- Get periodical report. \n 7- Get stock report. \n 8- Move to store. \n 9- Get all previous reports. \n 10- Print Low Stock Products. \n 11- Close store. \n");
                String option = scanner.nextLine();
                if(option.equals("1")){
                    System.out.println("What is the category of the product?");
                    String cat=scanner.nextLine();
                    System.out.println("What is the sub-category of the product?");
                    String sub=scanner.nextLine();
                    System.out.println("What is the name of the product?");
                    String name=scanner.nextLine();
                    System.out.println("What is the serial number of the product?");
                    String serialNum=scanner.nextLine();
                    System.out.println("What is the id of the item?");
                    String id=scanner.nextLine();
                    System.out.println("Who is the producer of the product?");
                    String producer=scanner.nextLine();
                    System.out.println("What is the cost of the product?");
                    String cost=scanner.nextLine();
                    System.out.println("What will be the price of the product in the store?");
                    String price=scanner.nextLine();
                    System.out.println("What is the size of the product?");
                    String size=scanner.nextLine();
                    System.out.println("What is the expiration date of the product (dd-mm-yyyy)?");
                    String expDate=scanner.nextLine();
                    boolean productExists=sr.productExists(cat, sub, Integer.parseInt(serialNum));
                    String output=sr.addItem(cat, sub, name, Integer.parseInt(serialNum), Integer.parseInt(id), producer, Integer.parseInt(cost), Integer.parseInt(price), Integer.parseInt(size), expDate);
                    if(output.length()==0 && !productExists){
                        System.out.println("What will be the minimum amount of the product before sending a warning?");
                        String amount=scanner.nextLine();
                        sr.updateMinimumAmount(cat, sub, Integer.parseInt(serialNum), Integer.parseInt(amount));
                    }
                    else{
                        System.out.println(output);
                    }
                }
                if(option.equals("2")){
                    System.out.println("What is the category of the item?");
                    String cat=scanner.nextLine();
                    System.out.println("What is the sub-category of the item?");
                    String sub=scanner.nextLine();
                    System.out.println("What is the serial number of the item?");
                    String serialNum=scanner.nextLine();
                    System.out.println("What is the id of the item?");
                    String id=scanner.nextLine();
                    String output=sr.sellItem(cat, sub, Integer.parseInt(serialNum), Integer.parseInt(id));
                    if(output.length()==0){
                        if(sr.stockWarning(cat, sub, Integer.parseInt(serialNum))){
                            System.out.println("Stock Warning! you need to order more items from this product");
                        }
                    }
                    else{
                        System.out.println(output);
                    }
                }
                if(option.equals("3")){
                    System.out.println("What is the category of the item?");
                    String cat=scanner.nextLine();
                    System.out.println("What is the sub-category of the item?");
                    String sub=scanner.nextLine();
                    System.out.println("What is the serial number of the item?");
                    String serialNum=scanner.nextLine();
                    System.out.println("What is the id of the item?");
                    String id=scanner.nextLine();
                    String output=sr.updateDamagedItem(cat, sub, Integer.parseInt(serialNum), Integer.parseInt(id));
                    if(output.length()==0){
                        if(sr.stockWarning(cat, sub, Integer.parseInt(serialNum))){
                            System.out.println("Stock Warning! you need to order more items from this product");
                        }
                    }
                    else{
                        System.out.println(output);
                    }
                }
                if(option.equals("4")){
                    System.out.println("What is the category of the product?");
                    String cat=scanner.nextLine();
                    System.out.println("What is the sub-category of the product?");
                    String sub=scanner.nextLine();
                    System.out.println("What is the serial number of the product?");
                    String serialNum=scanner.nextLine();
                    System.out.println("What is the discount precentage?");
                    String discount=scanner.nextLine();
                    System.out.println(sr.setDiscount(cat, sub, Integer.parseInt(serialNum), Integer.parseInt(discount)));
                }
                if(option.equals("5")){
                    System.out.println("What is the category of the product?");
                    String cat=scanner.nextLine();
                    System.out.println("What is the sub-category of the product?");
                    String sub=scanner.nextLine();
                    System.out.println("What is the serial number of the product?");
                    String serialNum=scanner.nextLine();
                    System.out.println(sr.getProductPrice(cat, sub, Integer.parseInt(serialNum)));
                }
                if(option.equals("6")){
                    System.out.println("Which category?");
                    String cat=scanner.nextLine();
                    System.out.println(sr.getPeriodicalReport(cat));
                }
                if(option.equals("7")){
                    System.out.println("Which category?");
                    String cat=scanner.nextLine();
                    System.out.println(sr.getStockReport(cat));
                }
                if(option.equals("8")){
                    System.out.println("What is the category of the item?");
                    String cat=scanner.nextLine();
                    System.out.println("What is the sub-category of the item?");
                    String sub=scanner.nextLine();
                    System.out.println("What is the serial number of the item?");
                    String serialNum=scanner.nextLine();
                    System.out.println("What is the id of the item?");
                    String id=scanner.nextLine();
                    System.out.println(sr.moveToStore(cat, sub, Integer.parseInt(serialNum), Integer.parseInt(id)));
                }
                if(option.equals("9")){
                    System.out.println(sr.printAllReports());
                }
                if(option.equals("10")){
                    System.out.println(sr.printLowStock());
                }
                if(option.equals("11")){
                    open=false;
                    sr.closeStore();
                }
                if(option.equals("12")){
                    recoverData=true;
                    sr.addItem("Dairy", "Milk", "Tnuva Milk 3%", 1, 1, "Tnuva", 5, 8, 1000, "01-09-2024");
                    sr.addItem("Dairy", "Milk", "Tnuva Milk 3%", 1, 2, "Tnuva", 5, 8, 1000, "10-08-2024");
                    sr.updateMinimumAmount("Dairy", "Milk", 1, 2);
                    sr.addItem("Dairy", "Yogurt", "Go Yogurt 1%", 2, 1, "Tnuva", 7, 10, 50, "05-09-2024");
                    sr.updateMinimumAmount("Dairy", "Yogurt", 2, 6);
                    sr.addItem("Hygiene", "Shampoo", "Herbel Essense Shampoo", 3, 1, "Yossi", 20, 30, 500, "04-07-2026");
                    sr.updateMinimumAmount("Hygiene", "Shampoo", 3, 4);
                    sr.addItem("Hygiene", "Tooth Paste", "Colgate Tooth Paste", 4, 1, "Shai", 20, 25, 50, "04-11-2024");
                    sr.updateMinimumAmount("Hygiene", "Tooth Paste", 4, 10);
                    sr.addItem("Drinks", "Juice", "Coke", 5, 1, "Coca Cola", 6, 12, 1500, "01-10-2024");
                    sr.updateMinimumAmount("Drinks", "Juice", 5, 20);
                }
            }
        }
    }
}
