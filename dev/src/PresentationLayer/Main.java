package PresentationLayer;

import DomainLayer.Store;
import ServiceLayer.StoreService;

public class Main {
    public static void main(String[] args) {
        Store store=new Store();
        StoreService sr=new StoreService(store);
        StoreInterface st=new StoreInterface(sr);
        st.mainLoop();
    }
}