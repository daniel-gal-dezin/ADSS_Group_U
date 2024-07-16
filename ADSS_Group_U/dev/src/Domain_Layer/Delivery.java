package Domain_Layer;

public class Delivery {
    int deliveryid;
    Employee driver;
    Employee store_keeper;
    char licenseneeded;
    int productId;
    int itemId;

    public void setDeliveryid(int deliveryid) {
        this.deliveryid = deliveryid;
    }

    public void setLicenseneeded(char licenseneeded) {
        this.licenseneeded = licenseneeded;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public char getLicenseneeded() {
        return licenseneeded;
    }





    private boolean checkDriver(Employee e){
        return e.getRoles().contains(Role.DRIVER);
    }

    private boolean checkStoreKeeper(Employee e){
        return e.getRoles().contains(Role.STOREKEEPER);
    }



    public Delivery(int deliveryid, Employee driver, Employee store_keeper ,char license, int proudctId,int itemId) {
        this.deliveryid = deliveryid;
        this.licenseneeded = license;
        setDriver(driver);
        setStore_keeper(store_keeper);
        productId = proudctId;
        this.itemId = itemId;
    }



    public int getDeliveryid() {
        return deliveryid;
    }


    public Employee getDriver() {
        return driver;
    }


    public void setDriver(Employee driver) {
        if(!checkDriver(driver)) throw new IllegalArgumentException("must insert driver");
        if(driver.getLicense() < this.licenseneeded) // if he has 'a' but need 'b' or 'c'\
            throw new IllegalArgumentException("must insert driver with appropriate license");
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
