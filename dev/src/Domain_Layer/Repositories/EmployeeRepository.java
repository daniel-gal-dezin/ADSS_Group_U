package Domain_Layer.Repositories;

import Data_Access_Layer.EmployeeDAO;
import Domain_Layer.Employee;
import Domain_Layer.Role;

import java.util.List;

public class EmployeeRepository {
    EmployeeDAO dao = new EmployeeDAO();
    BranchRepository bRep = BranchRepository.getBranchRepository();
    private static EmployeeRepository inst = null;

    private EmployeeRepository(){}

    public static EmployeeRepository getEmployeeRepository(){
        if(inst == null)
            inst = new EmployeeRepository();
        return inst;
    }

    public void insertEmployee(Employee em, int branchId){
        dao.insertEmployee(em);
        bRep.insertEmployeeToBranch(branchId,em.getId());
        for(Role r : em.getRoles()){
            addRoleToEmployee(em.getId(),r.toString());
        }
    }

    public Employee getEmployee(int id){
        return dao.getEmployee(id);
    }

    public void updateEmployee(Employee em){
        dao.updateEmployee(em);
    }

    public void deleteEmployee(int emId, int branchId) {
        dao.deleteEmployee(emId);
        bRep.deleteEmployeeFromBranch(branchId,emId);
        dao.removeEmployeeRoles(emId);
    }

    public List<Employee> getAllEmployees() {
        return dao.getAllEmployees();
    }


    public void addRoleToEmployee(int id, String r) {
        dao.addRoleToEmployee(id,r);
    }

    public void removeRollFromEmployee(int id, String r) {
        dao.removeRoleFromEmployee(id,r);
    }
}
