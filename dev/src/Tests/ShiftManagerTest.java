package Tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import Domain_Layer.*;

public class ShiftManagerTest {
    private ShiftManager shiftManager;
    private Employee manager;
    private Employee employee;
    private LocalDate futureDate;
    private LocalDate pastDate;
    private String morningShift;
    private String eveningShift;
    private List<String> rolesNeeded;

    @BeforeEach
    public void setUp() {
        shiftManager = new ShiftManager();
        List<Role> roles1 = new LinkedList<>();
        roles1.add(Role.MANAGER);
        List<Role> roles2 = new LinkedList<>();
        roles2.add(Role.CASHIER);
        Terms t = new Terms(LocalDate.now(), "full", "global", 1, 1);
        manager = new Employee(1, "John Doe", "123456",roles1, t, true);
        employee = new Employee(2, "Jane", "654321",roles2, t, false);
        futureDate = LocalDate.now().plusDays(1);
        pastDate = LocalDate.now().minusDays(1);
        morningShift = "morning";
        eveningShift = "evening";
        rolesNeeded = Arrays.asList("cashier", "driver", "manager", "storekeeper");
    }

    @Test
    public void testCreateShiftWithRoles() {
        assertDoesNotThrow(() -> shiftManager.createShift(futureDate, morningShift, rolesNeeded, manager));
        Shift shift = shiftManager.getShift(futureDate, morningShift);
        assertNotNull(shift);
        assertEquals(manager, shift.getShiftmanager());
    }

    @Test
    public void testCreateShiftWithoutRoles() {
        assertDoesNotThrow(() -> shiftManager.createShift(futureDate, morningShift, manager));
        Shift shift = shiftManager.getShift(futureDate, morningShift);
        assertNotNull(shift);
        assertEquals(4, shift.getRolesneeded().size());
        assertTrue(shift.getRolesneeded().contains(Role.MANAGER));
    }

    @Test
    public void testBlockShift() {
        shiftManager.blockShift(futureDate, morningShift);
        assertThrows(IllegalArgumentException.class, () -> shiftManager.createShift(futureDate, morningShift, manager));
        assertTrue(shiftManager.getBlockedShifts().contains(new Pair<>(futureDate, ShiftType.MORNING)));
    }

    @Test
    public void testAddEmployeeToShift() {
        shiftManager.createShift(futureDate, morningShift, manager);
        assertDoesNotThrow(() -> shiftManager.addEmployeeToShift(futureDate, morningShift, employee));
        Shift shift = shiftManager.getShift(futureDate, morningShift);
        assertTrue(shift.getEmployees().contains(employee));
    }

    @Test
    public void testRemoveEmployeeFromShift() {
        shiftManager.createShift(futureDate, morningShift, manager);
        shiftManager.addEmployeeToShift(futureDate, morningShift, employee);
        assertDoesNotThrow(() -> shiftManager.removeEmployeeFromShift(futureDate, morningShift, employee));
        Shift shift = shiftManager.getShift(futureDate, morningShift);
        assertFalse(shift.getEmployees().contains(employee));
    }
}