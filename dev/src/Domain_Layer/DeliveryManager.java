package Domain_Layer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeliveryManager {
    int delivery_id_counter = 1;
    Map<Pair<LocalDate,ShiftType>, List<Delivery>> deliveriesbyshift;


    public  DeliveryManager(){
        deliveriesbyshift = new HashMap<>();
    }

    public DeliveryManager( Map<Pair<LocalDate, ShiftType>, List<Delivery>> deliveriesbyshift) {
        this.deliveriesbyshift = deliveriesbyshift;
        delivery_id_counter = deliveriesbyshift.size()+1;
    }

    public void addDelivery(Pair<LocalDate,ShiftType> shift,  Employee driver, Employee store_keeper){
        Delivery newDel = new Delivery(delivery_id_counter++, driver,store_keeper);
        if(!deliveriesbyshift.containsKey(shift)){
            deliveriesbyshift.put(shift,new ArrayList<>());
        }
        deliveriesbyshift.get(shift).add(newDel);
    }

    public void removeDelivery(Pair<LocalDate,ShiftType> shift, int deliveryId){
        Delivery d = getDelivery(shift,deliveryId);
        deliveriesbyshift.get(shift).remove(d);
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
            if(d.getDriver().equals(e))
                return 1;
            if(d.getStore_keeper().equals(e))
                return 2;
        }
        return 0;
    }

}
