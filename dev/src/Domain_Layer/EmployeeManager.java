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

    public void addEmployee(String name, String bankAcc, Terms terms){
        currentEmployees.put(next_employee_id,new Employee(next_employee_id,name,bankAcc,null,terms));
        next_employee_id++;
    }

    public void deleteEmployee(int id) throws IllegalArgumentException{
        if(currentEmployees.get(id) == null)
            throw new IllegalArgumentException("Could't delete employee #" + id + ". id not found!");
        historyEmployees.put(id,currentEmployees.get(id));
        currentEmployees.remove(id);
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

    public void getSalary(int id) throws IllegalArgumentException{
        Employee em = getEmployee(id);
        em.getSalary();
    }
    public void setName(int id, String n) throws IllegalArgumentException{
        Employee em = getEmployee(id);
        em.setName(n);
    }

    public List<Employee> getHistoryEmployees(){
        return historyEmployees.values().stream().toList();
    }

    //peulat hamashlim
    public List<Employee> getComplement(List<Employee> empl){
        List<Employee> ans = new ArrayList<>(this.currentEmployees.values());
        ans.removeAll(empl);
        return ans;
    }

}
