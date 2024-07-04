package Tests;

import Domain_Layer.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShiftTest {
    private Shift shift;
    private Employee manager;
    private Employee employee;
    private Pair<LocalDate, ShiftType> shiftID;
    List<Role> rolesneeded;

    @BeforeEach
    void setUp() {
        Terms t = new Terms(LocalDate.now(), "full", "global", 1, 1);
        List<Role> roles = new ArrayList<>();
        roles.add(Role.MANAGER);
        List<Role> roles1 = new ArrayList<>();
        roles1.add(Role.DRIVER);
        manager = new Employee(1, "manager", "123",roles, t, true,'0');
        employee = new Employee(2, "employee", "123",roles1 , t, false,'0');
        shiftID = new Pair<>(LocalDate.now(), ShiftType.MORNING);
        rolesneeded = new ArrayList<>();
        rolesneeded.add(Role.CASHIER);// defualt role needed
        rolesneeded.add(Role.DRIVER);
        rolesneeded.add(Role.MANAGER);
        rolesneeded.add(Role.STOREKEEPER);
        shift = new Shift(shiftID,rolesneeded,manager);
    }

    @Test
    void createShiftSuccessfully() {
        assertNotNull(shift);
    }
    @Test
    void addEmployeeToShiftSuccessfully() {
        shift.addEmployee(employee);
        assertTrue(shift.getEmployees().contains(employee));
    }


    @Test
    void removeEmployeeFromShiftSuccessfully() {
        shift.addEmployee(employee);
        shift.removeEmployee(employee);
        assertFalse(shift.getEmployees().contains(employee));
    }

    @Test
    void removeEmployeeFromShiftThrowsExceptionWhenEmployeeNotInShift() {
        assertThrows(IllegalArgumentException.class, () -> shift.removeEmployee(employee));
    }

    @Test
    void addConstraintSuccessfully() {
        shift.addConstraint(employee);
        assertTrue(shift.getConstraints().contains(employee));
    }

}