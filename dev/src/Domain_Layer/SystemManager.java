package Domain_Layer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SystemManager {
    EmployeeManager em;
    ShiftManager sm;

    public SystemManager(){
        em = new EmployeeManager();
        sm = new ShiftManager();
    }

    public void addEmployeeToShift(LocalDate date, String sType, int id){
        sm.addEmployeeToShift(new Pair<>(date,convertShiftType(sType)),getEmployee(id));
    }

    public void removeEmployeeFromShift(LocalDate date, String sType, int id){
        sm.removeEmployeeFromShift(new Pair<>(date,convertShiftType(sType)),getEmployee(id));
    }

    public void addRole(int id, String role){
        em.addRole(id,convertRole(role));
    }

    public void removeRole(int id, String name,String bankAcc,Terms terms){
        em.addEmployee(id,name,bankAcc,terms);
    }

    public List<Role> getEmployeeRoles(int id){
        return em.getEmployeeRoles(id);
    }

    private Employee getEmployee(int id){
        return em.getEmployee(id);
    }

    public List<Employee> getEmployees(){
        return em.getEmployees();
    }

    public void setSalary(int id, int salary){
        em.setSalary(id,salary);
    }

    public void setEmplymentType(int id, String emT){
        em.setEmplymentType(id,emT);
    }

    public void setVacationDays(int id, int vd){
        em.setVacationDays(id,vd);
    }

    public void setSalaryType(int id, String st){
        em.setSalaryType(id,st);
    }

    public void setIsManager(int id, boolean m){
        em.setIsManager(id,m);
    }

    public void setBankAccount(int id, String ba){
        em.setBankAccount(id,ba);
    }

    public void setName(int id, String n){
        em.setName(id,n);
    }

    public List<Employee> getHistoryEmployees(){
        return em.getHistoryEmployees();
    }

    public void setDefaultRoles(List<String> roles){
        List<Role> realRoles = new ArrayList<>();
        for(String role: roles){
            realRoles.add(convertRole(role));
        }
        sm.setDefaultRolesForShift(realRoles);
    }

    public void createShift(LocalDate date, String sType, List<Role> roles, Employee manager){
        sm.createShift(new Pair<>(date,convertShiftType(sType)), roles, manager);
    }

    public void blockShift(LocalDate date, String sType){
        sm.blockShift(new Pair<>(date,convertShiftType(sType)));
    }

    public void unblockShift(LocalDate date, String sType) throws IllegalArgumentException {
        sm.unblockShift(new Pair<>(date,convertShiftType(sType)));
    }

    private ShiftType convertShiftType(String s){
        if(s.toLowerCase().compareTo("morning") != 0)
            return ShiftType.MORNING;
        else if(s.toLowerCase().compareTo("evening") != 0)
            return ShiftType.EVENING;
        else
            throw new IllegalArgumentException("no such shift type '" + s +"'. only have morning or evening");
    }

    private Role convertRole(String role){
        if(role.toLowerCase().compareTo("manager") != 0)
            return Role.MANAGER;
        else if(role.toLowerCase().compareTo("storekeeper") != 0)
            return Role.STOREKEEPER;
        else if((role.toLowerCase().compareTo("cashier") != 0))
            return Role.CASHIER;
        else if ((role.toLowerCase().compareTo("driver") != 0))
            return Role.DRIVER;
        else
            throw new IllegalArgumentException("Could't add role '" + role + "'. does not exist!");

    }


    public void changeManager(LocalDate date, String sType, int id){
        sm.changeManager(new Pair<>(date,convertShiftType(sType)),getEmployee(id));
    }

    public List<String> getShiftHistory(){
        List<String> ans = new ArrayList<>();
        for(Shift s: sm.getShiftHistory())
            ans.add(s.toString());
        return ans;
    }

    public void changeShift(int id1, int id2,LocalDate date1, String sType1,LocalDate date2, String sType2){
        sm.changeShift(getEmployee(id1),getEmployee(id2),new Pair<>(date1,convertShiftType(sType1)),new Pair<>(date2,convertShiftType(sType2)));
    }

    public void changeDeadLine(LocalDate date, String sType,LocalDate newDate){
        sm.changeDeadLine(new Pair<>(date,convertShiftType(sType)),newDate);
    }

    public void addConstraint(LocalDate date, String sType, int id){
        sm.addConstraint(new Pair<>(date,convertShiftType(sType)),getEmployee(id));
    }

    public void removeConstraint(LocalDate date, String sType, int id){
        sm.removeConstraint(new Pair<>(date,convertShiftType(sType)),getEmployee(id));
    }

    public List<String> getConstraints(LocalDate date, String sType){
        List<String> ans = new ArrayList<>();
        for(Employee e: sm.getConstraints(new Pair<>(date,convertShiftType(sType))))
            ans.add(e.toString());
        return ans;
    }

    public List<String> getAvailableEmployees(LocalDate date, String sType){
        List<Employee> unAvailableEmployees = sm.getConstraints(new Pair<>(date,convertShiftType(sType)));
        List<Employee> availableEmployees = em.getComplement(unAvailableEmployees);
        List<String> ans = new ArrayList<>();
        for(Employee e : availableEmployees){
            ans.add(e.toString());
        }
        return ans;
    }
}

