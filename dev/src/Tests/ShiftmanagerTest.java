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
        manager = new Employee("John Doe", "123456", new Terms());
        employee = new Employee("John Doe", "123456", new Terms());
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
        assertEquals(4, shift.getRolesNeeded().size());
        assertEquals(manager, shift.getShiftManager());
    }

    @Test
    public void testCreateShiftWithoutRoles() {
        assertDoesNotThrow(() -> shiftManager.createShift(futureDate, morningShift, manager));
        Shift shift = shiftManager.getShift(futureDate, morningShift);
        assertNotNull(shift);
        assertEquals(4, shift.getRolesNeeded().size());
        assertTrue(shift.getRolesNeeded().contains(Role.MANAGER));
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