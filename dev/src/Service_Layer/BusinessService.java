package Service_Layer;

import Domain_Layer.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class BusinessService {
    BusinessManager bm;

    public BusinessService(BusinessManager bm){
        this.bm = bm;
    }


    public String setDefaultRoles(int brId, List<String> roles){
        try{
            bm.setDefaultRolesShift(brId, roles);
        } catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return new Response().toJson();
    }

        public String createBranch(String name){
        try{
            bm.createBranch(name);
        } catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return new Response().toJson();
    }

    public String setDefaultRoles(List<String> roles){
        try{
            bm.setDefaultRolesShift(roles);
        }

        catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return new Response().toJson();
    }

    public String createShiftwithroles(int brId, int year, int month, int day, String sType, List<String> roles, int managerId){
        LocalDate date = LocalDate.of(year,month,day);
        try {
            bm.createShift(brId, date, sType, roles, managerId);
        }
        catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return new Response().toJson();
    }



    public String createShiftwithdefroles(int brId, int year, int month, int day, String sType, int managerId){//with difault roles
        LocalDate date = LocalDate.of(year,month,day);
        try {
            bm.createShiftwithdefroles(brId, date, sType,managerId);
        }
        catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return new Response().toJson();
    }

    public String blockShift(int brId, int year, int month, int day, String sType){
        LocalDate date = LocalDate.of(year,month,day);
        try {
            bm.blockShift(brId, date, sType);
        }
        catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }

        return new Response().toJson();
    }

    public String unblockShift(int brId, int year, int month, int day , String sType) throws IllegalArgumentException {
        LocalDate date = LocalDate.of(year,month,day);
        try {
            bm.unblockShift(brId, date, sType);
        }
        catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return new Response().toJson();
    }

    public String changeManager(int brId, int year, int month, int day, String sType, int id){
        LocalDate date = LocalDate.of(year,month,day);
        try {
            bm.changeManager(brId, date, sType, id);
        }
        catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return new Response().toJson();
    }
    public String changeshiftDeadLine(int brId, int year, int month, int day, String sType,int year1, int month1, int day1){
        LocalDate shiftdate = LocalDate.of(year,month,day);
        LocalDate constraintdate = LocalDate.of(year1,month1,day1);
        try{
            bm.changeshiftDeadline(brId,shiftdate,sType,constraintdate);
        }
        catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return new Response().toJson();
    }

    public String changeendofmornig(int brId, int year, int month, int day, String sType, int hour,int minute){
        LocalDate shiftdate = LocalDate.of(year,month,day);
        LocalTime hour1 = LocalTime.of(hour, minute);
        try{
            bm.changeendofmorning(brId,shiftdate,sType,hour1);
        }
        catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return new Response().toJson();
    }

    public String getShiftHistory(int brId){ //can't throw
        Response r = new Response();
        r.ReturnValue = listToString(bm.getShiftHistory(brId));
        return r.toJson();
    }

    public String changeShift(int brId, int id1, int id2,int year1, int month1, int day1, String sType1,int year2, int month2, int day2, String sType2){

        LocalDate date1 = LocalDate.of(year2,month2,day2);
        LocalDate date2 = LocalDate.of(year2,month2,day2);
        try {
            bm.changeShift(brId, id1, id2, date1, sType1, date2, sType2);
        } catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return new Response().toJson();
    }

    public String changeDeadLine(int brId, int year, int month, int day, String sType,LocalDate newDate){
        if(year == 0 || month == 0 || day == 0)
            return new Response("Invalid date").toJson();
        LocalDate date = LocalDate.of(year,month,day);
        try {
            bm.changeDeadline(brId, date, sType, newDate);
        } catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return new Response().toJson();
    }

    public String addConstraint(int brId, int year, int month, int day, String sType, int id){
        if(year == 0 || month == 0 || day == 0)
            return new Response("Invalid date").toJson();
        LocalDate date = LocalDate.of(year,month,day);
        try {
            bm.addConstraint(brId, date, sType, id);
        } catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return new Response().toJson();
    }

    public String removeConstraint(int brId, int year, int month, int day, String sType, int id){
        if(year == 0 || month == 0 || day == 0)
            return new Response("Invalid date").toJson();
        LocalDate date = LocalDate.of(year,month,day);
        try {
            bm.removeConstraint(brId, date, sType, id);
        }catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return new Response().toJson();
    }

    public String getConstraints(int brId, int year, int month, int day, String sType){
        if(year == 0 || month == 0 || day == 0)
            return new Response("Invalid date").toJson();
        LocalDate date = LocalDate.of(year,month,day);
        Response r = new Response();
        try {
            r.ReturnValue = listToString(bm.getConstraints(brId, date, sType));
        } catch (Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return r.toJson();
    }

    public String getShift(int brId, int year, int month, int day, String sType){
        if(year == 0 || month == 0 || day == 0)
            return new Response("Invalid date").toJson();
        LocalDate date = LocalDate.of(year,month,day);
        Response r = new Response();
        try {
            r.ReturnValue = bm.getShift(brId, date, sType).toString();
        } catch (Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return r.toJson();
    }
    public String getAvailableEmployees(int brId, int year, int month, int day, String sType){
        if(year == 0 || month == 0 || day == 0)
            return new Response("Invalid date").toJson();
        if(year == 0 || month == 0 || day == 0)
            return new Response("Invalid date").toJson();
        LocalDate date = LocalDate.of(year,month,day);
        Response r = new Response();
        try{
            r.ReturnValue = listToString(bm.getAvailbleEmployeesForShift(brId, date, sType));
        }catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return r.toJson();
    }

    public String removeEmployeeFromShift(int brId, int year, int month, int day, String sType, int id){
        if(year == 0 || month == 0 || day == 0)
            return new Response("Invalid date").toJson();
        LocalDate date = LocalDate.of(year,month,day);
        try {
            bm.removeEmployeeFromShift(brId, date, sType, id);
        }catch(Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return new Response().toJson();
    }

    public String addEmployeeToShift(int brId, int year, int month, int day, String sType, int id){
        if(year == 0 || month == 0 || day == 0)
            return new Response("Invalid date").toJson();
        LocalDate date = LocalDate.of(year,month,day);
        try {
            bm.addEmployeetoshift(brId, date,sType, id);
        }catch (Exception e){
            return new Response(e.getMessage()).toJson();
        }
        return new Response().toJson();
    }

    private <T> String listToString(List<T> list){
        String s = "";
        for(T element:list){
            s += element + ",";
        }
        return s.substring(0,s.length() - 1);
    }

    public String checkBranch(int id){
        return bm.checkBranch(id);
    }

}
