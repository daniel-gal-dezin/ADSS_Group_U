package Domain_Layer;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class EmployeeManager {

    private Map<Integer, List<Employee>> branchEmployees;
    private Map<Integer,Employee> currentEmployees;
    private Map<Integer,List<Employee>> branchHistoryEmployees;
    private  Integer next_employee_id;

    public EmployeeManager(){
        currentEmployees = new HashMap<>();
        branchHistoryEmployees = new HashMap<>();
        branchEmployees = new HashMap<>();
        next_employee_id = 1;
    }

//    public EmployeeManager(Map<Integer,Map<Integer,Employee>> emplo){
//        branchHistoryEmployees = new HashMap<>();
//
//        for(Map.Entry<Integer,Map<Integer,Employee>> branch : emplo){
//            for()
//        }
//        //emplo.values()
//    }

    public void createBranch(int id){
        branchHistoryEmployees.put(id,new ArrayList<>());
        branchEmployees.put(id,new ArrayList<>());
    }



    public void addEmployee(int branchId, String name, String bankAcc, LocalDate startWork, String employmentType, String salaryType, int salary, int vacationDays,boolean isManager){
        List<Role> r = new ArrayList<>();
        r.add(Role.CASHIER);
        r.add(Role.STOREKEEPER);
        r.add(Role.DRIVER);
        if(isManager) r.add(Role.MANAGER);
        int id;
        try {
            id = next_employee_id;
            next_employee_id++;
        } catch (Exception e){
            throw new IllegalArgumentException("branch ID doesn't exist");
        }
        Employee newEmp = new Employee(id,name,bankAcc, r, startWork,employmentType,salaryType,salary,vacationDays,isManager);
        currentEmployees.put(id,newEmp);
        branchEmployees.get(branchId).add(newEmp);

    }

    public void deleteEmployee(int branchId, int id) throws IllegalArgumentException{
        if(currentEmployees.get(id) == null)
            throw new IllegalArgumentException("Could't delete employee #" + id + ". id not found!");

        try{
            branchHistoryEmployees.get(branchId).add(currentEmployees.get(id));
        } catch (Exception e){
            throw new IllegalArgumentException("branch ID doesn't exist");
        }
        currentEmployees.remove(id);
        branchEmployees.get(branchId).remove(id);
    }

    public void addRole(int id, String role) throws IllegalArgumentException{
        if(!currentEmployees.containsKey(id))
            throw new IllegalArgumentException("Could't add role to employee #" + id + ". id not found!");
        Employee em = getEmployee(id);
        em.addRole(role);
        if(role.toLowerCase() == "manager" && !em.isIsmanagar())
            em.setIsmanagar(true);
    }
    public void removeRole(int id, String role) throws IllegalArgumentException{
        Employee em = getEmployee(id);
        em.removeRole(role);
    }

    public List<Role> getEmployeeRoles(int id) throws IllegalArgumentException{
        Employee em =getEmployee(id);
        return em.getRoles();
    }

    public Employee getEmployee(int id) throws IllegalArgumentException{
        if(!currentEmployees.containsKey(id))
         throw new IllegalArgumentException("Could't get employee #" + id + ". id not found!");
        Employee em = currentEmployees.get(id);
        return em;
    }

    public Employee getEmployee(int branchId, int id){
        if(!currentEmployees.containsKey(id))
            throw new IllegalArgumentException("Could't get employee #" + id + ". id not found!");
        List<Employee> bEmpls = branchEmployees.get(branchId);
        for(Employee e : bEmpls){
            if(e.getId() == id)
                return e;
        }
        throw new IllegalArgumentException("Couln't find Employee " + id + ".\nfount it in currentEmployes, but not in branchEmployee");
    }

    /**
     *  this function return list of all employees.
     * @return A list of all current employees
     */
    public List<Employee> getEmployees(){
        return this.currentEmployees.values().stream().toList();
    }

    /**
     *
     * @param branchId
     * @return A list of all employees by branch
     */
    public List<Employee> getEmployees(int branchId){
        try{
            return this.branchEmployees.get(branchId).stream().toList();
        } catch (Exception e){
            throw new IllegalArgumentException("branch ID doesn't exist");
        }
    }

    public void setSalary(int id, int salary) throws IllegalArgumentException{
        Employee em = getEmployee(id);
        em.setSalary(salary);
    }

    public void setEmplymentType(int id, String emT) throws IllegalArgumentException{
        Employee em = getEmployee(id);
        em.setEmploymentType(emT);
    }

    public void setVacationDays(int id, int vd) throws IllegalArgumentException{
        Employee em = getEmployee(id);
        em.setVacationDays(vd);
    }

    public void setSalaryType(int id, String st) throws IllegalArgumentException{
        Employee em = getEmployee(id);
        em.setSalaryType(st);
    }

    public void setIsManager(int id, boolean m) throws IllegalArgumentException{
        Employee em = getEmployee(id);
        em.setIsmanagar(m);
    }

    public void setBankAccount(int id, String ba) throws IllegalArgumentException{
        Employee em = getEmployee(id);
        em.setBankAccount(ba);
    }

    public void getSalary(int id) throws IllegalArgumentException{
        Employee em = getEmployee(id);
        em.getSalary();
    }
    public void setName(int id, String n) throws IllegalArgumentException{
        Employee em = getEmployee(id);
        em.setName(n);
    }

    public List<Employee> getHistoryEmployees(int branchId){
        return branchHistoryEmployees.get(branchId).stream().toList();
    }

    //peulat hamashlim
    public List<Employee> getComplement(int branchId, List<Employee> empl){
        List<Employee> ans;
        try {
            ans = new ArrayList<>(this.branchEmployees.get(branchId));
        }catch (Exception e){
            throw new IllegalArgumentException("branch ID doesn't exist");
        }
        ans.removeAll(empl);
        return ans;
    }

    public Role convertRole(String role){
        if(role.toLowerCase().compareTo("manager") == 0)
            return Role.MANAGER;
        else if(role.toLowerCase().compareTo("storekeeper") == 0)
            return Role.STOREKEEPER;
        else if((role.toLowerCase().compareTo("cashier") == 0))
            return Role.CASHIER;
        else if ((role.toLowerCase().compareTo("driver") == 0))
            return Role.DRIVER;
        else
            throw new IllegalArgumentException("Could't add role '" + role + "'. does not exist!");

    }
}
