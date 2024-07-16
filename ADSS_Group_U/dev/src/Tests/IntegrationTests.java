package Tests;

import Data_Access_Layer.DeliveryDAO;
import Domain_Layer.*;
import Service_Layer.BusinessService;
import Service_Layer.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class IntegrationTests {


    @Nested
    class DeliveryTest {
        ArrayList<Role> lst = new ArrayList<>();
        Employee driver, storeKeepper;
        Employee e;
        Delivery d;
        Shift s;
        Terms t = new Terms(LocalDate.now(), "full", "monthly", 100000, 12);
        DeliveryDAO da = new DeliveryDAO();
        DeliveryManager dm = new DeliveryManager(1);
        EmployeeManager em = new EmployeeManager();
        BusinessManager bm = new BusinessManager(em);
        BusinessService bs = new BusinessService(bm);
        EmployeeService es = new EmployeeService(em);



        @BeforeEach
        public void setup() {
            //employees
            es.addEmployee(1, "a","1", LocalDate.of(2025,10,10),"Full","Global",12,1,true,'a');
            es.addEmployee(1,"b","2",LocalDate.of(2025,10,10),"full","Global",12,1,true,'b');
            es.addEmployee(1,"c","2",LocalDate.of(2025,10,10),"full","Global",12,1,true,'b');

            //createshift
            bs.createShiftwithdefroles(1,2025,10,10,"morning",1);
            bs.createShiftwithdefroles(1,2025,10,10,"evening",1);

            //addconstraint
            bs.addConstraint(1,2025,10,10,"evening",2);

            //addemployeetoshift
            bs.addEmployeeToShift(1,2025,10,10,"morning",2);
            bs.addEmployeeToShift(1,2025,10,10,"morning",3);
        }

        @Test
        //the data base needs to be empty and to include one item and one product
        public void successaddDelivery(){
            assertDoesNotThrow(()-> bs.addDelivery(1,2025,10,10,"morning",2,2,'b',1,1));
            String answer=da.getItem(1,1);
            assertEquals("Item not found", answer);
        }

        @Test
        //the data base needs to be empty and to include one item and one product
        public void failaddDelivery(){
            String answer=bs.addDelivery(1,2025,10,10,"morning",2,2,'b',1,1);
            assertEquals("java.lang.Exception: Item with ID 1 and ProductId 1 does not exist.~~", answer);
        }





    }
}