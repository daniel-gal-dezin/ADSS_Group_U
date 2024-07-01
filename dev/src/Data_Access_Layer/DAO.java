package Data_Access_Layer;

import java.sql.Date;
import java.time.LocalDate;


public abstract class DAO {




    public Date fromlocaltodate(LocalDate d){
        return new Date(d.getYear(),d.getMonthValue(),d.getDayOfMonth());
    }
}
