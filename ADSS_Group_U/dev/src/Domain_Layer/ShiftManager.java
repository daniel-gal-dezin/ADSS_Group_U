package Domain_Layer;

import Domain_Layer.Repositories.ShiftRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ShiftManager {
    private HashMap<Pair<LocalDate, ShiftType>, Shift> shifts;
    private List<Pair<LocalDate, ShiftType>> blockedShift;
    private List<Role> defaultRolesNeeded;
    private int branchid;


    public ShiftManager(int branchid) {//with diffualt roles needed
        shifts = new HashMap<>();
        blockedShift = new ArrayList<>();
        this.defaultRolesNeeded = createDefaultRolesNeeded();
        this.branchid = branchid;
    }

    public ShiftManager(int bId, HashMap<Pair<LocalDate, ShiftType>, Shift> shifts,List<Pair<LocalDate, ShiftType>> blockedShift){
        this.shifts=shifts;
        this.blockedShift = blockedShift;
        this.branchid = bId;
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

    public Shift createShift(LocalDate date, String sType, List<String> rolesneeded, Employee manager) throws IllegalArgumentException{//
        Pair<LocalDate,ShiftType> shift = new Pair<>(date,convertShiftType(sType));
        if (shifts.containsKey(shift))
            throw new IllegalArgumentException("Main.Shift already exists");

        if (blockedShift.contains(shift))
            throw new IllegalArgumentException("Main.Shift is blocked");

        if (!shift.getFirst().isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Main.Shift is in the past");
        if(manager == null)
            throw new IllegalArgumentException("can't create a shift, need a manager! the employee inserted isn't one.");

        List<Role> roles = rolesneeded.stream().map((r)->convertRole(r)).collect(Collectors.toList());
        Shift newshift = new Shift(shift,roles,manager);
        shifts.put(shift, newshift);
        ShiftRepository.getShiftRepository().insertShift(newshift,this.branchid);
        return newshift;
    }

    //NOTE(dayan): does the low function needed? can send to the func above with roles = null

    public Shift createShift(LocalDate date, String sType, Employee manager) throws IllegalArgumentException {//create shift without knowing which roles needed
        Pair<LocalDate,ShiftType> shift = new Pair<>(date,convertShiftType(sType));
        if (shifts.containsKey(shift))
            throw new IllegalArgumentException("Main.Shift already exists");

        if (blockedShift.contains(shift))
            throw new IllegalArgumentException("Main.Shift is blocked");

        if (!shift.getFirst().isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Main.Shift is in the past");
        if(manager == null){
            throw new IllegalArgumentException("can't create a shift, need a manager! the employee inserted isn't one.");
        }


        Shift newshift = new Shift(shift, this.defaultRolesNeeded, manager);
        shifts.put(shift, newshift);
        return newshift;
    }


    public void deleteShift(LocalDate date, String sType) throws IllegalArgumentException {//
        Shift shift = getShift(date,sType);
//        shifts.get(shift).removeEmployees();
        shifts.remove(shift);
        ShiftRepository.getShiftRepository().deleteShift(shift,branchid);
    }


    public void blockShift(LocalDate date, String sType) throws IllegalArgumentException {//

        Pair<LocalDate,ShiftType> shift = new Pair<>(date,convertShiftType(sType));
        if (!shift.getFirst().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Main.Shift is in the past");
        }
        if (shifts.containsKey(shift)) {
            deleteShift(date, sType);
        }
        blockedShift.add(shift);
        ShiftRepository.getShiftRepository().insertBlockedShift(date,sType,this.branchid);
    }


    public void unblockShift(LocalDate date, String sType) throws IllegalArgumentException {//
        Pair<LocalDate,ShiftType> shift = new Pair<>(date,convertShiftType(sType));
        if (!blockedShift.contains(shift)) {
            throw new IllegalArgumentException("Main.Shift is not blocked");
        }
        blockedShift.remove(shift);
        ShiftRepository.getShiftRepository().deleteBlockedShift(date,sType,this.branchid);
    }

    public void setEndOfMorning(LocalDate date, String sType, LocalTime time){
        Shift shift1 = this.getShift(date,sType);
        shift1.setEndMorning(time);
        ShiftRepository.getShiftRepository().updateShift(shift1);
    }

    public void addEmployeeToShift(LocalDate date, String sType, Employee employee){//
        Shift shift = getShift(date,sType);
        if(blockedShift.contains(shift.getShiftID())){
            throw new IllegalArgumentException("this shift is blocked!");
        }
        if(LocalDate.now().isAfter(shift.getDeadLine())){
            throw new IllegalArgumentException("the chance to submit constraint has pass");
        }
        if(!shift.getRolesneeded().containsAll(employee.getRoles())){
            throw new IllegalArgumentException("can't add employee to shift! he doesn't have the needed roles");
        }
        if(shift.getEmployees().contains(employee)){
            throw new IllegalArgumentException("can't add employee to shift! He is already in it");
        }
        if(shift.getConstraints().contains(employee)){
            throw new IllegalArgumentException("can't add employee to shift! He is in the constraints");
        }
        shift.addEmployee(employee);
        ShiftRepository.getShiftRepository().addWorkerToShift(date,sType, employee.getId());
    }

    public void changeManager(LocalDate date, String sType, Employee employee){//
        Shift shift = getShift(date,sType);
        shift.setShiftmanager(employee);
        ShiftRepository.getShiftRepository().updateShift(shift);
    }

    public Shift getShift(LocalDate date, String sType){//
        Pair<LocalDate,ShiftType> s = new Pair<>(date,convertShiftType(sType));

        for (Pair<LocalDate,ShiftType> shift : shifts.keySet()) {
            if (shift.equals(s)) {
                return shifts.get(shift);
            }
        }
        throw new IllegalArgumentException("no such shift!");

    }

    public List<Shift> getShiftHistory(){
        return shifts.values().stream().toList();
    }//

    public void changeShift(Employee e1, Employee e2, LocalDate date1, String sType1){//
        Shift s1 = getShift(date1,sType1);
        //Shift shift1 = getShift(s1), shift2 = getShift(s2);
        try{
            addEmployeeToShift(date1,sType1,e2); //if throws, no actions done
        }
        catch (Exception e){
            throw new IllegalArgumentException("unable to change shift! " + e.getMessage());
        }
        try{
            removeEmployeeFromShift(date1,sType1,e1);
        }
        catch (Exception e){
            s1.removeEmployee(e2);
            throw new IllegalArgumentException("unable to change shift! " + e.getMessage());
        }

    }

    public void removeEmployeeFromShift(LocalDate date, String sType, Employee employee){
        Shift shift = getShift(date,sType);
        if(shift.getmanager().equals(employee)){
            throw new IllegalArgumentException("cant remove employee he manager");
        }
        shift.removeEmployee(employee);
        ShiftRepository.getShiftRepository().deleteWorkerFromShift(date,sType,employee.getId());
    }//

    public void changeDeadLine(LocalDate date, String sType,LocalDate newDte){
        Shift shift = getShift(date,sType);
        shift.setDeadLine(newDte);
        ShiftRepository.getShiftRepository().updateShift(shift);
    }//

    public void addConstraint(LocalDate date, String sType, Employee em){
        Shift shift = getShift(date,sType);
        shift.addConstraint(em);
        ShiftRepository.getShiftRepository().addConstraintToShift(date,sType,em.getId());
    }

    public void removeConstraint(LocalDate date, String sType, Employee em){
        Shift shift = getShift(date,sType);
        shift.removeConstraint(em);
        ShiftRepository.getShiftRepository().deleteConstraintFromShift(date,sType, em.getId());
    }

    public List<Employee> getConstraints(LocalDate date, String sType){
        Shift shift = getShift(date,sType);
        return shift.getConstraints();
    }


    private ShiftType convertShiftType(String s){
        if(s.toLowerCase().compareTo("morning") == 0)
            return ShiftType.MORNING;
        else if(s.toLowerCase().compareTo("evening") == 0)
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

    public List<Pair<LocalDate,ShiftType>> getBlockedShifts() {
        return this.blockedShift;
    }
}
