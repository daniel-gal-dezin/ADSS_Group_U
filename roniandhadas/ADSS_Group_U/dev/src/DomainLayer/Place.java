package DomainLayer;

public class Place {
    private int aigle;
    private int shelf;

    public Place(int aigle, int shelf){
        this.aigle=aigle;
        this.shelf=shelf;
    }

    public String getStringPlace(){
        return "Aigle: " + this.aigle + ", Shelf: " + this.shelf;
    }
}
