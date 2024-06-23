package PresentationLayer;

import DomainLayer.StoreManager;
import ServiceLayer.StoreService;

public class Main {
    public static void main(String[] args) {
        StoreManager store=new StoreManager();
        StoreService sr=new StoreService(store);
        StoreInterface st=new StoreInterface(sr);
        st.mainLoop();
    }
}