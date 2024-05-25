package Tests;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import Domain_Layer.Employee;
import Domain_Layer.Role;
import java.util.Arrays;
public class EmployeeTest {
    @Test
    public void testAddRole() {
        Employee employee = new Employee(1, "John", "123456", Arrays.asList(Role.CASHIER), null);
        employee.addRole("DRIVER");
        assertTrue(employee.getRoles().contains(Role.DRIVER));
    }
    @Test
    public void testRemoveRole() {
        Employee employee = new Employee(1, "John", "123456", Arrays.asList(Role.CASHIER, Role.DRIVER), null);
        employee.removeRole("DRIVER");
        assertFalse(employee.getRoles().contains(Role.DRIVER));
    }
    @Test
    public void testPrintRoles() {
        Employee employee = new Employee(1, "John", "123456", Arrays.asList(Role.CASHIER, Role.DRIVER), null);
        employee.printRoles();
    }
    @Test
    public void testGetId() {
        Employee employee = new Employee(1, "John", "123456", Arrays.asList(Role.CASHIER, Role.DRIVER), null);
        assertEquals(1, employee.getId());
    }
    @Test
    public void testSetId() {
        Employee employee = new Employee(1, "John", "123456", Arrays.asList(Role.CASHIER, Role.DRIVER), null);
        employee.setId(2);
        assertEquals(2, employee.getId());
    }
    @Test
    public void convertRole() {
        Employee.convertRole("manager");}
    //the test not work with upeer letters dont understand why. check later
}
