package Tests;

import Domain_Layer.Delivery;
import Domain_Layer.Employee;
import Domain_Layer.Role;
import Domain_Layer.Terms;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class DeliveryTest {
    ArrayList<Role> lst = new ArrayList<>();
    Employee driver, storeKeepper;
    Delivery d;
    Terms t = new Terms(LocalDate.now(),"full","monthly",100000,12);

    @BeforeEach
    public void setup(){
        lst.add(Role.DRIVER);
        lst.add(Role.STOREKEEPER);
        driver= new Employee(1,"david","", lst, t,false,'a');
        storeKeepper= new Employee(2,"shlomo","", lst, t,false,'0');
    }

    @Test
    public void checkNonDriverInsertionn(){
        lst.remove(Role.DRIVER);
        driver= new Employee(1,"david","", lst, t,false,'a');

        try {
            d = new Delivery(1, driver, storeKeepper, 'a');
            fail("non driver has inserted as a driver of delivery");
        } catch(Exception e){
            String str = e.getMessage();
            assertTrue("must insert driver".equals(str));
        }
    }


    @Test
    public void checkBadLicense() {
        try {
            d = new Delivery(1, driver, storeKeepper, 'b');
            fail("non driver has inserted as a driver of delivery");
        } catch(Exception e){
            String str = e.getMessage();
            assertTrue("must insert driver with appropriate license".equals(str));
        }
    }


    @Test
    public void setGoodDriver(){
        d = new Delivery(1, driver, storeKeepper, 'a');
        Employee driver2 = new Employee(3,"shaul","", lst, t,false,'a');
        d.setDriver(driver2);
        assertTrue(driver2.equals(d.getDriver()));
    }





}
