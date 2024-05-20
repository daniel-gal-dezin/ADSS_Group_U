package Domain_Layer;

import java.util.LinkedList;
import java.util.List;

public class Employee {

    private int id;
    private String name;
    private String BankAccount;
    private List<Role> roles = new LinkedList<Role>();
    private Terms termsofem;
    private boolean ismanagar;

    public Employee(int id, String name, String bankAccount, List<Role> roles, Terms termsofem) {
        this.id = id;
        this.name = name;
        BankAccount = bankAccount;
        if(roles != null && !roles.isEmpty()){
            this.roles.addAll(roles);
        }
        this.termsofem = termsofem;

    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public void removeRole(Role role) {
        roles.remove(role);
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


    @Override
    public String toString() {
        //TODO: implement
        return "";
    }
}
