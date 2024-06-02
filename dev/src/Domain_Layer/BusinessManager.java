package Domain_Layer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BusinessManager {
    private Map<Integer,Branch> branches;
    private EmployeeManager em;
    private int branch_idcounter;
    public ShiftManagerFactory shiftmanagerFactory;



    public BusinessManager(EmployeeManager em1){
        branches = new HashMap<>();
        em = em1;
        branch_idcounter = 1;
        shiftmanagerFactory =  () -> new ShiftManager();
        this.createBranch("First Branch");
    }


    public String checkBranch(int id){
        return branches.containsKey(id) ? "":"Branch not exist";
    }

    public void createBranch(String name){
        branches.put(branch_idcounter,new Branch(branch_idcounter, name,shiftmanagerFactory.createShiftManager()));
        em.createBranch(branch_idcounter);
        branch_idcounter++;
    }


    public void createShift(int branchId, LocalDate date, String sType, List<String> rolesneeded, int manager )throws IllegalArgumentException{
        branches.get(branchId).getSm().createShift(date,sType,rolesneeded,em.getEmployee(manager));
    }
    public void createShift(int branchId,LocalDate date, String sType, int managerId) throws IllegalArgumentException{
        branches.get(branchId).getSm().createShift(date,sType,em.getEmployee(branchId,managerId));
    }
    public void setDefaultRolesShift(int branchId,List<String> roles){
        branches.get(branchId).getSm().setDefaultRolesForShift(roles);
    }

    public void setDefaultRolesShift(List<String> roles){
        branches.values().stream().forEach((br) -> br.getSm().setDefaultRolesForShift(roles));
    }

    public void deleteShift(int branchId,LocalDate date, String sType ){
        branches.get(branchId).getSm().deleteShift(date,sType);
    }

    public void blockShift(int branchId,LocalDate date, String sType){
        branches.get(branchId).getSm().blockShift(date,sType);
    }

    public void unblockShift(int branchId,LocalDate date, String sType){
        branches.get(branchId).getSm().unblockShift(date,sType);
    }

    public void addEmployeetoshift(int branchId,LocalDate date, String sType, int idofemployee ){
        branches.get(branchId).getSm().addEmployeeToShift(date,sType,em.getEmployee(branchId,idofemployee));

    }

    public void changeManager(int branchId,LocalDate date, String sType, int employeeId){
        branches.get(branchId).getSm().changeManager(date,sType,em.getEmployee(employeeId));
    }

    public String getShift(int branchId,LocalDate date, String sType){
        return branches.get(branchId).getSm().getShift(date,sType).toString();
    }


    public List<String> getShiftHistory(int branchId){
        return branches.get(branchId).getSm().getShiftHistory().stream().map((shift)->shift.toString()).toList();

    }

    public void changeShift(int branchId,int e1, int e2, LocalDate date1, String sType1,LocalDate date2, String sType2 ){
        if(em.getEmployee(e1).isIsmanagar() == true){
            throw new IllegalArgumentException ("can't change shift of manager please do it via change manager");
        }
        if(em.getEmployee(e2).isIsmanagar() == true){
            throw new IllegalArgumentException ("can't change shift of manager please do it via change manager");
        }
        branches.get(branchId).getSm().changeShift(em.getEmployee(e1),em.getEmployee(e2),date1,sType1,date2,sType2);
    }

    public void removeEmployeeFromShift(int branchId,LocalDate date, String sType, int idofemployee) {
        branches.get(branchId).getSm().removeEmployeeFromShift(date, sType, em.getEmployee(branchId, idofemployee));
    }

    public void changeDeadline(int branchId,LocalDate date, String sType,LocalDate newDte){
        branches.get(branchId).getSm().changeDeadLine(date,sType,newDte);
    }
     public void addConstraint(int branchId,LocalDate date, String sType, int idofemployee) {
         branches.get(branchId).getSm().addConstraint(date, sType, em.getEmployee(branchId, idofemployee));
     }
     public void removeConstraint(int branchId,LocalDate date, String sType, int idofemployee){
         branches.get(branchId).getSm().removeConstraint(date, sType, em.getEmployee(branchId, idofemployee));
     }

     public List<String> getConstraints(int branchid,LocalDate date, String sType){
         List<Employee> ans;
         try {

             List<Employee> employeewithconstraint = branches.get(branchid).getSm().getConstraints(date,sType);
             return em.getComplement(1,employeewithconstraint).stream().map((em) -> em.toString()).toList();


         }catch (Exception e){
             throw new IllegalArgumentException(e);
         }



    }




    public void addEmployeeToBranch(int branchId, String name, String bankAcc, List<String> roles, String employmentType, String salaryType, int salary, int vacationDays, boolean isManager){
        em.addEmployee(branchId,name,bankAcc,LocalDate.now(),employmentType,salaryType,salary,vacationDays,isManager);
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


}
