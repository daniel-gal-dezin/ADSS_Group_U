package Domain_Layer;

import java.time.LocalDate;

public class SystemManager {
    EmployeeManager em;
    ShiftManager sm;

    public SystemManager(){
        em = new EmployeeManager();
        sm = new ShiftManager();
    }

}
