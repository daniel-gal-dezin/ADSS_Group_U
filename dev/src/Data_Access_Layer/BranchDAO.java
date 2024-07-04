package Data_Access_Layer;


import Domain_Layer.Branch;
import Domain_Layer.Employee;
import Domain_Layer.Pair;
import Domain_Layer.Shift;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BranchDAO {
    String url = "jdbc:sqlite:docs/DB.db";
    private ShiftDAO sDAO = new ShiftDAO();
    private EmployeeDAO eDAO = new EmployeeDAO();


    public BranchDAO(){
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertBranch(Branch branch)  {
        String sql = "INSERT INTO Branch(ID, name) VALUES(?, ?)";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, branch.getId());
            pstmt.setString(2, branch.getName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            try{
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Branch getBranch(int id)   {
        String sql = "SELECT * FROM Branch WHERE ID = ?";
        Branch branch = null;

        Connection conn = null;
        try  {
            conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                branch = new Branch(rs.getInt("ID"), rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            try{
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return branch;
    }

    public void updateBranch(Branch branch)   {
        String sql = "UPDATE Branch SET name = ? WHERE ID = ?";

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, branch.getName());
            pstmt.setInt(2, branch.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            try{
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void deleteBranch(int id)   {
        String sql = "DELETE FROM Branch WHERE ID = ?";

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            try{
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Map<Integer,String> getAllBranches()   {
        String sql = "SELECT * FROM Branch";
        Map<Integer,String> branches = new HashMap<>();

        Connection conn = null;
        try  {
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                branches.put(rs.getInt("ID"), rs.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            try{
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return branches;
    }

    public List<Shift> getAllShiftsByBranch(int branchId)   {
        String sql = "SELECT s.date, s.sType FROM Shift s " +
                "JOIN BranchtoShift bs ON s.date = bs.Shift_date AND s.sType = bs.shift_type " +
                "WHERE bs.\"branch-ID\" = ?";
        List<Shift> shifts = new ArrayList<>();

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, branchId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                LocalDate date = rs.getDate("Shift-date").toLocalDate();
                String shiftType = rs.getString("shift-type");
                Shift shift = sDAO.getShift(date,shiftType);
                shifts.add(shift);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            try{
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return shifts;
    }



    ////////////////////////BranchtoEmployee table
    public List<Employee> getAllEmployeeByBranch(int branchId) {
        String sql = "SELECT \"emp-id\" FROM BranchtoEmployee WHERE \"Branch-id\" = ?";
        List<Employee> employees = new ArrayList<>();

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
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
        }finally {
            try{
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return employees;
    }



    public void insertEmployeeToBranch(int branchId, int employeeId) {
        String sql = "INSERT INTO BranchtoEmployee(\"Branch-id\", \"emp-id\") VALUES(?, ?)";

        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, branchId);
            pstmt.setInt(2, employeeId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            try{
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void deleteEmployeeFromBranch(int branchId, int employeeId) {
        String sql = "DELETE FROM BranchtoEmployee WHERE \"Branch-id\" = ? AND emp-id = ?";

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, branchId);
            pstmt.setInt(2, employeeId);
            pstmt.executeUpdate();

            eDAO.deleteEmployee(employeeId);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            try{
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }



}
