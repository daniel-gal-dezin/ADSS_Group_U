package Service_Layer;
import Domain_Layer.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

public class SystemInit {
    private EmployeeManager em;
    private BusinessService bs;
    private EmployeeService es;
    private BusinessManager bm;




    public SystemInit() {
        em = new EmployeeManager();
        es = new EmployeeService(em);
        bm = new BusinessManager(em);
        bs = new BusinessService(bm);
    }




    public BusinessService getBs() {
        return bs;
    }

    public EmployeeService getEs() {
        return es;
    }



}
