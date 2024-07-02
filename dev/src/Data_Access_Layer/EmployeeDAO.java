package Data_Access_Layer;

import Domain_Layer.Employee;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Domain_Layer.Role;

public class EmployeeDAO extends DAO {
    final String url = "docs/DB.db";

    public void insertEmployee(Employee em)  {
        String sql = "INSERT INTO Employee(ID,name,\"bank-account\",\"start-work\",\"employment-type\", \"salary-type\", vacation_days,license, ismanager) VALUES(?,?,?,?,?,?,?,?,?)";

        try(Connection conn = DriverManager.getConnection(url)){
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,em.getId());
            ps.setString(2, em.getName());
            ps.setString(3,em.getBankAccount());
            ps.setDate(4,super.fromlocaltodate(em.getTermsofem().getStartWork()));
            ps.setString(5, em.getTermsofem().employmentType);
            ps.setString(6, em.getTermsofem().salaryType);
            ps.setInt(7,em.getTermsofem().vacationDays);
            ps.setString(8, String.valueOf(em.getLicense()));
            ps.setInt(9,em.isIsmanagar() ? 1:0);
            ps.executeUpdate();
        }
        catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public Employee getEmployee(int id){
        String sql = "SELECT * FROM Employee WHERE ID = ?";
        Employee em = null;

        try(Connection conn = DriverManager.getConnection(url)){
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
                        rs.getInt("vacation_days"),
                        rs.getInt("ismanger") == 0 ? false:true
                        );
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
            return em;
        }
        return em;
    }



    public void updateEmployee(Employee em) {
        String sql = "UPDATE Employee SET name = ?, bank_account = ?, start_work = ?, employment_type = ?, salary_type = ?, vacation_days = ?, license = ?, is_manager = ? WHERE ID = ?";

        try (Connection conn = DriverManager.getConnection(url)) {
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
        }
    }

    public void deleteEmployee(int id) {
        String sql = "DELETE FROM Employee WHERE ID = ?";

        try (Connection conn = DriverManager.getConnection(url)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Employee> getAllEmployees() {
        String sql = "SELECT * FROM Employee";
        List<Employee> employees = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

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
                        rs.getInt("vacation_days"),
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

        try (Connection conn = DriverManager.getConnection(url)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(2, id);
            ps.setString(1,r);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeRoleFromEmployee(int id, String r){
        String sql = "DELETE FROM employeetoroles WHERE \"em-id\" = ?";

        try (Connection conn = DriverManager.getConnection(url)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeEmployeeRoles(int id){
        String sql = "DELETE FROM employeetoroles WHERE \"em-id\" = ?  ";

        try (Connection conn = DriverManager.getConnection(url)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }





































    public List<Role> getRoles(int employeeId){
        List<String> ans = new ArrayList<>();
        String sql = "SELECT etr.role FROM Employeetoroles as etr "+
                "JOIN Employee as e ON etr.em-id = e.id "+
                "WHERE e.id = ?";
        try(Connection conn = DriverManager.getConnection(url)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, employeeId);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                ans.add(rs.getString("role"));
            }
        } catch(SQLException e){
            System.out.println(e.getMessage());
            return null;
        }
        return ans.stream().map((String r) -> Employee.convertRole(r)).toList();
    }
}
