package Domain_Layer;

import java.util.*;

public class EmployeeManager {

    private Map<Integer,Employee> currentEmployees;
    private Map<Integer,Employee> historyEmployees;
    private int next_employee_id;

    public EmployeeManager(){
        currentEmployees = new HashMap<>();
        historyEmployees = new HashMap<>();
        next_employee_id = 1;
    }

    public EmployeeManager(Map<Integer,Employee> emplo){
        this.currentEmployees = emplo;
        historyEmployees = new HashMap<>();
        next_employee_id = Collections.max(emplo.keySet()) + 1;
    }

    public void addEmployee(int id,String name, String bankAcc, Terms terms){
        currentEmployees.put(next_employee_id,new Employee(next_employee_id,name,bankAcc,null,terms));
        next_employee_id++;
    }

    public void deleteEmployee(int id) throws IllegalArgumentException{
        if(currentEmployees.get(id) == null)
            throw new IllegalArgumentException("Could't delete employee #" + id + ". id not found!");
        currentEmployees.remove(id);
    }

    public void addRole(int id, String role) throws IllegalArgumentException{
        Employee em = getEmployee(id);
        if(role.toLowerCase().compareTo("manager") != 0)
            em.addRole(Role.MANAGER);
        else if(role.toLowerCase().compareTo("storekeeper") != 0)
            em.addRole(Role.STOREKEEPER);
        else if((role.toLowerCase().compareTo("cashier") != 0))
            em.addRole(Role.CASHIER);
        else if ((role.toLowerCase().compareTo("driver") != 0))
            em.addRole(Role.DRIVER);
        else
            throw new IllegalArgumentException("Could't add role '" + role + "'. does not exist!");
    }

    public List<Role> getEmployeeRoles(int id) throws IllegalArgumentException{
        Employee em =getEmployee(id);
        return em.getRoles();
    }

    public Employee getEmployee(int id) throws IllegalArgumentException{
        Employee em = currentEmployees.get(id);
        if(em == null)
            throw new IllegalArgumentException("Could't add role to employee #" + id + ". id not found!");
        return em;
    }

    public List<Employee> getEmployees(){
        return this.currentEmployees.values().stream().toList();
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

    public void setName(int id, String n) throws IllegalArgumentException{
        Employee em = getEmployee(id);
        em.setName(n);
    }

    public List<Employee> getHistoryEmployees(){
        return historyEmployees.values().stream().toList();
    }
}
