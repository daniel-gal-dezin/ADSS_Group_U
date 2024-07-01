package Domain_Layer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeliveryManager {
    int delivery_id_counter = 1;
    Map<Shift, List<Delivery>> deliveriesbyshift;


    public  DeliveryManager(){
        deliveriesbyshift = new HashMap<>();
    }

    public DeliveryManager( Map<Shift, List<Delivery>> deliveriesbyshift) {
        this.deliveriesbyshift = deliveriesbyshift;
        delivery_id_counter = deliveriesbyshift.size()+1;
    }

    public void addDelivery(Shift shift,  Employee driver, Employee store_keeper){
        Delivery newDel = new Delivery(delivery_id_counter++, driver,store_keeper);
        if(!deliveriesbyshift.containsKey(shift)){
            deliveriesbyshift.put(shift,new ArrayList<>());
        }
        List<Delivery> deliveries_in_Shift = deliveriesbyshift.get(shift);
        for (Delivery d : deliveries_in_Shift){

            //check if driver already assign to delivery , also storekeeper can assign to multiply deliveries.
            if(d.getDriver() == driver ){
                delivery_id_counter--;
                throw new IllegalArgumentException("employee already assign to Delivery hence he cannot assign to another");
            }
        }

        deliveriesbyshift.get(shift).add(newDel);
    }


    public void removeDelivery(Pair<LocalDate,ShiftType> shift, int deliveryId){
        Delivery d = getDelivery(shift,deliveryId);
        if(deliveriesbyshift.containsKey(shift)){
            if(!deliveriesbyshift.get(shift).remove(d)) //if no such thing
                throw new IllegalArgumentException("no such delivery in this shift");
        }
        else
            throw new IllegalArgumentException("no deliveries in this shifft or no such shifft");
        deliveriesbyshift.get(shift).remove(d);
    }

    public void changeDriver(Shift shift, int deliveryId, Employee e){
        if(!shift.getEmployees().contains(e)){
            throw new IllegalArgumentException("employee is not in shift");
        }
        Delivery d = getDelivery(shift.getShiftID(),deliveryId);
        d.setDriver(e);
    }

    public void changeStoreKeeper(Shift shift, int deliveryId, Employee e){
        if(!shift.getEmployees().contains(e)){
            throw new IllegalArgumentException("employee is not in shift");
        }
        Delivery d = getDelivery(shift.getShiftID(),deliveryId);
        d.setStore_keeper(e);
    }


    public Delivery getDelivery(Pair<LocalDate,ShiftType> shift, int deliveryId){
        List<Delivery> c = deliveriesbyshift.get(shift);
        for(Delivery d : c){
            if(d.getDeliveryid() == deliveryId)
                return d;
        }
        throw new IllegalArgumentException("delivery id Doesn't exist");
    }

    //if driver return 1
    // if storeKeeper return 2
    //else 0;
    public int isDriverOrStorekeeper(Employee e, LocalDate date, ShiftType t){
        List<Delivery> c = deliveriesbyshift.get(new Pair<>(date,t));
        for(Delivery d : c){
            if(d.getDriver().equals(e)) {
                return 1;
            }
            if(d.getStore_keeper().equals(e))
                return 2;
        }
        return 0;
    }

}
