package Domain_Layer;

public class Delivery {
    int deliveryid;
    Employee driver;
    Employee store_keeper;



    private boolean checkDriver(Employee e){
        return e.getRoles().contains(Role.DRIVER);
    }

    private boolean checkStoreKeeper(Employee e){
        return e.getRoles().contains(Role.STOREKEEPER);
    }



    public Delivery(int deliveryid, Employee driver, Employee store_keeper) {
        if(!checkDriver(driver) || !checkStoreKeeper(store_keeper)) throw new IllegalArgumentException("must insert driver and store keeper for delivery");
        this.deliveryid = deliveryid;
        this.driver = driver;
        this.store_keeper = store_keeper;
    }

    public int getDeliveryid() {
        return deliveryid;
    }


    public Employee getDriver() {
        return driver;
    }

    public void setDriver(Employee driver) {
        if(!checkDriver(driver)) throw new IllegalArgumentException("must insert driver");
        this.driver = driver;
    }

    public Employee getStore_keeper() {
        return store_keeper;
    }

    public void setStore_keeper(Employee store_keeper) {
        if(!checkStoreKeeper(store_keeper)) throw new IllegalArgumentException("must insert store keeper");
        this.store_keeper = store_keeper;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Delivery){
            return ((Delivery)obj).getDeliveryid() == this.deliveryid;
        }
        return false;
    }
}
