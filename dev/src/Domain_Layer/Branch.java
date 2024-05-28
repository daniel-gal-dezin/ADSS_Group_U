package Domain_Layer;

public class Branch {
    private int id;
    private final ShiftManager sm;

    public ShiftManager getSm() {
        return sm;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Branch(int id, ShiftManager sm){
        this.id = id;
        this.sm = sm;
    }




}
