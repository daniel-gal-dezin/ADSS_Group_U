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

        ui.main_loop();
    }
}





