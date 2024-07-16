package DomainLayer;
import DataAccessLayer.ItemDTO;

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
    private ItemDTO idto;


    public Item(int productId, int id, String expDate){
        this.id=id;
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            this.expDate = LocalDate.parse(expDate, dateFormat);
        }
        catch (Exception e) {
            System.out.println("Invalid date format: " + e.getMessage());
        }
        this.place="storage";
        this.idto=new ItemDTO(productId, id, expDate, "storage");
    }

    public Item(ItemDTO i){
        this.id=i.getId();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            this.expDate = LocalDate.parse(i.getExpDate(), dateFormat);
        }
        catch (Exception e) {
            System.out.println("Invalid date format: " + e.getMessage());
        }
        this.place=i.getPlace();
        this.idto=i;
        this.idto.setPersist();
    }

    public void setPlace(String place) throws Exception {
        this.place=place;
        try
        {
            idto.updatePlace(place);
        }
        catch (Exception ex)
        {
            throw new Exception (ex.getMessage());
        }
    }

    public LocalDate getExpDate(){
        return expDate;
    }

    public int getId(){
        return this.id;
    }

    public ItemDTO getIdto(){
        return this.idto;
    }

}
