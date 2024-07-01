package Data_Access_Layer;

import Domain_Layer.DeliveryManager;
import Domain_Layer.ShiftManager;

public class BranchDAO {
    private int id;
    private String name;

    public BranchDAO(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
