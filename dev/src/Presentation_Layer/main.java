package Presentation_Layer;

import Service_Layer.EmployeeService;
import Service_Layer.ShiftService;
import Service_Layer.SystemInit;

public class main {


    public static void main(String[] args) {
        SystemInit si = new SystemInit();

        UserInterface ui = new UserInterface(si);
        ui.main_loop();
    }
}





