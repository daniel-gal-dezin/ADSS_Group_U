package Service_Layer;

import Domain_Layer.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ShiftService {
    EmployeeManager em;
    ShiftManager sm;

    public ShiftService(EmployeeManager em, ShiftManager sm){
        this.em = em; this.sm = sm;
    }


    public String setDefaultRoles(List<String> roles){
        try{
            sm.setDefaultRolesForShift(roles);
        }
        catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return new Response().toJson();
    }


    public String createShift(int year, int month, int day, String sType, List<Role> roles, Employee manager){
        LocalDate date = LocalDate.of(year,month,day);
        try {
            sm.createShift(date, sType, roles, manager);
        }
        catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return new Response().toJson();
    }

    public String blockShift(int year, int month, int day, String sType){
        LocalDate date = LocalDate.of(year,month,day);
        try {
            sm.blockShift(date, sType);
        }
        catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }

        return new Response().toJson();
    }

    public String unblockShift(int year, int month, int day , String sType) throws IllegalArgumentException {
        LocalDate date = LocalDate.of(year,month,day);
        try {
            sm.unblockShift(date, sType);
        }
        catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return new Response().toJson();
    }

    public String changeManager(int year, int month, int day, String sType, int id){
        LocalDate date = LocalDate.of(year,month,day);
        try {
            sm.changeManager(date, sType,getEmployee(id));
        }
        catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return new Response().toJson();
    }

    public String getShiftHistory(){ //can't throw
        Response r = new Response();
        for(Shift s: sm.getShiftHistory())
            r.ReturnValue += s.toString() +',';
        return r.toJson();
    }

    public String changeShift(int id1, int id2,int year1, int month1, int day1, String sType1,int year2, int month2, int day2, String sType2){
        LocalDate date1 = LocalDate.of(year2,month2,day2);
        LocalDate date2 = LocalDate.of(year2,month2,day2);
        try {
            sm.changeShift(getEmployee(id1), getEmployee(id2), date1, sType1, date2, sType2);
        } catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return new Response().toJson();
    }

    public String changeDeadLine(int year, int month, int day, String sType,LocalDate newDate){
        LocalDate date = LocalDate.of(year,month,day);
        try {
            sm.changeDeadLine(date, sType, newDate);
        } catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return new Response().toJson();
    }

    public String addConstraint(int year, int month, int day, String sType, int id){
        LocalDate date = LocalDate.of(year,month,day);
        try {
            sm.addConstraint(date, sType, getEmployee(id));
        } catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return new Response().toJson();
    }

    public String removeConstraint(int year, int month, int day, String sType, int id){
        LocalDate date = LocalDate.of(year,month,day);
        try {
            sm.removeConstraint(date, sType, getEmployee(id));
        }catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return new Response().toJson();
    }

    public String getConstraints(int year, int month, int day, String sType){
        LocalDate date = LocalDate.of(year,month,day);
        Response r = new Response();
        List<String> ans = new ArrayList<>();
        for(Employee e: sm.getConstraints(date, sType))
            r.ReturnValue += e.toString() +',';
        return r.toJson();
    }

    public String getAvailableEmployees(int year, int month, int day, String sType){
        LocalDate date = LocalDate.of(year,month,day);
        Response r = new Response();
        List<Employee> unAvailableEmployees = sm.getConstraints(date, sType);
        List<Employee> availableEmployees = em.getComplement(unAvailableEmployees);
        List<String> ans = new ArrayList<>();
        for(Employee e : availableEmployees)
            r.ReturnValue += e.toString() +',';
        return r.toJson();
    }

    public String removeEmployeeFromShift(int year, int month, int day, String sType, int id){
        LocalDate date = LocalDate.of(year,month,day);
        try {
            sm.removeEmployeeFromShift(date, sType, getEmployee(id));
        }catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return new Response().toJson();
    }

    public String addEmployeeToShift(int year, int month, int day, String sType, int id){
        LocalDate date = LocalDate.of(year,month,day);
        try {
            sm.addEmployeeToShift(date,sType, getEmployee(id));
        }catch (Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return new Response().toJson();
    }


    private Employee getEmployee(int id){
        return em.getEmployee(id);
    }

}
