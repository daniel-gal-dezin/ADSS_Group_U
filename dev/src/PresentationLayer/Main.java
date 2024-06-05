package PresentationLayer;

import DomainLayer.Store;

public class Main {
    public static void main(String[] args) {
        Store store=new Store();
        StoreInterface st=new StoreInterface(store);
        st.mainLoop();
    }
}