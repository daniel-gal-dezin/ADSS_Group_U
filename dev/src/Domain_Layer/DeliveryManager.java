package Domain_Layer;

import Domain_Layer.Repositories.DeliveryRepository;

import java.time.LocalDate;
import java.util.*;

public class DeliveryManager {
    int delivery_id_counter = 1;
    Map<Shift, List<Delivery>> deliveriesbyshift;
    int branchid;


    public  DeliveryManager(int branchid){
        this.branchid = branchid;
        deliveriesbyshift = new HashMap<>();
    }

    public DeliveryManager(int bId, Map<Shift, List<Delivery>> deliveriesbyshift){
        int nextId = 0;
        for(List<Delivery> dels : deliveriesbyshift.values()){
            int max = Collections.max(dels.stream().map((Delivery d) -> d.getDeliveryid()).toList());
            if(max>nextId)
                nextId = max;
        }
        delivery_id_counter = nextId + 1;

        branchid = bId;
        this.deliveriesbyshift = deliveriesbyshift;
    }

    public int addDelivery(Shift shift,  Employee driver, Employee store_keeper, char license){
        Delivery newDel = new Delivery(delivery_id_counter, driver,store_keeper,license);
        delivery_id_counter++;
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
        DeliveryRepository.getDeliveryRepository().insertDelivery(newDel,shift.getShiftID().getFirst(),shift.getShiftID().getSecond().toString());

        return delivery_id_counter-1;
    }


    public void removeDelivery(Pair<LocalDate,ShiftType> shift, int deliveryId){
        Delivery d = getDelivery(shift,deliveryId);
        if(deliveriesbyshift.containsKey(shift)){
            if(!deliveriesbyshift.get(shift).remove(d)) //if no such thing
                throw new IllegalArgumentException("no such delivery in this shift");
        }
        else
            throw new IllegalArgumentException("no deliveries in this shifft or no such shifft");
        DeliveryRepository.getDeliveryRepository().deleteDelivery(deliveryId);
    }

    public void changeDriver(Shift shift, int deliveryId, Employee e){
        if(!shift.getEmployees().contains(e)){
            throw new IllegalArgumentException("employee is not in shift");
        }
        Delivery d = getDelivery(shift.getShiftID(),deliveryId);
        d.setDriver(e);
        DeliveryRepository.getDeliveryRepository().updateDelivery(d);
    }

    public void changeStoreKeeper(Shift shift, int deliveryId, Employee e){
        if(!shift.getEmployees().contains(e)){
            throw new IllegalArgumentException("employee is not in shift");
        }
        Delivery d = getDelivery(shift.getShiftID(),deliveryId);
        d.setStore_keeper(e);
        DeliveryRepository.getDeliveryRepository().updateDelivery(d);
    }


    public Delivery getDelivery(Pair<LocalDate,ShiftType> shift, int deliveryId){
        List<Delivery> c = deliveriesbyshift.get(shift);
        if(c == null) throw new IllegalArgumentException("no deliveries in this shift");
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
        if(c==null)
            return 0;
        for(Delivery d : c){
            if(d.getDriver().equals(e)) {
                return 1;
            }
            if(d.getStore_keeper().equals(e))
                return 2;
        }
        return 0;
    }


    public boolean canBeRemoven(Employee em, Shift s){
        if(this.deliveriesbyshift.containsKey(s))
            for(Delivery d: deliveriesbyshift.get(s))
                if(d.getStore_keeper().equals(em) || d.getDriver().equals(em))
                    return false;

        return true;
    }
}
