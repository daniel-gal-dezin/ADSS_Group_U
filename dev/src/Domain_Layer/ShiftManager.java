package Domain_Layer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    public void setDefaultRolesForShift(List<Role> roles){
        if (!roles.contains(Role.MANAGER))
            throw new IllegalArgumentException("can't set default roles! there is no manager");
        this.defaultRolesNeeded = new ArrayList<>(roles);
    }

    //to upload the data from the database
    public ShiftManager(HashMap<Pair<LocalDate, ShiftType>, Shift> shifts, List<Pair<LocalDate, ShiftType>> blockedshift, List<Role> rolesneeded) {
        this.shifts = shifts;
        this.blockedShift = blockedshift;
        this.defaultRolesNeeded = rolesneeded;
    }

    public void createShift(Pair<LocalDate, ShiftType> shift, List<Role> rolesneeded, Employee manager) throws IllegalArgumentException{

        if (shifts.containsKey(shift))
            throw new IllegalArgumentException("Main.Shift already exists");

        if (blockedShift.contains(shift))
            throw new IllegalArgumentException("Main.Shift is blocked");

        if (shift.getFirst().isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Main.Shift is in the past");


        Shift newshift = new Shift(shift, rolesneeded,manager);
        shifts.put(shift, newshift);
    }

    //NOTE(dayan): does the low function needed? can send to the func above with roles = null

    public void createShift(Pair<LocalDate, ShiftType> shift, Employee manager) throws IllegalArgumentException {//create shift without knowing which roles needed

        if (shifts.containsKey(shift))
            throw new IllegalArgumentException("Main.Shift already exists");

        if (blockedShift.contains(shift))
            throw new IllegalArgumentException("Main.Shift is blocked");

        if (shift.getFirst().isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Main.Shift is in the past");


        Shift newshift = new Shift(shift, this.defaultRolesNeeded, manager);
        shifts.put(shift, newshift);
    }


    public void deleteShift(Pair<LocalDate, ShiftType> shift) throws IllegalArgumentException {
        if (!shifts.containsKey(shift))
            throw new IllegalArgumentException("Main.Shift does not exist");
        shifts.get(shift).removeEmployees();
        shifts.remove(shift);
    }


    public void blockShift(Pair<LocalDate, ShiftType> shift) throws IllegalArgumentException {
        if (shift.getFirst().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Main.Shift is in the past");
        }
        if (shifts.containsKey(shift)) {
            deleteShift(shift);
        }
        blockedShift.add(shift);
    }


    public void unblockShift(Pair<LocalDate, ShiftType> shift) throws IllegalArgumentException {
        if (!blockedShift.contains(shift)) {
            throw new IllegalArgumentException("Main.Shift is not blocked");
        }
        blockedShift.remove(shift);
    }

    public void addEmployeeToShift(Pair<LocalDate,ShiftType> shift, Employee employee){
        Shift s = getShift(shift);
        if(blockedShift.contains(shift)){
            throw new IllegalArgumentException("this shift is blocked!");
        }
        s.addEmployee(employee);
    }

    public void changeManager(Pair<LocalDate,ShiftType> shift, Employee employee){
        getShift(shift).setShiftmanager(employee);
    }

    private Shift getShift(Pair<LocalDate,ShiftType> s){
        if(shifts.containsKey(s)) return shifts.get(s);
        throw new IllegalArgumentException("no such shift!");
    }

    public List<Shift> getShiftHistory(){
        return shifts.values().stream().toList();
    }

    public void changeShift(Employee e1, Employee e2, Pair<LocalDate,ShiftType> s1,Pair<LocalDate,ShiftType> s2){
        Shift shift1 = getShift(s1), shift2 = getShift(s2);
        try{
            shift1.addEmployee(e2); //if throws, no actions done
        }
        catch (Exception e){
            throw new IllegalArgumentException("unable to change shift! " + e.getMessage());
        }
        try{
            shift1.removeEmployee(e1);
        }
        catch (Exception e){
            shift1.addEmployee(e2);
            throw new IllegalArgumentException("unable to change shift! " + e.getMessage());
        }
        try{
            shift2.removeEmployee(e2);
        }
        catch (Exception e){
            shift1.removeEmployee(e2);
            shift1.addEmployee(e1);
            throw new IllegalArgumentException("unable to change shift! " + e.getMessage());
        }
        try{
            shift2.removeEmployee(e2);
        }
        catch (Exception e){
            shift1.removeEmployee(e2);
            shift1.addEmployee(e1);
            shift2.addEmployee(e2);
            throw new IllegalArgumentException("unable to change shift! " + e.getMessage());
        }
    }

    public void removeEmployeeFromShift(Pair<LocalDate,ShiftType> shift, Employee employee){
        getShift(shift).removeEmployee(employee);
    }

    public void changeDeadLine(Pair<LocalDate,ShiftType> shift,LocalDate date){
        getShift(shift).setDeadLine(date);
    }

    public void addConstraint(Pair<LocalDate,ShiftType> shift, Employee em){
        getShift(shift).addConstraint(em);
    }

    public void removeConstraint(Pair<LocalDate,ShiftType> shift, Employee em){
        getShift(shift).removeConstraint(em);
    }

    public List<Employee> getConstraints(Pair<LocalDate,ShiftType> shift){
        return getShift(shift).getConstraints();
    }

}
