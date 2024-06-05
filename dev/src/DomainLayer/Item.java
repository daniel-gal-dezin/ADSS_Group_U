package DomainLayer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.LinkedHashMap;
public class Item {
    private int id;
    private LocalDate expDate;
    public String place;


    public Item(int id, String expDate){ //implement place
        this.id=id;
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            this.expDate = LocalDate.parse(expDate, dateFormat);
        }
        catch (Exception e) {
            System.out.println("Invalid date format: " + e.getMessage());
        }
        this.place="storage";
    }

    public void setPlace(String place){
        this.place=place;
    }

    public LocalDate getExpDate(){
        return expDate;
    }

    public int getId(){
        return this.id;
    }

}
