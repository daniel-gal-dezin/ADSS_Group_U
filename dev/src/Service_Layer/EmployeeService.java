package Service_Layer;

import Domain_Layer.*;

import java.util.ArrayList;
import java.util.List;

public class EmployeeService {
    private EmployeeManager em = new EmployeeManager();

    public EmployeeService(EmployeeManager em){
        this.em = em;
    }


    public String addRole(int id, String role){
        try {
            em.addRole(id, role);
            return new Response().toJson();
        }catch (Exception e){
            return new Response(e.getMessage()).toJson();
        }
    }

    public String removeRole(int id, String name, String bankAcc, Terms terms){
        em.addEmployee(id,name,bankAcc,terms);

        return new Response().toJson();
    }

    //String
    public String getEmployeeRoles(int id){
        List<Role> roles;
        try{
            roles = em.getEmployeeRoles(id);
            return new Response("",listToString(roles)).toJson();
        } catch (Exception e){
            return new Response(e.getMessage()).toJson();
        }

    }

    //String
    public String getEmployees(){
        List<Employee> emps;
        try{
            emps = em.getEmployees();
            return new Response("",listToString(emps)).toJson();
        } catch (Exception e){
            return new Response(e.getMessage()).toJson();
        }

    }

    public String setSalary(int id, int salary){
        try {
            em.setSalary(id, salary);
            return new Response().toJson();
        } catch (Exception e){
            return new Response(e.getMessage()).toJson();
        }

    }

    public String setEmplymentType(int id, String emT){
        try {
            em.setEmplymentType(id,emT);
            return new Response().toJson();
        } catch (Exception e){
            return new Response(e.getMessage()).toJson();
        }
    }

    public String setVacationDays(int id, int vd){
        try {
            em.setVacationDays(id,vd);
            return new Response().toJson();
        } catch (Exception e){
            return new Response(e.getMessage()).toJson();
        }
    }

    public String setSalaryType(int id, String st){
        try {
            em.setSalaryType(id,st);
            return new Response().toJson();
        } catch (Exception e){
            return new Response(e.getMessage()).toJson();
        }
    }

    public String setIsManager(int id, boolean m){
        try {
            em.setIsManager(id,m);
            return new Response().toJson();
        } catch (Exception e){
            return new Response(e.getMessage()).toJson();
        }
    }

    public String setBankAccount(int id, String ba){
        try {
            em.setBankAccount(id,ba);
            return new Response().toJson();
        } catch (Exception e){
            return new Response(e.getMessage()).toJson();
        }

    }

    public String setName(int id, String n){
        try {
            em.setName(id,n);
            return new Response().toJson();
        } catch (Exception e){
            return new Response(e.getMessage()).toJson();
        }

    }

    public String getEployee(int id){
        try {
            return em.getEmployee(id).toString();
        } catch (Exception e){
            return new Response(e.getMessage()).toJson();
        }
    }

    public String getHistoryEmployees(){
        return listToString(em.getHistoryEmployees());
    }

    public String deleteEmployee(int id){
        try{
            em.deleteEmployee(id);
            return new Response().toJson();
        } catch (Exception e){
            return new Response(e.getMessage()).toJson();
        }
    }

    private <T> String listToString(List<T> lst){
        String str = "";
        for(T ob : lst){
            str += ob.toString() + ",";
        }
        return str;
    }
}
