package Data_Access_Layer;


import Domain_Layer.Delivery;
import Domain_Layer.Employee;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DeliveryDAO {
    private final String url = "docs/DB.db";
    private final EmployeeDAO employeeDAO = new EmployeeDAO();

    public void insertDelivery(Delivery delivery) {
        String sql = "INSERT INTO Delivery(DELIVERY_ID, Drive_id, store_keeper_id, license) VALUES(?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, delivery.getDeliveryid());
            pstmt.setInt(2, delivery.getDriver().getId());
            pstmt.setInt(3, delivery.getStore_keeper().getId());
            pstmt.setString(4,String.valueOf(delivery.getLicenseneeded()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Delivery getDelivery(int id) {
        String sql = "SELECT * FROM Delivery WHERE DELIVERY_ID = ?";
        Delivery delivery = null;

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Employee driver = employeeDAO.getEmployee(rs.getInt("Drive-id"));
                Employee storeKeeper = employeeDAO.getEmployee(rs.getInt("store-keeper-id"));
                delivery = new Delivery(rs.getInt("DELIVERY_ID"),
                        driver,
                        storeKeeper,
                        rs.getString("license").charAt(0));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return delivery;
    }

    public void updateDelivery(Delivery delivery)  {
        String sql = "UPDATE Delivery SET Drive_id = ?, store_keeper_id = ?, license = ? WHERE DELIVERY_ID = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, delivery.getDriver().getId());
            pstmt.setInt(2, delivery.getStore_keeper().getId());
            pstmt.setString(3,String.valueOf( delivery.getLicenseneeded()));
            pstmt.setInt(4, delivery.getDeliveryid());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteDelivery(int id) {
        String sql = "DELETE FROM Delivery WHERE DELIVERY_ID = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    //ShifttoDelivery table

    public void addDeliveryToShift(LocalDate date, String shiftType, int deliveryId) {
        String sql = "INSERT INTO ShifttoDelivery(date, sType, DELIVERY_ID) VALUES(?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, Date.valueOf(date));
            pstmt.setString(2, shiftType);
            pstmt.setInt(3, deliveryId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Delivery> getDeliveriesForShift(LocalDate date, String shiftType) throws SQLException {
        String sql = "SELECT DELIVERY_ID FROM ShifttoDelivery WHERE date = ? AND sType = ?";
        List<Delivery> deliveries = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDate(1, Date.valueOf(date));
            pstmt.setString(2, shiftType);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Delivery delivery = getDelivery(rs.getInt("DELIVERY_ID"));
                if (delivery != null) {
                    deliveries.add(delivery);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return deliveries;
    }


    public void deleteShifttoDelivery(int id){
        String sql = "DELETE FROM ShifttoDelivey WHERE DELIVERY_ID = ?";



        try(Connection conn = DriverManager.getConnection(url);
        PreparedStatement pstmt =conn.prepareStatement(sql)){
            pstmt.setInt(1,id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}