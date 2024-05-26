package Domain_Layer;

import Service_Layer.EmployeeService;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

public class Employee {

    private int id;
    private String name;
    private String BankAccount;
    private List<Role> roles = new LinkedList<Role>();
    private Terms termsofem;
    private boolean ismanagar;

    private void set_props(int id, String name, String bankAccount, List<Role> roles, Terms termsofem){
        this.id = id;
        this.name = name;
        BankAccount = bankAccount;
        if(roles != null && !roles.isEmpty()){
            this.roles.addAll(roles);
        }
        this.termsofem = termsofem;
    }

//    public Employee(int id, String name, String bankAccount, List<Role> roles, Terms termsofem) {
//        set_props(id, name, bankAccount, roles, termsofem);
//    }

    public Employee(int id, String name, String bankAccount, List<Role> roles, LocalDate startWork, String employmentType, String salaryType, int salary, int vacationDays){
        Terms t = new Terms(startWork, employmentType, salaryType, salary, vacationDays);
        set_props(id, name, bankAccount, roles, t);
    }

    public void addRole(String role) {
        roles.add(convertRole(role));
    }

    public void removeRole(String role) {
        roles.remove(convertRole(role));
    }


    public void printRoles() {
        for (Role role : roles) {
            System.out.println(role);
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {//Note(Dayan): maybe unnecessary? if yes, mybe add in empleeManager
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBankAccount() {
        return BankAccount;
    }

    public void setBankAccount(String bankAccount) {
        BankAccount = bankAccount;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public boolean isIsmanagar() {
        return ismanagar;
    }

    public void setIsmanagar(boolean ismanagar) {
        this.ismanagar = ismanagar;
    }

    public Terms getTermsofem() {
        return termsofem;
    }

    public int getSalary() {
        return termsofem.Salary;
    }



    public void setTermsofem(Terms termsofem) {
        //Note(Dayan) should it include start working date?
        this.termsofem = termsofem;
    }

    public void setSalary(int salary) {
        if(salary < 0)
            throw new IllegalArgumentException("Can't apply negetive salary! inserted: " + salary);
        termsofem.Salary = salary;
    }

    public void setSalaryType(String salaryType) throws IllegalArgumentException{
        if(salaryType.toLowerCase().compareTo("hourly")==0 || salaryType.toLowerCase().compareTo("global")==0)
            this.termsofem.salaryType = salaryType;
        else
            throw new IllegalArgumentException("no such employment type. the options are hourly or global");
    }

    public void setVacationDays(int vacationDays) {
        if(vacationDays < 0)
            throw new IllegalArgumentException("Can't apply negetive vacationDays! inserted: " + vacationDays);
        termsofem.vacationDays = vacationDays;
    }

    public void setEmploymentType(String employmentType) throws IllegalArgumentException{
        if(employmentType.toLowerCase().compareTo("full")==0 || employmentType.toLowerCase().compareTo("partial")==0)
            termsofem.employmentType = employmentType;
        else
            throw new IllegalArgumentException("no such employment type. the options are full or partial");
    }

    public static Role convertRole(String role){
        if(role.toLowerCase() == "manager")
            return Role.MANAGER;
        else if(role.toLowerCase() == "storekeeper")
            return Role.STOREKEEPER;
        else if((role.toLowerCase() == "cashier"))
            return Role.CASHIER;
        else if ((role.toLowerCase() == "driver"))
            return Role.DRIVER;
        else
            throw new IllegalArgumentException("Could't add role '" + role + "'. does not exist!");

    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", BankAccount='" + BankAccount + '\'' +
                ", roles=" + roles +
                ", termsofem=" + termsofem +
                ", ismanagar=" + ismanagar +
                '}';

    }
}
