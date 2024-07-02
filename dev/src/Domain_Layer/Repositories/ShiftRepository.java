package Domain_Layer.Repositories;

import Data_Access_Layer.ShiftDAO;
import Domain_Layer.Employee;
import Domain_Layer.Shift;
import Domain_Layer.ShiftManager;

import java.time.LocalDate;
import java.util.List;

public class ShiftRepository {
    private ShiftDAO shDAO = new ShiftDAO();
    private BranchRepository brep = BranchRepository.getBranchRepository();
    private static ShiftRepository ins = null;

    private ShiftRepository() {
    }

    ;

    public static ShiftRepository getShiftRepository() {
        if (ins == null)
            ins = new ShiftRepository();
        return ins;
    }


    public void insertShift(Shift shift,int bId) {
        shDAO.insertShift(shift);
        shDAO.insertShiftToBranch(shift.getShiftID().getFirst(),shift.getShiftID().getSecond().toString(),bId);

    }

    public Shift getShift(Shift shift) {
        return shDAO.getShift(shift.getShiftID().getFirst(), shift.getShiftID().getSecond().toString());
    }

    public void updateShift(Shift shift){
        shDAO.updateShift(shift);
    }


    public void deleteShift(Shift shift, int bId){
        shDAO.deleteShift(shift.getShiftID().getFirst(), shift.getShiftID().getSecond().toString());
        shDAO.deleteShiftFromBranch(shift.getShiftID().getFirst(),shift.getShiftID().getSecond().toString(),bId);
    }

    public List<Shift> getAllShifts(){
        return shDAO.getAllShifts();
    }


    public void addConstraintToShift(LocalDate shiftDate, String shiftType, int employeeId)   {
        shDAO.addConstraintToShift(shiftDate,shiftType,employeeId);
    }

    public void deleteConstraintFromShift(LocalDate shiftDate, String shiftType, int employeeId)   {
        shDAO.deleteConstraintFromShift(shiftDate,shiftType,employeeId);
    }

    public List<Employee> getEmployeesForShift(LocalDate date, String shiftType) {
        return shDAO.getEmployeesForShift(date,shiftType);
    }

    public void addWorkerToShift(LocalDate shiftDate, String shiftType, int employeeId)   {
        shDAO.addWorkerToShift(shiftDate, shiftType, employeeId);
    }

    public void deleteWorkerFromShift(LocalDate shiftDate, String shiftType, int employeeId)   {
        shDAO.deleteWorkerFromShift(shiftDate, shiftType, employeeId);
    }
    public void insertBlockedShift(LocalDate date, String sType, int bId){
        shDAO.insertBlockedShift(date, sType, bId);
    }

    public void deleteBlockedShift(LocalDate date, String sType, int bId){
        shDAO.deleteBlockedShift(date, sType, bId);
    }

    public List<Shift> getBlockedShifs(int bId){
        return shDAO.getBlockedShifs(bId);
    }









}
