package Domain_Layer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.List;

public class Shift {

    private Pair<LocalDate, ShiftType> shiftID;
    private List<Role> rolesneeded;
    private Employee shiftmanager;
    private List<Employee> employees;
    private List<Employee> constraints;
    private LocalDate deadLine;//until when worker can put constraints on this shift
    private LocalTime start;
    private LocalTime end;
    private int deadlinedfault = 1;// 1 week
    private LocalTime startmorning = LocalTime.of(6, 0);
    private LocalTime startevening = LocalTime.of(14, 0);
    private LocalTime endmorning = LocalTime.of(14, 0);
    private LocalTime endevening = LocalTime.of(22, 0);


    public Shift() {
    }
    public Employee getmanager(){
        return this.shiftmanager;
    }

    public Shift(Pair<LocalDate, ShiftType> shiftID,List<Role> rolesneeded, Employee manager) {
        if(!manager.isIsmanagar())
            throw new IllegalArgumentException("can't create a shift, need a manager! the employee inserted isn't one.");
        this.shiftID = shiftID;
        this.rolesneeded = rolesneeded;
        this.shiftmanager = manager;
        this.employees = new ArrayList<>();
        this.employees.add(manager);
        this.constraints = new ArrayList<>();
        this.deadLine = shiftID.getFirst().minusWeeks(deadlinedfault);//default value
        if (this.shiftID.getSecond() == ShiftType.MORNING) {// defualt value
            this.start = LocalTime.of(startmorning.getHour(), startmorning.getMinute());
            this.end = LocalTime.of(endmorning.getHour(), endmorning.getMinute());
        } else {
            this.start = LocalTime.of(startevening.getHour(), startevening.getMinute());
            this.end = LocalTime.of(endevening.getHour(), endevening.getMinute());
        }
    }
    public void setEndMorning(LocalTime time){
        if(time.isAfter(LocalTime.of(23,59))){
            throw new IllegalArgumentException("to late hour");
        }
     this.endmorning = time;
    }



    public void addEmployee(Employee employee) {
        if(employees.contains(employee))
            throw new IllegalArgumentException("can't add employee to shift! He is already in it  ");
        if(this.shiftmanager == null){
            throw new IllegalArgumentException("can't add employee to shift! there is no manager yet!");
        }
        employees.add(employee);
    }


    public void removeEmployee(Employee employee) {
        if(!employees.contains(employee))
            throw new IllegalArgumentException("can't remove employee from shift! He is not in it");
        List<Role> roles = employee.getRoles();
        for(Role role: roles){
            if(rolesneeded.contains(role)){
                boolean b = false;
                for(Employee e: employees){
                    if(e.getRoles().contains(role)) {
                        b = true;
                        break;
                    }
                }
                if(!b){
                    throw new IllegalArgumentException("can't remove employee from shift! the role " + role + " is missing without him");
                }
            }
        }
        employees.remove(employee);
    }
    public void removeEmployees() {
        employees.clear();
    }


    public void addConstraint(Employee employee) {
        if(constraints.contains(employee))
            throw new IllegalArgumentException("can't add employee's constraint! he is already written");
        if(this.shiftmanager.equals(employee)) throw new IllegalArgumentException("employee already assign as managaer please chnage shift");
        constraints.add(employee);
    }


    public void removeConstraint(Employee employee) {
        if(!constraints.contains(employee))
            throw new IllegalArgumentException("can't remove employee's constraint! he is not written");
        constraints.remove(employee);
    }


    public int getDeadlinedfault() {
        return deadlinedfault;
    }

    public void setDeadlinedfault(int deadlinedfault) {
        this.deadlinedfault = deadlinedfault;
    }

    public LocalTime getStartmorning() {
        return startmorning;
    }

    public void setStartmorning(LocalTime startmorning) {
        this.startmorning = startmorning;
    }

    public LocalTime getStartevening() {
        return startevening;
    }

    public void setStartevening(LocalTime startevening) {
        this.startevening = startevening;
    }

    public LocalTime getEndmorning() {
        return endmorning;
    }

    public void setEndmorning(LocalTime endmorning) {
        this.endmorning = endmorning;
    }

    public LocalTime getEndevening() {
        return endevening;
    }

    public void setEndevening(LocalTime endevening) {
        this.endevening = endevening;
    }

    public Pair<LocalDate, ShiftType> getShiftID() {
        return shiftID;
    }

    public void setShiftID(Pair<LocalDate, ShiftType> shiftID) {
        this.shiftID = shiftID;
    }

    public List<Role> getRolesneeded() {
        return rolesneeded;
    }

    public void setRolesneeded(List<Role> rolesneeded) {
        this.rolesneeded = rolesneeded;
    }

    public Employee getShiftmanager() {
        return shiftmanager;
    }

    public void setShiftmanager(Employee shiftmanager) {
        if(shiftmanager == null)
            throw new IllegalArgumentException("can't add null employee to shift");
        if(!shiftmanager.isIsmanagar())
            throw new IllegalArgumentException("can't asign eployee as manager. S.he is not certified");

        if(employees.contains(shiftmanager))
            employees.remove(shiftmanager);
        this.shiftmanager = shiftmanager;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }


    public List<Employee> getConstraints() {
        return constraints;
    }

    public void setConstraints(List<Employee> constraints) {
        this.constraints = constraints;
    }

    public LocalDate getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(LocalDate deadLine) {
        if(deadLine.isAfter(this.shiftID.getFirst())){
            throw new IllegalArgumentException("can't asign dead line for shift after it's date");
        }
        this.deadLine = deadLine;
    }

    public LocalTime getStart() {
        return start;
    }

    public void setStart(LocalTime start) {
        this.start = start;
    }

    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(LocalTime end) {
        this.end = end;
    }


    @Override
    public String toString() {
        return "Shift{" +
                "shiftID=" + shiftID +
                ", rolesneeded=" + rolesneeded +
                ", shiftmanager=" + shiftmanager +
                ", employees=" + employees +
                ", constraints=" + constraints +
                ", deadLine=" + deadLine +
                ", start=" + start +
                ", end=" + end +
                ", deadlinedfault=" + deadlinedfault +
                ", startmorning=" + startmorning +
                ", startevening=" + startevening +
                ", endmorning=" + endmorning +
                ", endevening=" + endevening +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Shift)
            return this.shiftID.equals(((Shift)obj).shiftID);
        if(obj instanceof Pair<?,?>){
            return this.shiftID.equals(((Pair<?, ?>) obj));
        }
        return false;
    }
}
