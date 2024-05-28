package Domain_Layer;

import java.time.LocalDate;
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
    }



    public void createBranch(){
        branches.put(branch_idcounter,new Branch(branch_idcounter,shiftmanagerFactory.createShiftManager()));
        em.createBranch(branch_idcounter);
        branch_idcounter++;
    }


    public void createShift(int branchId, LocalDate date, String sType, List<String> rolesneeded, Employee manager )throws IllegalArgumentException{
        branches.get(branchId).getSm().createShift(date,sType,rolesneeded,manager);
    }
    public void createShift(int branchId,LocalDate date, String sType, Employee manage) throws IllegalArgumentException{
        branches.get(branchId).getSm().createShift(date,sType,manage);
    }
    public void setDefaultRolesShift(int branchId,List<String> roles){
        branches.get(branchId).getSm().setDefaultRolesForShift(roles);
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

    public void changeManager(int branchId,LocalDate date, String sType, Employee employee){
        branches.get(branchId).getSm().changeManager(date,sType,employee);
    }

    public void getShift(int branchId,LocalDate date, String sType){
        branches.get(branchId).getSm().getShift(date,sType);
    }


    public void getShiftHistory(int branchId){
        branches.get(branchId).getSm().getShiftHistory();
    }

    public void changeShift(int branchId,Employee e1, Employee e2, LocalDate date1, String sType1,LocalDate date2, String sType2 ){
        branches.get(branchId).getSm().changeShift(e1,e2,date1,sType1,date2,sType2);
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
        return branches.get(branchid).getSm().getConstraints(date,sType).stream().map((c) -> c.toString()).toList();
     }




    public void addEmployeeToBranch(int branchId, String name, String bankAcc, List<String> roles, String employmentType, String salaryType, int salary, int vacationDays){
        em.addEmployee(branchId,name,bankAcc,roles,LocalDate.now(),employmentType,salaryType,salary,vacationDays);
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
