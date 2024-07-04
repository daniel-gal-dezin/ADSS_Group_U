package Data_Access_Layer;

import Domain_Layer.Pair;
import Domain_Layer.Role;
import Domain_Layer.Shift;
import Domain_Layer.Employee;
import Domain_Layer.ShiftType;

import java.sql.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class ShiftDAO extends DAO {
    private final String url = "jdbc:sqlite:dev/src/resources/docs/DB.db";
    EmployeeDAO edao = new EmployeeDAO();


    public void insertShift(Shift shift)  {
        String sql = "INSERT INTO Shift(date, sType, constraint_deadline, start, end, managerid) VALUES(?, ?, ?, ?, ?, ?)";

        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            LocalDate date = shift.getShiftID().getFirst();
            String stype = shift.getShiftID().getSecond().toString();
            Date date1 =Date.valueOf(date);
            pstmt.setDate(1,date1 );
            pstmt.setString(2, stype);
            pstmt.setDate(3, Date.valueOf(shift.getDeadLine()));
            pstmt.setTime(4, shift.getStart());
            pstmt.setTime(5, shift.getEnd());
            pstmt.setInt(6, shift.getmanager().getId());

            pstmt.executeUpdate();


        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try{
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public Shift getShift(LocalDate date, String shift) {
        String sql = "SELECT * FROM Shift WHERE date = ? AND sType = ?";
        Shift s = null;

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            Date sqlDate = Date.valueOf(date);
            pstmt.setDate(1, sqlDate);
            pstmt.setString(2, shift);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {  // Ensure there is data in the result set
                List<Role> rolesneeded = getRolesneeded(date, shift);
                int managerid = rs.getInt("managerid");  // Ensure correct column name

                // Print managerid for debugging
                System.out.println("Manager ID: " + managerid);

                s = new Shift(new Pair<>(date, convertShiftType(shift)), rolesneeded, edao.getEmployee(managerid));
                List<Employee> workers = getEmployeesForShift(date, shift);
                for (Employee e : workers) {
                    s.addEmployee(e);
                }

                List<Employee> constraints = getconstraintForShift(date, shift);
                for (Employee e : constraints) {
                    s.addConstraint(e);
                }
            } else {
                System.out.println("No data found for date: " + date + " and shift: " + shift);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return s;
    }

    public void updateShift(Shift shift) {
        String sql = "UPDATE Shift SET constraint_deadline = ?, start = ?, end = ?, managerid = ? WHERE date = ? AND sType = ?";

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, Date.valueOf(shift.getDeadLine()));
            pstmt.setTime(2, shift.getStart());
            pstmt.setTime(3, shift.getEnd());
            pstmt.setInt(4, shift.getmanager().getId());
            pstmt.setDate(5, Date.valueOf(shift.getShiftID().getFirst()));
            pstmt.setString(6, shift.getShiftID().getSecond().toString());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }finally {
            try{
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void deleteShift(LocalDate date, String shiftType)   {
        String sql = "DELETE FROM Shift WHERE date = ? AND sType = ?";

        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, Date.valueOf(date));
            pstmt.setString(2, shiftType);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }finally {
            try{
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }



    public List<Shift> getAllShifts()   {
        String sql = "SELECT * FROM Shift";
        List<Shift> shifts = new ArrayList<>();

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                LocalDate date = rs.getDate("date").toLocalDate();
                String shiftType = rs.getString("sType");
                List<Role> rolesNeeded = getRolesneeded(date, shiftType);
                Shift shift = new Shift(new Pair<>(date, convertShiftType(shiftType)), rolesNeeded, edao.getEmployee(rs.getInt("managerid")));
                List<Employee> workers = getEmployeesForShift(date, shiftType.toUpperCase());
                for (Employee e : workers) {
                    shift.addEmployee(e);
                }

                List<Employee> constraints = getconstraintForShift(date, shiftType);
                for (Employee e : constraints) {
                    shift.addConstraint(e);
                }

                shifts.add(shift);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            try{
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        return shifts;
    }


    public void deleteshifttoroll(LocalDate date, String shiftType)   {
        String sql = "DELETE FROM Rolesneededforshift WHERE date = ? AND sType = ?";

        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, Date.valueOf(date));
            pstmt.setString(2, shiftType.toUpperCase());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }finally {
            try{
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }


    public List<Role> getRolesneeded(LocalDate date, String shift) {
        List<String> ans = new ArrayList<>();
        String sql = "SELECT role FROM Rolesneededforshift as rn " +
                "WHERE \"shift-date\" = ? AND shifttype = ?";

        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDate(1, Date.valueOf(date));
            ps.setString(2, shift);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ans.add(rs.getString("role"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }finally {
            try{
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        return ans.stream().map((String r) -> Employee.convertRole(r)).toList();
    }




/////////////////////////////////////// ShifttoConstranit

    public List<Employee> getconstraintForShift(LocalDate date, String shiftType) {
        String sql = "SELECT \"em-id\" FROM ShifttoConstraints " +
                "WHERE \"shift-date\" = ? AND \"shift-type\" = ?";
        List<Employee> employees = new ArrayList<>();
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, Date.valueOf(date));
            pstmt.setString(2, shiftType.toUpperCase());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Employee employee = edao.getEmployee(rs.getInt("em-id"));
                employees.add(employee);
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

    public void addConstraintToShift(LocalDate shiftDate, String shiftType, int employeeId)   {
        String sql = "INSERT INTO ShifttoConstraints(\"shift-date\", \"shift-type\", \"em-id\") VALUES(?, ?, ?)";
        Connection conn = null;

        try  {
            conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, Date.valueOf(shiftDate));
            pstmt.setString(2, shiftType.toUpperCase());
            pstmt.setInt(3, employeeId);
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

    public void deleteConstraintFromShift(LocalDate shiftDate, String shiftType, int employeeId)   {
        String sql = "DELETE FROM ShifttoConstraints WHERE \"shift-date\" = ? AND \"shift-type\" = ? AND \"em-id\" = ?";
        Connection conn = null;

        try  {
            conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, Date.valueOf(shiftDate));
            pstmt.setString(2, shiftType.toUpperCase());
            pstmt.setInt(3, employeeId);
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



    ///////////////////////////////////////Shiftworker table


    public List<Employee> getEmployeesForShift(LocalDate date, String shiftType) {
        String sql = "SELECT \"em-id\" FROM Shiftworker " +
                "WHERE \"shift-date\" = ? AND \"shift-type\" = ?";
        List<Employee> employees = new ArrayList<>();
        Connection conn = null;

        try  {
            conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, Date.valueOf(date));
            pstmt.setString(2, shiftType.toUpperCase());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Employee employee = edao.getEmployee(rs.getInt("em-id"));
                employees.add(employee);
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

    public void addWorkerToShift(LocalDate shiftDate, String shiftType, int employeeId)   {
        String sql = "INSERT INTO Shiftworker(\"shift-date\", \"shift-type\", \"em-id\") VALUES(?, ?, ?)";
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, Date.valueOf(shiftDate));
            pstmt.setString(2, shiftType.toUpperCase());
            pstmt.setInt(3, employeeId);
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

    public void deleteWorkerFromShift(LocalDate shiftDate, String shiftType, int employeeId)   {
        String sql = "DELETE FROM Shiftworker WHERE \"shift-date\" = ? AND \"shift-type\" = ? AND \"em-id\" = ?";
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, Date.valueOf(shiftDate));
            pstmt.setString(2, shiftType.toUpperCase());
            pstmt.setInt(3, employeeId);
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

    public void insertBlockedShift(LocalDate date, String sType, int bId){
        String sql = "INSERT INTO BranchtoBlockShifts(branchid, date, sType) VALUES(?, ?, ?)";
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDate(2, Date.valueOf(date));
            pstmt.setString(3, sType.toUpperCase());
            pstmt.setInt(1, bId);
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

    public void deleteBlockedShift(LocalDate date, String sType, int bId){
        String sql = "DELETE FROM BranchtoBlockShifts WHERE branchid = ? AND date = ? AND sType = ?";

        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDate(2, Date.valueOf(date));
            pstmt.setString(3, sType);
            pstmt.setInt(1, bId);
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

    public List<Shift> getBlockedShifs(int bId){
        String sql = "SELECT date, sType FROM BranchtoBlockShifts " +
                "WHERE branchid = ?";
        List<Shift> shifts = new ArrayList<>();

        Connection conn = null;

        try{
            conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, bId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Date d = rs.getDate("date");
                LocalDate localDate = d.toLocalDate(); // Convert java.sql.Date to java.time.LocalDate
                Shift shift = getShift(localDate,rs.getString("sType"));
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


/////////////////////////////////////BranchtoShift
public void insertShiftToBranch(LocalDate date, String sType, int bId){
    String sql = "INSERT INTO BranchtoShift(\"branch-ID\", \"Shift-date\", \"shift-type\") VALUES(?, ?, ?)";
    Connection conn = null;
    try  {
        conn = DriverManager.getConnection(url);
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, bId);
        pstmt.setDate(2, Date.valueOf(date));
        pstmt.setString(3, sType);

        pstmt.executeUpdate();


    } catch (SQLException e) {
        System.out.println(e);
    }finally {
        try{
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}

    public void deleteShiftFromBranch(LocalDate date, String sType, int bId){
        String sql = "DELETE FROM BranchtoShift WHERE \"Shift-date\" = ? AND \"shift-type\" = ? AND \"branch-ID\" = ?";
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, Date.valueOf(date));
            pstmt.setString(2, sType);
            pstmt.setInt(3, bId);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }finally {
            try{
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }












    public Time fromlocaltimetotime(LocalTime t) {
        return new Time(t.getHour(), t.getHour(), t.getSecond());
    }


    private ShiftType convertShiftType(String s) {
        if (s.toLowerCase().compareTo("morning") == 0)
            return ShiftType.MORNING;
        else if (s.toLowerCase().compareTo("evening") == 0)
            return ShiftType.EVENING;
        else
            throw new IllegalArgumentException("no such shift type '" + s + "'. only have morning or evening");
    }
}
