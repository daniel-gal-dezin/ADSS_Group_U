package Domain_Layer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Branch {
    private int id;
    private final ShiftManager sm;
    private final DeliveryManager dm;
    private String name;

    public String getName() {
        return name;
    }

    public Branch(int id, String name, ShiftManager sm, DeliveryManager dm){
        this.id = id;
        this.sm = sm;
        this.dm = dm;
        this.name = name;
    }

    public Branch(int id, String name){
        this.id = id;
        this.sm = new ShiftManager(id);
        this.dm = new DeliveryManager(id);
        this.name =name;
    }



    public Shift createShift(LocalDate date,String stype, List<String> rolesneeded,Employee manager){
        return sm.createShift(date,stype,rolesneeded,manager);
    }

    public Shift createShiftwithdefroles(LocalDate date, String sType, Employee manager){
        return sm.createShift(date,sType,manager);
    }

    public void setDefaultRolesShift(List<String> roles){
      sm.setDefaultRolesForShift(roles);

    }

    public void blockShift(LocalDate date, String sType){
        sm.blockShift(date,sType);
    }

    public void unblockshift(LocalDate date, String stype){
        sm.unblockShift(date,stype);
    }

    public void addEmployeeToShift(LocalDate date, String stype, Employee e){
        sm.addEmployeeToShift(date,stype,e);
    }

    public void changeDeadLine(LocalDate date, String stype, LocalDate newdeadline){
        sm.changeDeadLine(date,stype,newdeadline);
    }

    public void changeendofmorning(LocalDate date, String stype, LocalTime time){
        sm.setEndOfMorning(date,stype,time);
    }

    public void changeManager(LocalDate date, String sType, Employee e){
        sm.changeManager(date,sType,e);

    }

    public String getShift(LocalDate date, String sType){
        return sm.getShift(date,sType).toString();
    }

    public List<String> getShiftHistory(){
        return sm.getShiftHistory().stream().map((shift)->shift.toString()).toList();

    }
    public void addConstraint(LocalDate date, String sType, Employee e) {
        sm.addConstraint(date, sType, e);
    }

    public void removeConstraint(LocalDate date, String sType, Employee e){
        sm.removeConstraint(date, sType, e);
    }
    public List<String> getConstraints(LocalDate date, String sType) {
        try {

            List<Employee> employeewithconstraint = sm.getConstraints(date, sType);
            return employeewithconstraint.stream().map((em) -> em.toString()).toList();


        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }





// from here relevant function for delivery

    public int addDelivery(LocalDate date, String stype, Employee driver, Employee storeKeeper,char lisence, int productId, int itemId) throws Exception {
        //if shift dosent exist throw error
        Shift s = sm.getShift(date,stype);

        
        if(!driver.getRoles().contains(Role.DRIVER)) throw new IllegalArgumentException("first employee is not driver");
        if(!storeKeeper.getRoles().contains(Role.STOREKEEPER)) throw new IllegalArgumentException("second employee is not storeKeeper");


        if(!s.getEmployees().contains(driver)){
            throw new IllegalArgumentException("driver isn't in the shift add him first");
        }
        if(!s.getEmployees().contains(storeKeeper)){
            throw new IllegalArgumentException("store keeper isn't in the shift add hime first");
        }
        //the check if driver already assign to a delivery chech in the delivery manager.
        return dm.addDelivery(s,driver,storeKeeper,lisence, productId, itemId);

    }

    public void removeDelivery(LocalDate date,String stype, int delId){
        dm.removeDelivery(sm.getShift(date,stype),delId);
    }

    public void changeDeliveryDriver(LocalDate date,String stype, int delId, Employee newDriver){
        dm.changeDriver(sm.getShift(date,stype),delId,newDriver);
    }

    public void changeDeliveryStoreKeeper(LocalDate date,String stype, int delId, Employee newDriver){
        dm.changeStoreKeeper(sm.getShift(date,stype),delId,newDriver);
    }

    public Delivery getDelivery(LocalDate date,String stype,int delId){
       return dm.getDelivery(sm.getShift(date,stype),delId);
    }




















//    public void addDelivery(int branchid, LocalDate date, String stype, Employee driver, Employee storekeeper) {
//        Shift shift = sm.getShift(date,stype);
//
//        if(!driver.getRoles().contains(Role.DRIVER)) throw new IllegalArgumentException("first employee is not driver");
//        if(!storekeeper.getRoles().contains(Role.STOREKEEPER)) throw new IllegalArgumentException("second employee is not storeKeeper");
//
//        //checks for e1
//        if(!shift.getEmployees().contains(driver)){
//            if(dm.isDriverOrStorekeeper(driver,date,convertShiftType(stype)) != 0)
//                throw new IllegalArgumentException("employee already assigned to delivery at this shift");
//            else{
//                if(shift.getConstraints().contains(driver))
//                    throw new IllegalArgumentException("can't assign employee to delivery, he has constraint on this shift");
//                else
//                    addEmployeetoshift(branchid,date,stype,e1.getId());
//            }
//        }
//
//        //checks for e2
//        if(!shift.getEmployees().contains(e2)){
//            if(branches.get(branchid).getDm().isDriverOrStorekeeper(e2,date,convertShiftType(stype)) != 0) {
//                removeEmployeeFromShift(branchid,date,stype,e1.getId());
//                throw new IllegalArgumentException("employee already assigned to delivery at this shift");
//            }
//            else{
//                if(shift.getConstraints().contains(e2)) {
//                    removeEmployeeFromShift(branchid,date,stype,e1.getId());
//                    throw new IllegalArgumentException("can't assign employee to delivery, he has constraint on this shift");
//                }
//                else
//                    addEmployeetoshift(branchid,date,stype,e2.getId());
//            }
//        }
//        branches.get(branchid).getDm().addDelivery(new Pair<>(date,convertShiftType(stype)),e1,e2);
//    }
//
//
//
//
//







    private ShiftType convertShiftType(String s){
        if(s.toLowerCase().compareTo("morning") == 0)
            return ShiftType.MORNING;
        else if(s.toLowerCase().compareTo("evening") == 0)
            return ShiftType.EVENING;
        else
            throw new IllegalArgumentException("no such shift type '" + s +"'. only have morning or evening");
    }





    public DeliveryManager getDm() {
        return dm;
    }

    public ShiftManager getSm() {
        return sm;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



















}
