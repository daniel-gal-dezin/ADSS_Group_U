package DomainLayer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
public class Item {
    private int id;
    private Date expDate;
    public String place;


    public Item(int id, String expDate){ //implement place
        this.id=id;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            this.expDate = dateFormat.parse(expDate);
        }
        catch (ParseException e) {
            System.out.println("Invalid date format: " + e.getMessage());
        }
        this.place="storage";
    }

    public void setPlace(String place){
        this.place=place;
    }

}
