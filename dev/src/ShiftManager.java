import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShiftManager {
    private HashMap<Pair<LocalDate, ShiftType>, Shift> shifts;
    private List<Pair<LocalDate, ShiftType>> blockedshift;
    private List<Employee.Role> difaultrolesneeded;


    public ShiftManager() {//with diffualt roles needed
        shifts = new HashMap<>();
        blockedshift = new ArrayList<>();
        this.difaultrolesneeded = createdifaultroleneede();
    }

    public List<Employee.Role> createdifaultroleneede() {
        List<Employee.Role> rolesneeded1 = new ArrayList<>();
        rolesneeded1.add(Employee.Role.CASHIER);// defualt role needed
        rolesneeded1.add(Employee.Role.DRIVER);
        rolesneeded1.add(Employee.Role.MANAGER);
        rolesneeded1.add(Employee.Role.STOREKEEPER);
        return rolesneeded1;
    }

    //to upload the data from the database
    public ShiftManager(HashMap<Pair<LocalDate, ShiftType>, Shift> shifts, List<Pair<LocalDate, ShiftType>> blockedshift, List<Employee.Role> rolesneeded) {
        this.shifts = shifts;
        this.blockedshift = blockedshift;
        this.difaultrolesneeded = rolesneeded;
    }

    public void createShift(Pair<LocalDate, ShiftType> shift, List<Employee.Role> rolesneeded) {

        if (shifts.containsKey(shift))
            throw new IllegalArgumentException("Shift already exists");

        if (blockedshift.contains(shift))
            throw new IllegalArgumentException("Shift is blocked");

        if (shift.getFirst().isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Shift is in the past");


        Shift newshift = new Shift(shift, rolesneeded);
        shifts.put(shift, newshift);
    }


    public void createShift(Pair<LocalDate, ShiftType> shift) {//create shift without knowing which roles needed

        if (shifts.containsKey(shift))
            throw new IllegalArgumentException("Shift already exists");

        if (blockedshift.contains(shift))
            throw new IllegalArgumentException("Shift is blocked");

        if (shift.getFirst().isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Shift is in the past");


        Shift newshift = new Shift(shift, this.difaultrolesneeded);
        shifts.put(shift, newshift);
    }


    public void deleteShift(Pair<LocalDate, ShiftType> shift) {
        if (!shifts.containsKey(shift))
            throw new IllegalArgumentException("Shift does not exist");
        shifts.get(shift).removeEmployees();
        shifts.remove(shift);
    }


    public void blockShift(Pair<LocalDate, ShiftType> shift) {
        if (shift.getFirst().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Shift is in the past");
        }
        if (shifts.containsKey(shift)) {
            deleteShift(shift);
        }
        blockedshift.add(shift);
    }


    public void unblockShift(Pair<LocalDate, ShiftType> shift) {
        if (!blockedshift.contains(shift)) {
            throw new IllegalArgumentException("Shift is not blocked");
        }
        blockedshift.remove(shift);
    }


}
