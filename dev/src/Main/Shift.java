package Main;

import java.time.LocalDate;
import java.time.LocalTime;
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

    public Shift(Pair<LocalDate, ShiftType> shiftID,List<Role> rolesneeded) {
        this.shiftID = shiftID;
        this.rolesneeded = rolesneeded;
        this.shiftmanager = null;
        this.employees = new ArrayList<>();
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



    public void addEmployee(Employee employee) {
        employees.add(employee);
    }


    public void removeEmployee(Employee employee) {
        employees.remove(employee);
    }
    public void removeEmployees() {
        employees.clear();
    }


    public void addConstraint(Employee employee) {
        constraints.add(employee);
    }


    public void removeConstraint(Employee employee) {
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
}
