package Domain_Layer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ShiftManager {
    private HashMap<Pair<LocalDate, ShiftType>, Shift> shifts;
    private List<Pair<LocalDate, ShiftType>> blockedShift;
    private List<Role> defaultRolesNeeded;


    public ShiftManager() {//with diffualt roles needed
        shifts = new HashMap<>();
        blockedShift = new ArrayList<>();
        this.defaultRolesNeeded = createDefaultRolesNeeded();
    }


    private List<Role> createDefaultRolesNeeded() {
        List<Role> rolesneeded1 = new ArrayList<>();
        rolesneeded1.add(Role.CASHIER);// defualt role needed
        rolesneeded1.add(Role.DRIVER);
        rolesneeded1.add(Role.MANAGER);
        rolesneeded1.add(Role.STOREKEEPER);
        return rolesneeded1;
    }

public void setDefaultRolesForShift(List<String> roles){
        List<Role> rolesR = roles.stream().map((role) -> convertRole(role)).collect(Collectors.toList());
        if (!rolesR.contains(Role.MANAGER))
            throw new IllegalArgumentException("can't set default roles! there is no manager");
        this.defaultRolesNeeded = rolesR;
    }

    //to upload the data from the database
    public ShiftManager(HashMap<Pair<LocalDate, ShiftType>, Shift> shifts, List<Pair<LocalDate, ShiftType>> blockedshift, List<Role> rolesneeded) {
        this.shifts = shifts;
        this.blockedShift = blockedshift;
        this.defaultRolesNeeded = rolesneeded;
    }

    public void createShift(LocalDate date, String sType, List<String> rolesneeded, Employee manager) throws IllegalArgumentException{//
        Pair<LocalDate,ShiftType> shift = new Pair<>(date,convertShiftType(sType));
        if (shifts.containsKey(shift))
            throw new IllegalArgumentException("Main.Shift already exists");

        if (blockedShift.contains(shift))
            throw new IllegalArgumentException("Main.Shift is blocked");

        if (shift.getFirst().isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Main.Shift is in the past");

        List<Role> roles = rolesneeded.stream().map((r)->convertRole(r)).collect(Collectors.toList());
        Shift newshift = new Shift(shift,roles,manager);
        shifts.put(shift, newshift);
    }

    //NOTE(dayan): does the low function needed? can send to the func above with roles = null

    public void createShift(LocalDate date, String sType, Employee manager) throws IllegalArgumentException {//create shift without knowing which roles needed
        Pair<LocalDate,ShiftType> shift = new Pair<>(date,convertShiftType(sType));
        if (shifts.containsKey(shift))
            throw new IllegalArgumentException("Main.Shift already exists");

        if (blockedShift.contains(shift))
            throw new IllegalArgumentException("Main.Shift is blocked");

        if (shift.getFirst().isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Main.Shift is in the past");


        Shift newshift = new Shift(shift, this.defaultRolesNeeded, manager);
        shifts.put(shift, newshift);
    }


    public void deleteShift(LocalDate date, String sType) throws IllegalArgumentException {//
        Shift shift = getShift(date,sType);
//        shifts.get(shift).removeEmployees();
        shifts.remove(shift);
    }


    public void blockShift(LocalDate date, String sType) throws IllegalArgumentException {//
        Pair<LocalDate,ShiftType> shift = new Pair<>(date,convertShiftType(sType));
        if (shift.getFirst().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Main.Shift is in the past");
        }
        if (shifts.containsKey(shift)) {
            deleteShift(date, sType);
        }
        blockedShift.add(shift);
    }


    public void unblockShift(LocalDate date, String sType) throws IllegalArgumentException {//
        Pair<LocalDate,ShiftType> shift = new Pair<>(date,convertShiftType(sType));
        if (!blockedShift.contains(shift)) {
            throw new IllegalArgumentException("Main.Shift is not blocked");
        }
        blockedShift.remove(shift);
    }

    public void addEmployeeToShift(LocalDate date, String sType, Employee employee){//
        Shift shift = getShift(date,sType);
        if(blockedShift.contains(shift.getShiftID())){
            throw new IllegalArgumentException("this shift is blocked!");
        }
        shift.addEmployee(employee);
    }

    public void changeManager(LocalDate date, String sType, Employee employee){//
        Shift shift = getShift(date,sType);
        shift.setShiftmanager(employee);
    }

    public Shift getShift(LocalDate date, String sType){//
        Pair<LocalDate,ShiftType> s = new Pair<>(date,convertShiftType(sType));
        if(shifts.containsKey(s)) return shifts.get(s);
        throw new IllegalArgumentException("no such shift!");
    }

    public List<Shift> getShiftHistory(){
        return shifts.values().stream().toList();
    }//

    public void changeShift(Employee e1, Employee e2, LocalDate date1, String sType1,LocalDate date2, String sType2){//
        Shift s1 = getShift(date1,sType1);
        Shift s2 = getShift(date2,sType2);
        //Shift shift1 = getShift(s1), shift2 = getShift(s2);
        try{
            s1.addEmployee(e2); //if throws, no actions done
        }
        catch (Exception e){
            throw new IllegalArgumentException("unable to change shift! " + e.getMessage());
        }
        try{
            s2.addEmployee(e1);
        }
        catch (Exception e){
            s1.removeEmployee(e2);
            throw new IllegalArgumentException("unable to change shift! " + e.getMessage());
        }
        try{
            s1.removeEmployee(e2);
        }
        catch (Exception e){
            s1.removeEmployee(e2);
            s2.removeEmployee(e1);
            throw new IllegalArgumentException("unable to change shift! " + e.getMessage());
        }
        try{
            s2.removeEmployee(e1);
        }
        catch (Exception e){
            s1.removeEmployee(e2);
            s2.removeEmployee(e1);
            s1.addEmployee(e2);
            throw new IllegalArgumentException("unable to change shift! " + e.getMessage());
        }
    }

    public void removeEmployeeFromShift(LocalDate date, String sType, Employee employee){
        Shift shift = getShift(date,sType);
        shift.removeEmployee(employee);
    }//

    public void changeDeadLine(LocalDate date, String sType,LocalDate newDte){
        Shift shift = getShift(date,sType);
        shift.setDeadLine(date);
    }//

    public void addConstraint(LocalDate date, String sType, Employee em){
        Shift shift = getShift(date,sType);
        shift.addConstraint(em);
    }

    public void removeConstraint(LocalDate date, String sType, Employee em){
        Shift shift = getShift(date,sType);
        shift.removeConstraint(em);
    }

    public List<Employee> getConstraints(LocalDate date, String sType){
        Shift shift = getShift(date,sType);
        return shift.getConstraints();
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
}
