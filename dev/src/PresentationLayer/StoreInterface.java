package PresentationLayer;

import DomainLayer.Store;

public class StoreInterface {
    private Store store;
    private boolean open;

    public StoreInterface(Store store){
        this.store=store;
        this.open=false;
    }

    public void mainLoop(){
        System.out.println("Welcome To Our Store! \n");
        while(!open){
            System.out.println("Would you like to open the store? yes/no");
            String input = System.console().readLine();
            if(input.equals("yes")){
                open=true;
            }
        }
        while(open){
            System.out.println("Would you like to recover default data? yes/no");
            String input = System.console().readLine();
            if(input.equals("yes")){
                store.addItem("Dairy", "Milk", "Tnuva Milk 3%", 1, "Tnuva", 5, 8, 1000, "01-07-2024");
                store.addItem("Dairy", "Milk", "Tnuva Milk 3%", 1, "Tnuva", 5, 8, 1000, "10-07-2024");
                store.addItem("Dairy", "Yogurt", "Go Yogurt 1%", 2, "Tnuva", 7, 10, 50, "05-07-2024");
                store.addItem("Hygiene", "Shampoo", "Herbel Essense Shampoo", 3, "Yossi", 20, 30, 500, "04-07-2026");
                store.addItem("Hygiene", "Tooth Paste", "Colgate Tooth Paste", 4, "Shai", 20, 25, 50, "04-11-2024");
                store.addItem("Drinks", "Juice", "Coke", 5, "Coca Cola", 6, 12, 1500, "01-10-2024");
            }
            System.out.println(store.openStore());
        }
    }
}
