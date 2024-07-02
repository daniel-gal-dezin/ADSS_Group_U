package Data_Access_Layer;


import Domain_Layer.Branch;
import Domain_Layer.Employee;
import Domain_Layer.Shift;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BranchDAO {
    private final String url = "docs/DB.db";
    private ShiftDAO sDAO = new ShiftDAO();
    private EmployeeDAO eDAO = new EmployeeDAO();

    public BranchDAO() {
    }

    public void insertBranch(Branch branch)  {
        String sql = "INSERT INTO Branch(ID, name) VALUES(?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, branch.getId());
            pstmt.setString(2, branch.getName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Branch getBranch(int id)   {
        String sql = "SELECT * FROM Branch WHERE ID = ?";
        Branch branch = null;

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                branch = new Branch(rs.getInt("ID"), rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return branch;
    }

    public void updateBranch(Branch branch)   {
        String sql = "UPDATE Branch SET name = ? WHERE ID = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, branch.getName());
            pstmt.setInt(2, branch.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteBranch(int id)   {
        String sql = "DELETE FROM Branch WHERE ID = ?";

        try (Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Branch> getAllBranches()   {
        String sql = "SELECT * FROM Branch";
        List<Branch> branches = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Branch branch = new Branch(rs.getInt("ID"), rs.getString("name"));
                branches.add(branch);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return branches;
    }

    public List<Shift> getAllShiftsByBranch(int branchId)   {
        String sql = "SELECT s.date, s.sType FROM Shift s " +
                "JOIN BranchtoShift bs ON s.date = bs.Shift_date AND s.sType = bs.shift_type " +
                "WHERE bs.branch_ID = ?";
        List<Shift> shifts = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, branchId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                LocalDate date = rs.getDate("date").toLocalDate();
                String shiftType = rs.getString("sType");
                Shift shift = sDAO.getShift(date,shiftType);
                shifts.add(shift);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return shifts;
    }



    ////////////////////////BranchtoEmployee table
    public List<Employee> getAllEmployeeByBranch(int branchId) {
        String sql = "SELECT emp-id FROM BranchtoEmployee WHERE Branch-id = ?";
        List<Employee> employees = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, branchId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int employeeId = rs.getInt("emp-id");
                Employee employee = eDAO.getEmployee(employeeId);
                if (employee != null) {
                    employees.add(employee);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return employees;
    }



    public void insertEmployeeToBranch(int branchId, int employeeId) {
        String sql = "INSERT INTO BranchtoEmployee(Branch-id, emp-id) VALUES(?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, branchId);
            pstmt.setInt(2, employeeId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteEmployeeFromBranch(int branchId, int employeeId) {
        String sql = "DELETE FROM BranchtoEmployee WHERE Branch-id = ? AND emp-id = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, branchId);
            pstmt.setInt(2, employeeId);
            pstmt.executeUpdate();

            eDAO.deleteEmployee(employeeId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



}
