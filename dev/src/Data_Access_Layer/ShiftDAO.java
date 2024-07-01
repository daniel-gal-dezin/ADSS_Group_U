package Data_Access_Layer;

import java.sql.Time;
import java.time.LocalDate;

public class ShiftDAO {
    LocalDate date;
    String sType;
    LocalDate constraintDeadLine;
    Time start;
    Time end;

    public ShiftDAO(LocalDate date, String sType, LocalDate constraintDeadLine, int start, int end) {
        this.date = date;
        this.sType = sType;
        this.constraintDeadLine = constraintDeadLine;
        this.start = new Time(start,0,0);
        this.end = new Time(end ,0,0);
    }
}
