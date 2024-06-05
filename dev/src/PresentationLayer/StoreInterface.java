package PresentationLayer;

import DomainLayer.Store;
import ServiceLayer.StoreService;

public class StoreInterface {
    private StoreService sr;
    private int open;

    public StoreInterface(StoreService store){
        this.sr=store;
        this.open=-1; //-1: never opened, 0: closed, 1: opened first time, 2:open
    }

    public void mainLoop(){
        System.out.println("Welcome To Our Store! \n");
        while(open==0 || open==-1){
            System.out.println("Would you like to open the store? yes/no");
            String input = System.console().readLine();
            if(input.equals("yes")){
                if(open==-1)
                    open=1;
                else
                    open=2;
            }
            if(open==1){
                System.out.println("Would you like to recover default data? yes/no");
                String in = System.console().readLine();
                if(in.equals("yes")){
                    sr.addItem("Dairy", "Milk", "Tnuva Milk 3%", 1, 1, "Tnuva", 5, 8, 1000, "01-07-2024");
                    sr.addItem("Dairy", "Milk", "Tnuva Milk 3%", 1, 2, "Tnuva", 5, 8, 1000, "10-07-2024");
                    sr.addItem("Dairy", "Yogurt", "Go Yogurt 1%", 2, 1, "Tnuva", 7, 10, 50, "05-07-2024");
                    sr.addItem("Hygiene", "Shampoo", "Herbel Essense Shampoo", 3, 1, "Yossi", 20, 30, 500, "04-07-2026");
                    sr.addItem("Hygiene", "Tooth Paste", "Colgate Tooth Paste", 4, 1, "Shai", 20, 25, 50, "04-11-2024");
                    sr.addItem("Drinks", "Juice", "Coke", 5, 1, "Coca Cola", 6, 12, 1500, "01-10-2024");
                }
                open=2;
            }
            while(open==1 || open ==2){
                System.out.println(sr.openStore());
                System.out.println("Select a number of one of the following options: \n 1- Add item. \n 2- Update damaged item. \n 3- Set discount. \n 4- Get product price. \n 5- Get periodical report. \n 6- Get stock report. \n 7- Move to store. \n 8- Close store. \n");
                String option = System.console().readLine();
                if(option.equals("1")){
                    System.out.println("What is the category of the product?");
                    String cat=System.console().readLine();
                    System.out.println("What is the sub-category of the product?");
                    String sub=System.console().readLine();
                    System.out.println("What is the name of the product?");
                    String name=System.console().readLine();
                    System.out.println("What is the serial number of the product?");
                    String serialNum=System.console().readLine();
                    System.out.println("What is the id of the item?");
                    String id=System.console().readLine();
                    System.out.println("Who is the producer of the product?");
                    String producer=System.console().readLine();
                    System.out.println("What is the cost of the product?");
                    String cost=System.console().readLine();
                    System.out.println("What will be the price of the product in the store?");
                    String price=System.console().readLine();
                    System.out.println("What is the size of the product?");
                    String size=System.console().readLine();
                    System.out.println("What is the expiration date of the product (dd-mm-yyyy)?");
                    String expDate=System.console().readLine();
                    System.out.println(sr.addItem(cat, sub, name, Integer.parseInt(serialNum), Integer.parseInt(id), producer, Integer.parseInt(cost), Integer.parseInt(price), Integer.parseInt(size), expDate));
                }
                if(option.equals("2")){
                    System.out.println("What is the category of the item?");
                    String cat=System.console().readLine();
                    System.out.println("What is the sub-category of the item?");
                    String sub=System.console().readLine();
                    System.out.println("What is the serial number of the item?");
                    String serialNum=System.console().readLine();
                    System.out.println("What is the id of the item?");
                    String id=System.console().readLine();
                    System.out.println(sr.updateDamagedItem(cat, sub, Integer.parseInt(serialNum), Integer.parseInt(id)));
                }
                if(option.equals("3")){
                    System.out.println("What is the category of the product?");
                    String cat=System.console().readLine();
                    System.out.println("What is the sub-category of the product?");
                    String sub=System.console().readLine();
                    System.out.println("What is the serial number of the product?");
                    String serialNum=System.console().readLine();
                    System.out.println("What is the discount precentage?");
                    String discount=System.console().readLine();
                    System.out.println(sr.setDiscount(cat, sub, Integer.parseInt(serialNum), Integer.parseInt(discount)));
                }
                if(option.equals("4")){
                    System.out.println("What is the category of the product?");
                    String cat=System.console().readLine();
                    System.out.println("What is the sub-category of the product?");
                    String sub=System.console().readLine();
                    System.out.println("What is the serial number of the product?");
                    String serialNum=System.console().readLine();
                    System.out.println(sr.getProductPrice(cat, sub, Integer.parseInt(serialNum)));
                }
                if(option.equals("5")){
                    System.out.println("Which category?");
                    String cat=System.console().readLine();
                    System.out.println(sr.getPeriodicalReport(cat));
                }
                if(option.equals("6")){
                    System.out.println("Which category?");
                    String cat=System.console().readLine();
                    System.out.println(sr.getStockReport(cat));
                }
                if(option.equals("7")){
                    System.out.println("What is the category of the item?");
                    String cat=System.console().readLine();
                    System.out.println("What is the sub-category of the item?");
                    String sub=System.console().readLine();
                    System.out.println("What is the serial number of the item?");
                    String serialNum=System.console().readLine();
                    System.out.println("What is the id of the item?");
                    String id=System.console().readLine();
                    System.out.println(sr.moveToStore(cat, sub, Integer.parseInt(serialNum), Integer.parseInt(id)));
                }
                if(option.equals("8")){
                    open=0;
                }
            }
        }
    }
}
