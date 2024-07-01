package Data_Access_Layer;

import Domain_Layer.Employee;

public class DeliveryDAO {
    int deliveryid;
    int driverID;
    int store_keeperID;

    public DeliveryDAO(int deliveryid, int driverID, int store_keeperID) {
        this.deliveryid = deliveryid;
        this.driverID = driverID;
        this.store_keeperID = store_keeperID;
    }
}
