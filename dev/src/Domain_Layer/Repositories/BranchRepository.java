package Domain_Layer.Repositories;

import Data_Access_Layer.BranchDAO;
import Domain_Layer.Branch;
import Domain_Layer.Employee;
import Domain_Layer.Shift;

import java.util.List;

public class BranchRepository {
    BranchDAO BDAO = new BranchDAO();
    private static BranchRepository inst = null;

    private BranchRepository(){}

    public static BranchRepository getBranchRepository(){
        if(inst == null)
            inst = new BranchRepository();
        return inst;
    }

    public void insertBranch(Branch branch) {
        BDAO.insertBranch(branch);
    }

    public Branch getBranch(int id) {
        return BDAO.getBranch(id);
    }

    public void updateBranch(Branch branch) {
        BDAO.updateBranch(branch);
    }

    public void deleteBranch(int id) {
        BDAO.deleteBranch(id);
    }

    public List<Branch> getAllBranches() {
        return BDAO.getAllBranches();
    }

    public List<Shift> getAllShiftsByBranch(int branchId)   {
        return BDAO.getAllShiftsByBranch(branchId);
    }


    public List<Employee> getAllEmployeeByBranch(int brId) {
        return BDAO.getAllEmployeeByBranch(brId);
    }

    public void insertEmployeeToBranch(int branchId, int employeeId) {
        BDAO.insertEmployeeToBranch(branchId, employeeId);
    }

    public void deleteEmployeeFromBranch(int branchId, int employeeId) {
        BDAO.deleteEmployeeFromBranch(branchId, employeeId);
    }





    }
