package Domain_Layer;

public class Branch {
    private int id;
    private final ShiftManager sm;

    public DeliveryManager getDm() {
        return dm;
    }

    private final DeliveryManager dm;
    private String name;

    public ShiftManager getSm() {
        return sm;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Branch(int id,String name, ShiftManager sm, DeliveryManager dm){
        this.id = id;
        this.sm = sm;
        this.dm = dm;
    }




}
