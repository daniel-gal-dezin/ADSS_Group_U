package Presentation_Layer;

import Data_Access_Layer.ShiftDAO;
import Service_Layer.BusinessService;
import Service_Layer.EmployeeService;
import Service_Layer.SystemInit;

import java.time.LocalDate;

public class main {


    public static void main(String[] args) {

//        ShiftDAO s = new ShiftDAO();
//        s.getShift(LocalDate.of(2025,10,10),"morning");


        SystemInit si = new SystemInit();
        EmployeeService es = si.getEs();
        BusinessService bs = si.getBs();
        UserInterface ui = new UserInterface(si);


        //employees
        es.addEmployee(1, "a","1", LocalDate.of(2025,10,10),"Full","Global",12,1,true,'a');
        es.addEmployee(1,"b","2",LocalDate.of(2025,10,10),"full","Global",12,1,true,'b');
        es.addEmployee(1,"c","2",LocalDate.of(2025,10,10),"full","Global",12,1,true,'b');





        //createshift
        bs.createShiftwithdefroles(1,2025,10,10,"morning",1);
        bs.createShiftwithdefroles(1,2025,10,10,"evening",1);


        //addconstraint
        bs.addConstraint(1,2025,10,10,"evening",2);



        //addemployeetoshift
        bs.addEmployeeToShift(1,2025,10,10,"morning",2);
        bs.addEmployeeToShift(1,2025,10,10,"morning",3);


//        //adddelivery
        bs.addDelivery(1,2025,10,10,"morning",2,2,'b');
        bs.blockShift(1,2025,10,10,"morning");

        ui.main_loop();
    }
}





