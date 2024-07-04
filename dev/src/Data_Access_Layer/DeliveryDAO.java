package Data_Access_Layer;


import Domain_Layer.Delivery;
import Domain_Layer.Employee;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DeliveryDAO {
    private final String url = "jdbc:sqlite:docs/DB.db";
    private final EmployeeDAO employeeDAO = new EmployeeDAO();

    public void insertDelivery(Delivery delivery) {
        String sql = "INSERT INTO Delivery(DELIVERY_ID, \"Drive-id\", \"store-keeper-id\", license) VALUES(?, ?, ?, ?)";
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, delivery.getDeliveryid());
            pstmt.setInt(2, delivery.getDriver().getId());
            pstmt.setInt(3, delivery.getStore_keeper().getId());
            pstmt.setString(4, String.valueOf(delivery.getLicenseneeded()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }    finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public Delivery getDelivery(int id) {
        String sql = "SELECT * FROM Delivery WHERE DELIVERY_ID = ?";
        Delivery delivery = null;
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
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
        finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
        return delivery;
    }

    public void updateDelivery(Delivery delivery) {
        String sql = "UPDATE Delivery SET Drive_id = ?, store_keeper_id = ?, license = ? WHERE DELIVERY_ID = ?";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, delivery.getDriver().getId());
            pstmt.setInt(2, delivery.getStore_keeper().getId());
            pstmt.setString(3, String.valueOf(delivery.getLicenseneeded()));
            pstmt.setInt(4, delivery.getDeliveryid());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void deleteDelivery(int id) {
        String sql = "DELETE FROM Delivery WHERE DELIVERY_ID = ?";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }


    //ShifttoDelivery table

    public void addDeliveryToShift(LocalDate date, String shiftType, int deliveryId) {
        String sql = "INSERT INTO ShifttoDelivery(date, sType, DELIVERY_ID) VALUES(?, ?, ?)";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDate(1, Date.valueOf(date));
            pstmt.setString(2, shiftType);
            pstmt.setInt(3, deliveryId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public List<Delivery> getDeliveriesForShift(LocalDate date, String shiftType) {
        String sql = "SELECT DELIVERY_ID FROM ShifttoDelivery WHERE date = ? AND sType = ?";
        List<Delivery> deliveries = new ArrayList<>();
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
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
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

        return deliveries;
    }


    public void deleteShifttoDelivery(int id) {
        String sql = "DELETE FROM ShifttoDelivey WHERE DELIVERY_ID = ?";

        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

    }


}