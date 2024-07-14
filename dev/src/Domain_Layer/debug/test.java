package Domain_Layer.debug;

import Domain_Layer.*;
import Domain_Layer.Repositories.BranchRepository;
import Domain_Layer.Repositories.ShiftRepository;
import Service_Layer.BusinessService;
import Service_Layer.EmployeeService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class test {
    EmployeeManager em = new EmployeeManager();
    EmployeeService es = new EmployeeService(em);
    BusinessManager bm = new BusinessManager(em);
     BusinessService bs = new BusinessService(bm);

    BranchRepository br = BranchRepository.getBranchRepository();
    ShiftRepository sr = ShiftRepository.getShiftRepository();
    //stype = morning / evening



    public test(){

       es.addEmployee(1, "a","1",LocalDate.of(2025,9,9),"Full","Global",12,1,true,'a');
       bs.createShiftwithdefroles(1,2025,10,10,"morning",1);
       es.addEmployee(1,"b","2",LocalDate.of(2025,9,9),"full","Global",12,1,true,'a');
       bs.createShiftwithdefroles(1,2025,10,10,"evening",1);
       bs.addConstraint(1,2025,10,10,"evening",2);
       bs.addEmployeeToShift(1,2025,10,10,"morning",2);
























    }





}


