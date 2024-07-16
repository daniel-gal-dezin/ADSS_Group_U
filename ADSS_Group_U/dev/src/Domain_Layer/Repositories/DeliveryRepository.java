package Domain_Layer.Repositories;

import Data_Access_Layer.DeliveryDAO;
import Domain_Layer.Delivery;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class DeliveryRepository {
    private DeliveryDAO delDAO = new DeliveryDAO();
    private static DeliveryRepository inst = null;

    private DeliveryRepository(){}

    public static DeliveryRepository getDeliveryRepository(){
        if(inst == null)
            inst = new DeliveryRepository();
        return inst;
    }

    public void insertDelivery(Delivery delivery, LocalDate date, String sType) throws Exception {
        try {
            delDAO.removeItem(delivery.getProductId(), delivery.getItemId());
            delDAO.updateProductStock(delivery.getProductId());
            delDAO.insertDelivery(delivery);
            addDeliveryToShift(date,sType,delivery.getDeliveryid());
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }

    public Delivery getDelivery(int id)  {
        return delDAO.getDelivery(id);
    }

    public void updateDelivery(Delivery delivery) {
        delDAO.updateDelivery(delivery);
    }

    public void deleteDelivery(int id) {
        deleteShiftfromDelivery(id);
        delDAO.deleteDelivery(id);


    }

    public void addDeliveryToShift(LocalDate date, String shiftType, int deliveryId) {
        delDAO.addDeliveryToShift(date,shiftType,deliveryId);
    }

    public List<Delivery> getDeliveriesForShift(LocalDate date, String shiftType)  {
        return delDAO.getDeliveriesForShift(date,shiftType);
    }

    public void deleteShiftfromDelivery(int delId) {
        delDAO.deleteShifttoDelivery(delId);
    }




    }
