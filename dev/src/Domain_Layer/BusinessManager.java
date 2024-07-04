package Domain_Layer;

import Domain_Layer.Repositories.BranchRepository;
import Domain_Layer.Repositories.DeliveryRepository;
import Domain_Layer.Repositories.EmployeeRepository;
import Domain_Layer.Repositories.ShiftRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class BusinessManager {
    private Map<Integer,Branch> branches;
    private EmployeeManager em;
    private int branch_idcounter;
    public ShiftManagerFactory shiftmanagerFactory;
    public DeliveryManagerFactory deliverymanagerfactory;



    public BusinessManager(EmployeeManager em1){
        branches = new HashMap<>();
        em = em1;
        branch_idcounter = 1;
        shiftmanagerFactory =  () -> new ShiftManager(branch_idcounter);
        deliverymanagerfactory = () -> new DeliveryManager(branch_idcounter);
        this.createBranch("First Branch");
    }


    public String checkBranch(int id){
        return branches.containsKey(id) ? "":"Branch not exist";
    }

    public Branch getBranch(int bid){
        return branches.get(bid);
    }


    public void createBranch(String name){
        Branch newBranch = new Branch(branch_idcounter, name,shiftmanagerFactory.createShiftManager(), deliverymanagerfactory.createDeliveryManager());
        branches.put(branch_idcounter,newBranch);
        em.createBranch(branch_idcounter);
        BranchRepository.getBranchRepository().insertBranch(newBranch);
        branch_idcounter++;
    }


    public void createShift(int branchId, LocalDate date, String sType, List<String> rolesneeded, int manager )throws IllegalArgumentException{
        if(!branches.containsKey(branchId))
            throw new IllegalArgumentException("no such branch");
        Shift s = branches.get(branchId).createShift(date,sType,rolesneeded,em.getEmployee(manager));
        ShiftRepository.getShiftRepository().insertShift(s,branchId);
        ShiftRepository.getShiftRepository().addWorkerToShift(date,sType, manager);
    }

    public void createShiftwithdefroles(int branchId,LocalDate date, String sType, int managerId) throws IllegalArgumentException{
        if(!branches.containsKey(branchId))
            throw new IllegalArgumentException("no such branch");
        Shift s = branches.get(branchId).createShiftwithdefroles(date,sType,em.getEmployee(branchId,managerId));
        ShiftRepository.getShiftRepository().insertShift(s,branchId);
        ShiftRepository.getShiftRepository().addWorkerToShift(date,sType, managerId);
    }


    public void setDefaultRolesShift(int branchId,List<String> roles){
        if(!branches.containsKey(branchId))
            throw new IllegalArgumentException("no such branch");
        branches.get(branchId).setDefaultRolesShift(roles);
    }

  //comment -  // function not used added to to user intrface or not needed?
    public void deleteShift(int branchId,LocalDate date, String sType ){
        if(!branches.containsKey(branchId))
            throw new IllegalArgumentException("no such branch");
        branches.get(branchId).getSm().deleteShift(date,sType);
    }


    public void blockShift(int branchId,LocalDate date, String sType){
        if(!branches.containsKey(branchId))
            throw new IllegalArgumentException("no such branch");
        branches.get(branchId).blockShift(date,sType);
    }

    public void unblockShift(int branchId,LocalDate date, String sType){
        if(!branches.containsKey(branchId))
            throw new IllegalArgumentException("no such branch");
        branches.get(branchId).unblockshift(date,sType);
    }

    public void addEmployeetoshift(int branchId,LocalDate date, String sType, int idofemployee ){
        if(!branches.containsKey(branchId))
            throw new IllegalArgumentException("no such branch");
        branches.get(branchId).addEmployeeToShift(date,sType,em.getEmployee(branchId,idofemployee));

    }

    public void changeshiftDeadline(int branchId,LocalDate date, String sType,LocalDate newdeadline){
        if(!branches.containsKey(branchId))
            throw new IllegalArgumentException("no such branch");
        branches.get(branchId).changeDeadLine(date,sType,newdeadline);
    }

    public void changeendofmorning(int branchId, LocalDate date, String sType, LocalTime time){
        if(!branches.containsKey(branchId))
            throw new IllegalArgumentException("no such branch");
        branches.get(branchId).changeendofmorning(date,sType,time);
    }

    public void changeManager(int branchId,LocalDate date, String sType, int employeeId){
        if(!branches.containsKey(branchId))
            throw new IllegalArgumentException("no such branch");
        branches.get(branchId).changeManager(date,sType,em.getEmployee(employeeId));
    }

    public String getShift(int branchId,LocalDate date, String sType){
        if(!branches.containsKey(branchId))
            throw new IllegalArgumentException("no such branch");
        return branches.get(branchId).getShift(date,sType);
    }

    public List<String> getShiftHistory(int branchId){
        if(!branches.containsKey(branchId))
            throw new IllegalArgumentException("no such branch");
        return branches.get(branchId).getShiftHistory();

    }

    public void addConstraint(int branchId,LocalDate date, String sType, int idofemployee) {
        if(!branches.containsKey(branchId))
            throw new IllegalArgumentException("no such branch");
        branches.get(branchId).addConstraint(date, sType, em.getEmployee(branchId, idofemployee));
    }


    public void removeConstraint(int branchId,LocalDate date, String sType, int idofemployee){
        if(!branches.containsKey(branchId))
            throw new IllegalArgumentException("no such branch");
        branches.get(branchId).removeConstraint(date, sType, em.getEmployee(branchId, idofemployee));
    }

    public List<String> getConstraints(int branchid,LocalDate date, String sType){
        if(!branches.containsKey(branchid))
            throw new IllegalArgumentException("no such branch");
        return branches.get(branchid).getConstraints(date, sType);
    }


//from here relevant functions for delivery








    public void changeShift(int branchId,int e1, int e2, LocalDate date1, String sType1){
        if(!branches.containsKey(branchId))
            throw new IllegalArgumentException("no such branch");
        Branch b = branches.get(branchId);
        Employee em1 = em.getEmployee(e1);
        Employee em2 = em.getEmployee(e2);
        Shift s1 = b.getSm().getShift(date1,sType1);
        if(s1.getShiftmanager().equals(em1) ) {
            if (em2.isIsmanagar())
                changeManager(branchId, date1, sType1, em2.getId());
            else{
                throw new IllegalArgumentException ("can't change shift of manager please do it via change manager");
            }
        }

        if(!branches.get(branchId).getDm().canBeRemoven(em1,s1))
            throw new IllegalArgumentException("one of the employees cannot be removen! he is part of an expected delivery");
        branches.get(branchId).getSm().changeShift(em.getEmployee(e1),em.getEmployee(e2),date1,sType1);
    }



    //
    public void removeEmployeeFromShift(int branchId,LocalDate date, String sType, int idofemployee) {
        if(!branches.containsKey(branchId))
            throw new IllegalArgumentException("no such branch");
        Employee e = em.getEmployee(branchId, idofemployee);
        ShiftType st = convertShiftType(sType);
        if (branches.get(branchId).getDm().isDriverOrStorekeeper(e,date,st) != 0)
            throw new IllegalArgumentException("can't remove employee, he is part of delivery at this shift. Change the delivery responsibility");
        branches.get(branchId).getSm().removeEmployeeFromShift(date, sType, e);
    }









        //add to service layer
    public int addDelivery(int branchid, LocalDate date, String stype, int driverid, int storekeeperid,char lisence){
        //if employee dosent exist throw error
        if(!branches.containsKey(branchid))
            throw new IllegalArgumentException("no such branch");
        Employee e1 = em.getEmployee(branchid,driverid);
        Employee e2 = em.getEmployee(branchid,storekeeperid);
        return branches.get(branchid).addDelivery(date,stype,e1,e2,lisence);
    }


    public void removeDelivery(int branchid, LocalDate date, String stype, int deliveryId){
        if(!branches.containsKey(branchid))
            throw new IllegalArgumentException("no such branch");
        branches.get(branchid).removeDelivery(date,stype,deliveryId);
    }

    public void changeDriver(int branchId, LocalDate date, String sType,int deliveryId, int newDriverId){
        if(!branches.containsKey(branchId))
            throw new IllegalArgumentException("no such branch");
        branches.get(branchId).changeDeliveryDriver(date,sType,deliveryId,em.getEmployee(branchId,newDriverId));
    }

    public void changeDeliveryStoreKeeper(int branchId, LocalDate date, String sType, int delId, int newstoreKeeperId){
        if(!branches.containsKey(branchId))
            throw new IllegalArgumentException("no such branch");
        branches.get(branchId).changeDeliveryStoreKeeper(date,sType,delId,em.getEmployee(branchId,newstoreKeeperId));
    }
    public String getdelivery(int branchId, LocalDate date, String sType, int deliveryId){
        if(!branches.containsKey(branchId))
            throw new IllegalArgumentException("no such branch");
        return branches.get(branchId).getDelivery(date,sType,deliveryId).toString();
    }













// Employee manager domain
    public List<String> getAvailbleEmployeesForShift(int branchid,LocalDate date, String sType){
        List<Employee> ans;
        Shift s =  branches.get(branchid).getSm().getShift(date,sType);
        try {

            List<Employee> employeewithconstraint = branches.get(branchid).getSm().getConstraints(date,sType);
            List<Employee> temp = em.getComplement(1,employeewithconstraint);
            temp.remove(s.getShiftmanager());
            temp.removeAll(s.getEmployees());
            if(!temp.isEmpty())
            {return temp.stream().map((em) -> em.toString()).toList();}
            else{
                throw new IllegalArgumentException("there not availble employee");
            }


        }catch (Exception e){
            throw new IllegalArgumentException(e);
        }



    }
//comment -// Employee Manager Domain we don't use them why they here?

    public int addEmployeeToBranch(int branchId, String name, String bankAcc, List<String> roles, String employmentType, String salaryType, int salary, int vacationDays, boolean isManager, char lis){
        return em.addEmployee(branchId,name,bankAcc,LocalDate.now(), lis,employmentType,salaryType,salary,vacationDays,isManager);
    }

    public void deleteEmployeeFromBranch(int branchId, int id){
        em.deleteEmployee(branchId,id);
    }

    public void addRole(int id, String role){
        em.addRole(id,role);
    }


    public void removeRole(int id, String role){
        removeRole(id,role);
    }

    public List<String> getEmployeeRoles(int id){
        return em.getEmployeeRoles(id).stream().map((role) -> role.toString()).toList();
    }

    public String getEmployee(int id){
        return em.getEmployee(id).toString();
    }

    public String getEmployee(int branchId, int id){
        return em.getEmployee(branchId,id).toString();
    }

    public List<String> getEmployees(int branchId){
        return em.getEmployees(branchId).stream().map((em) -> em.toString()).toList();
    }





    private ShiftType convertShiftType(String s){
        if(s.toLowerCase().compareTo("morning") == 0)
            return ShiftType.MORNING;
        else if(s.toLowerCase().compareTo("evening") == 0)
            return ShiftType.EVENING;
        else
            throw new IllegalArgumentException("no such shift type '" + s +"'. only have morning or evening");
    }




//    public void uploadDataFromDB(){
//        BranchRepository br = BranchRepository.getBranchRepository();
//        DeliveryRepository dr = DeliveryRepository.getDeliveryRepository();
////        EmployeeRepository er = EmployeeRepository.getEmployeeRepository();
//        ShiftRepository sr = ShiftRepository.getShiftRepository();
//
//        Map<Integer,String> branchesLST = br.getAllBranches();
//        branch_idcounter = Collections.max(branchesLST.keySet()) + 1;
//
//        //1. GET EMPLOYEES
//
//        // branchID -> list<employee>
//        Map<Integer, List<Employee>> branchEmployees = new HashMap<>();
//        // id -> employee
//        Map<Integer,Employee> currentEmployees = new HashMap<>();
//
//        for(int bId : branchesLST.keySet()) {
//            List<Employee> branchesEmployees = br.getAllEmployeeByBranch(bId);
//
//            if (!branchEmployees.containsKey(bId))
//                branchEmployees.put(bId, new ArrayList<>());
//            branchEmployees.get(bId).addAll(branchesEmployees);
//
//            branchesEmployees.stream().forEach((Employee e) -> currentEmployees.put(e.getId(), e));
//        }
//        em = new EmployeeManager(branchEmployees,currentEmployees);
//
//        // 2. GET SHIFTS
//        //+3. GET DELIVERIES
//
//        //branch -> shiftManager
//        HashMap<Integer,ShiftManager> shiftManagers = new HashMap<>();
//        //branch -> deliveryManager
//        HashMap<Integer,DeliveryManager> deliveryManagers = new HashMap<>();
//        for(int bId : branchesLST.keySet()) {
//            Map<Shift, List<Delivery>> deliveriesbyshift = new HashMap<>(); //delivery
//
//            HashMap<Pair<LocalDate, ShiftType>, Shift> shifts = new HashMap<>(); //shift
//            List<Pair<LocalDate, ShiftType>> blockedShift = new ArrayList<>(); //shift
//
//            List<Shift> bShifts = br.getAllShiftsByBranch(bId);
//            for(Shift s: bShifts){
//                shifts.put(s.getShiftID(),s); //shift
//
//                deliveriesbyshift.put(s,dr.getDeliveriesForShift(s.getShiftID().getFirst(),s.getShiftID().getSecond().equals(ShiftType.MORNING)?"morning":"evening"));//delivery
//            }
//
//            List<Shift> bBlockedShifts = sr.getBlockedShifs(bId); //shift
//            bBlockedShifts.forEach((Shift s) -> blockedShift.add(s.getShiftID())); //shift
//
//            shiftManagers.put(bId,new ShiftManager(bId,shifts,blockedShift)); //shift
//            deliveryManagers.put(bId,new DeliveryManager(bId,deliveriesbyshift));//delivery
//        }
//
//
//        //once we have shift manager and deliveery manager, we can move forward to:
//        //4. CREATE BRANCHES
//
//        branches = new HashMap<>();
//
//        for(int bId : branchesLST.keySet()) {
//            ShiftManager sm;
//            DeliveryManager dm;
//
//            if(!shiftManagers.containsKey(bId))
//                sm = new ShiftManager(bId);
//            else
//                sm = shiftManagers.get(bId);
//            if(!deliveryManagers.containsKey(bId))
//                dm = new DeliveryManager(bId);
//            else
//                dm = deliveryManagers.get(bId);
//
//            branches.put(bId,new Branch(bId,branchesLST.get(bId),sm,dm));
//        }
//
//    }





    public void uploadDataFromDB() {
        BranchRepository br = BranchRepository.getBranchRepository();
        DeliveryRepository dr = DeliveryRepository.getDeliveryRepository();
        ShiftRepository sr = ShiftRepository.getShiftRepository();

        Map<Integer, String> branchesLST = br.getAllBranches(); //get all the branches this method also print the branches


        branch_idcounter = Collections.max(branchesLST.keySet()) + 1;

        // 1. GET EMPLOYEES
        Map<Integer, List<Employee>> branchEmployees = new HashMap<>();
        Map<Integer, Employee> currentEmployees = new HashMap<>();

        for (int bId : branchesLST.keySet()) {
            List<Employee> branchesEmployees = br.getAllEmployeeByBranch(bId);
            branchEmployees.put(bId,branchesEmployees);

            System.out.println("emplyees list for :" + bId);
            for(Map.Entry<Integer,List<Employee>> en : branchEmployees.entrySet()){
                for(Employee em : en.getValue()){
                    System.out.println("branch " + en.getKey() + " to employee " + em  );
                }

            }

//            if (!branchEmployees.containsKey(bId)) {
//                branchEmployees.put(bId, new ArrayList<>());
//            }
//            branchEmployees.get(bId).addAll(branchesEmployees);

            for (Employee e : branchesEmployees) {
                currentEmployees.put(e.getId(), e);
            }
        }
        em = new EmployeeManager(branchEmployees, currentEmployees);

        // 2. GET SHIFTS + 3. GET DELIVERIES
        Map<Integer, ShiftManager> shiftManagers = new HashMap<>();
        Map<Integer, DeliveryManager> deliveryManagers = new HashMap<>();

        for (int bId : branchesLST.keySet()) {
            Map<Shift, List<Delivery>> deliveriesByShift = new HashMap<>();
            Map<Pair<LocalDate, ShiftType>, Shift> shifts = new HashMap<>();
            List<Pair<LocalDate, ShiftType>> blockedShift = new ArrayList<>();

            List<Shift> bShifts = br.getAllShiftsByBranch(bId);// get all the sift of the branch
            for (Shift s : bShifts) {//for each shift get all the deliveries
                shifts.put(s.getShiftID(), s);// add the shift for the shift list to add later to the shiftmanager of specific branch
                List<Delivery> deliveries = dr.getDeliveriesForShift(s.getShiftID().getFirst(),//get al the deliveries for that shieft
                        s.getShiftID().getSecond().equals(ShiftType.MORNING) ? "MORNING" : "EVENING");
                deliveriesByShift.put(s, deliveries);
            }

            List<Shift> bBlockedShifts = sr.getBlockedShifs(bId);//get all blocked shift by bid
            for (Shift s : bBlockedShifts) {
                blockedShift.add(s.getShiftID());
            }

            shiftManagers.put(bId, new ShiftManager(bId, (HashMap<Pair<LocalDate, ShiftType>, Shift>) shifts,blockedShift));
            deliveryManagers.put(bId, new DeliveryManager(bId, deliveriesByShift));
        }

        // 4. CREATE BRANCHES
        branches = new HashMap<>();

        for (int bId : branchesLST.keySet()) {
            ShiftManager sm = shiftManagers.getOrDefault(bId, new ShiftManager(bId));
            DeliveryManager dm = deliveryManagers.getOrDefault(bId, new DeliveryManager(bId));
            branches.put(bId, new Branch(bId, branchesLST.get(bId), sm, dm));
        }
    }
}
