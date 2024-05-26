package Service_Layer;
import Domain_Layer.*;

import java.util.ArrayList;

public class SystemInit {
    private EmployeeManager em;
    private ShiftManager sh;
    private ShiftService ss;
    private EmployeeService es;




    public SystemInit() {
        em = new EmployeeManager();
        sh = new ShiftManager();
        ss = new ShiftService(em, sh);
        es = new EmployeeService(em);
    }




    public ShiftService getSs() {
        return ss;
    }

    public EmployeeService getEs() {
        return es;
    }




    public void adddefualtinit() {

        Terms t = new Terms();
        t.employmentType = "full time";
        t.Salary = 10000;
        t.salaryType = "monthly";
        t.vacationDays = 21;
        es.addEmployee("dani", "123", t);
        es.addEmployee("dani2", "123", t);

        es.addRole(1, "manager");
        es.addRole(2, "worker");

        ss.createShift(2024, 5, 5, "morning", 1);//with difault roles

        ss.addEmployeeToShift(2024, 5, 5, "morning", 1);
        ss.addEmployeeToShift(2024, 5, 5, "evening", 2);

        ss.createShift(2024, 5, 6, "morning",1) ;
        ss.addConstraint(2024,5,6,"morning",2);


    }




}
