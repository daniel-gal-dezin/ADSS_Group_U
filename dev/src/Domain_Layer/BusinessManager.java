package Domain_Layer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BusinessManager {
    private Map<Integer,Branch> branches;
    private EmployeeManager em;
    private int branch_idcounter;
    public ShiftManagerFactory shiftmanagerFactory;
    public DeliveryManagerFactory deliverymanagerfactory;



    public BusinessManager(EmployeeManager em1){
        branches = new HashMap<>();
        em = em1;
        branch_idcounter = 1;
        shiftmanagerFactory =  () -> new ShiftManager();
        deliverymanagerfactory = () -> new DeliveryManager();
        this.createBranch("First Branch");
    }


    public String checkBranch(int id){
        return branches.containsKey(id) ? "":"Branch not exist";
    }

    public void createBranch(String name){
        branches.put(branch_idcounter,new Branch(branch_idcounter, name,shiftmanagerFactory.createShiftManager(), deliverymanagerfactory.createDeliveryManager()));
        em.createBranch(branch_idcounter);
        branch_idcounter++;
    }


    public void createShift(int branchId, LocalDate date, String sType, List<String> rolesneeded, int manager )throws IllegalArgumentException{
        branches.get(branchId).createShift(date,sType,rolesneeded,em.getEmployee(manager));
    }

    public void createShiftwithdefroles(int branchId,LocalDate date, String sType, int managerId) throws IllegalArgumentException{
        branches.get(branchId).createShiftwithdefroles(date,sType,em.getEmployee(branchId,managerId));
    }


    public void setDefaultRolesShift(int branchId,List<String> roles){
        branches.get(branchId).setDefaultRolesShift(roles);
    }

  //comment -  // function not used added to to user intrface or not needed?
    public void deleteShift(int branchId,LocalDate date, String sType ){
        branches.get(branchId).getSm().deleteShift(date,sType);
    }


    public void blockShift(int branchId,LocalDate date, String sType){
        branches.get(branchId).blockShift(date,sType);
    }

    public void unblockShift(int branchId,LocalDate date, String sType){
        branches.get(branchId).unblockshift(date,sType);
    }

    public void addEmployeetoshift(int branchId,LocalDate date, String sType, int idofemployee ){
        branches.get(branchId).addEmployeeToShift(date,sType,em.getEmployee(branchId,idofemployee));

    }

    public void changeshiftDeadline(int branchId,LocalDate date, String sType,LocalDate newdeadline){
        branches.get(branchId).changeDeadLine(date,sType,newdeadline);
    }

    public void changeendofmorning(int branchId, LocalDate date, String sType, LocalTime time){
        branches.get(branchId).changeendofmorning(date,sType,time);
    }

    public void changeManager(int branchId,LocalDate date, String sType, int employeeId){
        branches.get(branchId).changeManager(date,sType,em.getEmployee(employeeId));
    }

    public String getShift(int branchId,LocalDate date, String sType){
        return branches.get(branchId).getShift(date,sType);
    }

    public List<String> getShiftHistory(int branchId){
        return branches.get(branchId).getShiftHistory();

    }

    public void addConstraint(int branchId,LocalDate date, String sType, int idofemployee) {
        branches.get(branchId).addConstraint(date, sType, em.getEmployee(branchId, idofemployee));
    }


    public void removeConstraint(int branchId,LocalDate date, String sType, int idofemployee){
        branches.get(branchId).removeConstraint(date, sType, em.getEmployee(branchId, idofemployee));
    }

    public List<String> getConstraints(int branchid,LocalDate date, String sType){
       return branches.get(branchid).getConstraints(date, sType);
    }


//from here relevant functions for delivery










    public void changeShift(int branchId,int e1, int e2, LocalDate date1, String sType1,LocalDate date2, String sType2 ){
        if(em.getEmployee(e1).isIsmanagar() == true){
            throw new IllegalArgumentException ("can't change shift of manager please do it via change manager");
        }
        Branch b = branches.get(branchId);
        Employee em1 = em.getEmployee(e1);
        Employee em2 = em.getEmployee(e2);
        Shift s1 = b.getSm().getShift(date1,sType1);
        Shift s2 = b.getSm().getShift(date2, sType2);
        int e1type = branches.get(branchId).getDm().isDriverOrStorekeeper(em1,s1.getShiftID().getFirst(), s1.getShiftID().getSecond());
        int e2type = branches.get(branchId).getDm().isDriverOrStorekeeper(em2,s2.getShiftID().getFirst(), s2.getShiftID().getSecond());
        //if((e1type == e2type && e1type == 0)|| )
        branches.get(branchId).getSm().changeShift(em.getEmployee(e1),em.getEmployee(e2),date1,sType1,date2,sType2);
    }



    //
    public void removeEmployeeFromShift(int branchId,LocalDate date, String sType, int idofemployee) {
        Employee e = em.getEmployee(branchId, idofemployee);
        ShiftType st = convertShiftType(sType);
        if (branches.get(branchId).getDm().isDriverOrStorekeeper(e,date,st) != 0)
            throw new IllegalArgumentException("can't remove employee, he is part of delivery at this shift. Change the delivery responsibility");
        branches.get(branchId).getSm().removeEmployeeFromShift(date, sType, e);
    }









        //add to service layer
        public void addDelivery(int branchid, LocalDate date, String stype, int driverid, int storekeeperid){
        //if employee dosent exist throw error
        Employee e1 = em.getEmployee(branchid,driverid);
        Employee e2 = em.getEmployee(branchid,storekeeperid);
        branches.get(branchid).addDelivery(date,stype,e1,e2);
    }















// Employee manager domain
    public List<String> getAvailbleEmployeesForShift(int branchid,LocalDate date, String sType){
        List<Employee> ans;
        Shift s =  branches.get(branchid).getSm().getShift(date,sType);
        try {

            List<Employee> employeewithconstraint = branches.get(branchid).getSm().getConstraints(date,sType);
            List<Employee> temp = em.getComplement(1,employeewithconstraint);
            temp.remove(s.getShiftmanager());
            temp.removeAll(s.getEmployees());
            if(!temp.isEmpty())
            {return temp.stream().map((em) -> em.toString()).toList();}
            else{
                throw new IllegalArgumentException("there not availble employee");
            }


        }catch (Exception e){
            throw new IllegalArgumentException(e);
        }



    }
//comment -// Employee Manager Domain we don't use them why they here?

    public int addEmployeeToBranch(int branchId, String name, String bankAcc, List<String> roles, String employmentType, String salaryType, int salary, int vacationDays, boolean isManager){
        return em.addEmployee(branchId,name,bankAcc,LocalDate.now(),employmentType,salaryType,salary,vacationDays,isManager);
    }

    public void deleteEmployeeFromBranch(int branchId, int id){
        em.deleteEmployee(branchId,id);
    }

    public void addRole(int id, String role){
        em.addRole(id,role);
    }


    public void removeRole(int id, String role){
        removeRole(id,role);
    }

    public List<String> getEmployeeRoles(int id){
        return em.getEmployeeRoles(id).stream().map((role) -> role.toString()).toList();
    }

    public String getEmployee(int id){
        return em.getEmployee(id).toString();
    }

    public String getEmployee(int branchId, int id){
        return em.getEmployee(branchId,id).toString();
    }

    public List<String> getEmployees(int branchId){
        return em.getEmployees(branchId).stream().map((em) -> em.toString()).toList();
    }





    private ShiftType convertShiftType(String s){
        if(s.toLowerCase().compareTo("morning") == 0)
            return ShiftType.MORNING;
        else if(s.toLowerCase().compareTo("evening") == 0)
            return ShiftType.EVENING;
        else
            throw new IllegalArgumentException("no such shift type '" + s +"'. only have morning or evening");
    }


}
