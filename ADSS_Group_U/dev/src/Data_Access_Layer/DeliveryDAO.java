package Data_Access_Layer;


import Domain_Layer.Delivery;
import Domain_Layer.Employee;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DeliveryDAO {
    private final String url = "jdbc:sqlite:ADSS_Group_U/dev/src/resources/docs/DB.db";
    private final String url2 = "jdbc:sqlite:roniandhadas/ADSS_Group_U/dev/stockDB.db";
    private final EmployeeDAO employeeDAO = new EmployeeDAO();

    public void insertDelivery(Delivery delivery) {
        String sql = "INSERT INTO Delivery(DELIVERY_ID, \"Drive-id\", \"store-keeper-id\", license, \"product-id\", \"item-id\") VALUES(?, ?, ?, ?, ?, ?)";
        Connection conn = null;
        try{
            conn = DriverManager.getConnection(url);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, delivery.getDeliveryid());
            pstmt.setInt(2, delivery.getDriver().getId());
            pstmt.setInt(3, delivery.getStore_keeper().getId());
            pstmt.setString(4, String.valueOf(delivery.getLicenseneeded()));
            pstmt.setInt(5,delivery.getProductId());
            pstmt.setInt(6,delivery.getItemId());
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


//    public  void removeItem(int productId, int itemId){
//        String sql = "DELETE FROM Items WHERE ID = ? AND ProductId = ?";
//        Connection conn = null;
//
//        try {
//            //String url1 = "jdev/src/resources/docs/DB.db/StockDB";
//            conn = DriverManager.getConnection(url);
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//            pstmt.setInt(1, itemId);
//            pstmt.setInt(2,productId);
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println("item does not exist");
//        } finally {
//            try {
//                conn.close();
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//
//        }
//
//    }
//
//    public  void updateProductStock(int productId){
//        String sql = "UPDATE products SET Stock = Stock - 1 WHERE ProductId = ?";
//        Connection conn = null;
//        try {
//            conn = DriverManager.getConnection(url);
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//            pstmt.setInt(1,productId);
//            pstmt.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println("item does not exist");
//        } finally {
//            try {
//                conn.close();
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }


    public void removeItem(int productId, int itemId) {
        String sql = "DELETE FROM Items WHERE ID = ? AND ProductId = ?";
        try (Connection conn = DriverManager.getConnection(url2);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, itemId);
            pstmt.setInt(2, productId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected == 0) {
                throw new Exception("Item with ID " + itemId + " and ProductId " + productId + " does not exist.");
            } else {
                System.out.println("Item removed successfully.");
            }

        } catch (SQLException e) {
            System.out.println("SQL error occurred: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public String getItem(int productId, int itemId) {
        String sql = "SELECT * FROM Items WHERE ProductId = ? AND ID = ?";
        try (Connection conn = DriverManager.getConnection(url2);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, productId);
            pstmt.setInt(2, itemId);
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) {
                return "Item not found";
            } else {
                return "Item found";
            }

        } catch (SQLException e) {
            return "SQL error occurred: " + e.getMessage();
        }
    }


    public void updateProductStock(int productId) {
        String sql = "UPDATE Products SET Stock = Stock - 1 WHERE ProductId = ?";
        try (Connection conn = DriverManager.getConnection(url2);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, productId);
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected == 0) {
                System.out.println("Product with ID " + productId + " does not exist or stock is already zero.");
            } else {
                System.out.println("Stock updated successfully for Product ID: " + productId);
            }

        } catch (SQLException e) {
            System.out.println("SQL error occurred: " + e.getMessage());
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
                int productId = rs.getInt("product-id");
                int itemId = rs.getInt("item-id");
                delivery = new Delivery(rs.getInt("DELIVERY_ID"),
                        driver,
                        storeKeeper,
                        rs.getString("license").charAt(0),
                        productId,
                        itemId);
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
        String sql = "UPDATE Delivery SET \"Drive-id\" = ?, \"store-keeper-id\" = ?, license = ? WHERE DELIVERY_ID = ?";
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
            pstmt.setString(2, shiftType.toUpperCase());
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
            pstmt.setString(2, shiftType.toUpperCase());
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
        String sql = "DELETE FROM ShifttoDelivery WHERE DELIVERY_ID = ?";

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