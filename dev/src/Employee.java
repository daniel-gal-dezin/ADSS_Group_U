import java.security.PublicKey;
import java.util.List;

public class Employee {
    public enum Role {
        MANAGER,
        STOREKEEPER,
        CASHIER,
        DRIVER
    }



    private int id;
    private String name;
    private String BankAccount;
    private List<Role> roles;
    private Terms termsofem;
    private boolean ismanagar;


    public Employee() {
    }

    public Employee(int id, String name, String bankAccount, List<Role> roles, Terms termsofem) {
        this.id = id;
        this.name = name;
        BankAccount = bankAccount;
        this.roles = roles;
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

    public void setId(int id) {
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

    public Terms getTermsofem() {
        return termsofem;
    }

    public void setTermsofem(Terms termsofem) {
        this.termsofem = termsofem;
    }
    public boolean isIsmanagar() {
        return ismanagar;
    }

    public void setIsmanagar(boolean ismanagar) {
        this.ismanagar = ismanagar;
    }
}