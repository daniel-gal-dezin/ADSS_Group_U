package Data_Access_Layer;

import Domain_Layer.Pair;
import Domain_Layer.Role;
import Domain_Layer.Shift;
import Domain_Layer.Employee;
import Domain_Layer.ShiftType;

import java.sql.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ShiftDAO extends DAO {
    private final String url = "docs/DB.db";
    EmployeeDAO edao = EmployeeDAO.getEmployeeDAO();


    public void insertShift(Shift shift) throws SQLException {
        String sql = "INSERT INTO Shift(date, sType, constraint_deadline, start, end, managerid) VALUES(?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            LocalDate date = shift.getShiftID().getFirst();
            String stype = shift.getShiftID().getSecond().toString();
            pstmt.setDate(1, super.fromlocaltodate(shift.getShiftID().getFirst()));
            pstmt.setString(2, stype);
            pstmt.setDate(3, super.fromlocaltodate(shift.getDeadLine()));
            pstmt.setTime(4, fromlocaltimetotime(shift.getStart()));
            pstmt.setTime(5, fromlocaltimetotime(shift.getEnd()));
            pstmt.setInt(6,shift.getmanager().getId());

            pstmt.executeUpdate();


        } catch (SQLException e) {
            System.out.println(e);


        }


    }


    public Shift getShift(LocalDate date, String shift) throws SQLException {
        String sql = "SELECT * FROM Shift WHERE date = ? AND shiftType =?";
        Shift s = null;


        try(Connection conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql)){

            pstmt.setDate(1,new Date(date.getYear(), date.getMonthValue(), date.getDayOfMonth()));
            pstmt.setString(2,shift);
            ResultSet rs = pstmt.executeQuery();

            List<Role> rolesneeded = getRolesneeded(date, shift);
            s = new Shift(new Pair<LocalDate, ShiftType>(date,convertShiftType(shift)),rolesneeded,edao.getEmployee(rs.getInt("mangerid")));
            List<Employee> worker = getEmployeesForShift(date, shift);
            for(Employee e: worker){
                s.addEmployee(e);
            }

            List<Employee> constrains = getconstraintForShift(date,shift);
            for(Employee e: constrains){
                s.addConstraint(e);
            }





        }
        return s;
    }























    public List<Employee> getconstraintForShift(LocalDate date, String shiftType) {
        String sql = "SELECT se.employeeId FROM ShifttoConstraints se  " +
                "WHERE se.shiftDate = ? AND se.shiftType = ?";
        List<Employee> employees = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, Date.valueOf(date));
            pstmt.setString(2, shiftType);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Employee employee = edao.getEmployee(rs.getInt("ID"));
                employees.add(employee);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return employees;
    }




















    public List<Role> getRolesneeded(LocalDate date, String shift){
        List<String> ans = new ArrayList<>();
        String sql = "SELECT rn.role FROM Rolesneededforshift as rn "+
                "WHERE se.shiftDate = ? AND se.shiftType = ?";
        try(Connection conn = DriverManager.getConnection(url)) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDate(1,super.fromlocaltodate(date));
            ps.setString(2, shift);
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




















    public List<Employee> getEmployeesForShift(LocalDate date, String shiftType) {
        String sql = "SELECT e.employeeId FROM Employees e " +
                "JOIN ShiftEmployees se ON e.employeeId = se.employeeId " +
                "WHERE se.shiftDate = ? AND se.shiftType = ?";
        List<Employee> employees = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, Date.valueOf(date));
            pstmt.setString(2, shiftType);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Employee employee = edao.getEmployee(rs.getInt("ID"));
                employees.add(employee);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return employees;
    }






















    public Time fromlocaltimetotime(LocalTime t) {
        return new Time(t.getHour(), t.getHour(), t.getSecond());
    }

    private List<Role> createDefaultRolesNeeded() {
        List<Role> rolesneeded1 = new ArrayList<>();
        rolesneeded1.add(Role.CASHIER);// defualt role needed
        rolesneeded1.add(Role.DRIVER);
        rolesneeded1.add(Role.MANAGER);
        rolesneeded1.add(Role.STOREKEEPER);
        return rolesneeded1;
    }
    private ShiftType convertShiftType(String s){
        if(s.toLowerCase().compareTo("morning") == 0)
            return ShiftType.MORNING;
        else if(s.toLowerCase().compareTo("evening") == 0)
            return ShiftType.EVENING;
        else
            throw new IllegalArgumentException("no such shift type '" + s +"'. only have morning or evening");
    }
}
