package Tests;
import Domain_Layer.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeManagerTest {
    private EmployeeManager employeeManager;

    @BeforeEach
    void setUp() {
        employeeManager = new EmployeeManager();
    }

    @Test
    void testAddEmployee() {
        employeeManager.addEmployee("John Doe", "123456", new Terms());
        assertEquals(1, employeeManager.getEmployees().size());
        assertEquals("John Doe", employeeManager.getEmployees().get(0).getName());
    }

    @Test
    void testDeleteEmployee() {
        employeeManager.addEmployee("John Doe", "123456", new Terms());
        employeeManager.deleteEmployee(1);
        assertEquals(0, employeeManager.getEmployees().size());
    }

    @Test
    void testAddRole() {
        employeeManager.addEmployee("John Doe", "123456", new Terms());
        employeeManager.addRole(1, "manager");
        assertTrue(employeeManager.getEmployeeRoles(1).contains(Role.MANAGER));
    }

    @Test
    void testRemoveRole() {
        employeeManager.addEmployee("John Doe", "123456", new Terms());
        employeeManager.addRole(1, "Manager");
        employeeManager.removeRole(1, "Manager");
        assertFalse(employeeManager.getEmployeeRoles(1).contains(Role.MANAGER));
    }

    @Test
    void testSetSalary() {
        employeeManager.addEmployee("John Doe", "123456", new Terms());
        employeeManager.setSalary(1, 5000);
        assertEquals(5000, employeeManager.getEmployee(1).getSalary());
    }
}