package Service_Layer;
import Domain_Layer.*;

public class SystemInit {
    private EmployeeManager em;
    private BusinessService ss;
    private EmployeeService es;
    private BusinessManager bm;




    public SystemInit() {
        em = new EmployeeManager();
        es = new EmployeeService(em);
        bm = new BusinessManager(em);
        ss = new BusinessService(bm);
    }




    public BusinessService getSs() {
        return ss;
    }

    public EmployeeService getEs() {
        return es;
    }




    public void adddefualtinit() {
        //TODO: edit this to work



    }




}
