package Data_Access_Layer;

import Domain_Layer.Employee;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Domain_Layer.Role;

public class EmployeeDAO extends DAO {
    final String url = "jdbc:sqlite:ADSS_Group_U/dev/src/resources/docs/DB.db";

    public void insertEmployee(Employee em)  {
        String sql = "INSERT INTO Employee(ID,name,\"bank-account\",\"start-work\",\"employment-type\", \"salary-type\", vacations_days,license, ismanager, salary) VALUES(?,?,?,?,?,?,?,?,?,?)";
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,em.getId());
            ps.setString(2, em.getName());
            ps.setString(3,em.getBankAccount());
            ps.setDate(4,Date.valueOf(em.getTermsofem().getStartWork()));
            ps.setString(5, em.getTermsofem().employmentType);
            ps.setString(6, em.getTermsofem().salaryType);
            ps.setInt(7,em.getTermsofem().vacationDays);
            ps.setString(8, String.valueOf(em.getLicense()));
            ps.setInt(9,em.isIsmanagar() ? 1:0);
            ps.setInt(10,em.getSalary());
            ps.executeUpdate();
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public Employee getEmployee(int id){
        String sql = "SELECT * FROM Employee WHERE ID = ?";
        Employee em = null;
        Connection conn =null;

        try{
            conn = DriverManager.getConnection(url);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                Date d = rs.getDate("start-work");
                em = new Employee(rs.getInt("ID"),
                        rs.getString("name"),
                        rs.getString("bank-account"),
                        getRoles(id),
                        LocalDate.of(d.getYear(),d.getMonth(),d.getDay()),
                        rs.getString("license").charAt(0),
                        rs.getString("employment-type"),
                        rs.getString("salary-type"),
                        rs.getInt("salary"),
                        rs.getInt("vacations_days"),
                        rs.getInt("ismanager") == 0 ? false:true
                        );
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return em;
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        return em;
    }



    public void updateEmployee(Employee em) {
        String sql = "UPDATE Employee SET name = ?, \"bank-account\" = ?, \"start-work\" = ?, \"employment-type\" = ?, \"salary-type\" = ?, vacations_days = ?, license = ?, ismanager = ? WHERE ID = ?";
        Connection conn = null;
        try  {
            conn = DriverManager.getConnection(url);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, em.getName());
            ps.setString(2, em.getBankAccount());
            ps.setDate(3, super.fromlocaltodate(em.getTermsofem().getStartWork()));
            ps.setString(4, em.getTermsofem().employmentType);
            ps.setString(5, em.getTermsofem().salaryType);
            ps.setInt(6, em.getTermsofem().vacationDays);
            ps.setString(7, String.valueOf(em.getLicense()));
            ps.setInt(8, em.isIsmanagar() ? 1 : 0);
            ps.setInt(9, em.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void deleteEmployee(int id) {
        String sql = "DELETE FROM Employee WHERE ID = ?";
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public List<Employee> getAllEmployees() {
        String sql = "SELECT * FROM Employee";
        List<Employee> employees = new ArrayList<>();
        Connection conn = null;
        try{
             conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Date d = rs.getDate("start_work");
                Employee em = new Employee(rs.getInt("ID"),
                        rs.getString("name"),
                        rs.getString("bank_account"),
                        getRoles(rs.getInt("ID")),
                        LocalDate.of(d.toLocalDate().getYear(), d.toLocalDate().getMonthValue(), d.toLocalDate().getDayOfMonth()),
                        rs.getString("license").charAt(0),
                        rs.getString("employment_type"),
                        rs.getString("salary_type"),
                        rs.getInt("salary"),
                        rs.getInt("vacations_days"),
                        rs.getInt("is_manager") == 0 ? false : true
                );
                employees.add(em);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return employees;
    }

    //roles

    public void addRoleToEmployee(int id, String r){
        String sql = "INSERT INTO employeetoroles(role,\"em-id\") VALUES(?,?)";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(2, id);
            ps.setString(1,r);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public void removeRoleFromEmployee(int id, String r){
        String sql = "DELETE FROM employeetoroles WHERE \"em-id\" = ?";
            Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }




    public void removeEmployeeRoles(int id){
        String sql = "DELETE FROM employeetoroles WHERE \"em-id\" = ?  ";
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }





































    public List<Role> getRoles(int employeeId){
        List<String> ans = new ArrayList<>();
        String sql = "SELECT etr.role FROM Employeetoroles as etr "+
                "JOIN Employee as e ON etr.\"em-id\" = e.id "+
                "WHERE e.id = ?";
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, employeeId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                ans.add(rs.getString("role"));
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        return ans.stream().map((String r) -> Employee.convertRole(r)).toList();
    }
}
