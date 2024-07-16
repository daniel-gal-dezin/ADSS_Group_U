package DataAccessLayer;

import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductsMapper {
    private static final String MESSAGE_TABLE_NAME = "Products";
    private final String connectionString;
    private final String tableName;

    public ProductsMapper() {
        String path = Paths.get(System.getProperty("user.dir"), "stockDB.db").toAbsolutePath().toString();
//        this.connectionString = "jdbc:sqlite:" + path;
        this.connectionString = "jdbc:sqlite:roniandhadas/ADSS_Group_U/dev/stockDB.db";
        this.tableName = MESSAGE_TABLE_NAME;

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<ProductDTO> select() {
        List<ProductDTO> results = new ArrayList<>();
        String query = "SELECT * from " + tableName;
        try (Connection connection = DriverManager.getConnection(connectionString);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                results.add(convertReaderToObject(resultSet));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return results;
    }

    public List<ProductDTO> selectAllProducts() {
        return new ArrayList<>(select());
    }

    public ProductDTO convertReaderToObject(ResultSet resultSet) throws SQLException {
        int productId=resultSet.getInt(1);
        String store=resultSet.getString(2);
        String category=resultSet.getString(3);
        String subcategory=resultSet.getString(4);
        int serialNumber=resultSet.getInt(5);
        String name=resultSet.getString(6);
        int stock=resultSet.getInt(7);
        int shelf=resultSet.getInt(8);
        int aigle=resultSet.getInt(9);
        String producer=resultSet.getString(10);
        int cost=resultSet.getInt(11);
        int soldPrice=resultSet.getInt(12);
        int size=resultSet.getInt(13);
        int discount=resultSet.getInt(14);
        int minimumAmount=resultSet.getInt(15);
        return new ProductDTO(store, category, subcategory, serialNumber, name, stock, shelf, aigle, producer, cost, soldPrice, size, discount, minimumAmount);
    }

    public boolean insert(ProductDTO p) throws Exception {
        String query = "INSERT INTO " + tableName + " (ProductId, Store, Category, Subcategory, SerialNumber, Name, Stock, Shelf, Aigle, Producer, Cost, SoldPrice, Size, Discount, MinimumAmount) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, p.getProductId());
            statement.setString(2, p.getStore());
            statement.setString(3, p.getCategory());
            statement.setString(4, p.getSubcategory());
            statement.setInt(5, p.getSerialNumber());
            statement.setString(6, p.getName());
            statement.setInt(7, p.getStock());
            statement.setInt(8, p.getShelfNum());
            statement.setInt(9, p.getAigleNum());
            statement.setString(10, p.getProducer());
            statement.setInt(11, p.getCost());
            statement.setInt(12, p.getSoldPrice());
            statement.setInt(13, p.getSize());
            statement.setInt(14, p.getDiscount());
            statement.setInt(15, p.getMinimumAmount());
            int res = statement.executeUpdate();
            return res > 0;
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public boolean increaseStock(int id) throws Exception {
        String query = "UPDATE " + tableName + " SET Stock = Stock + 1 WHERE ProductId = ?";
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            int res = statement.executeUpdate();
            return res > 0;
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        }
    }


    public boolean updateMinimumAmount(int id, int amount) throws Exception {
        String query = "UPDATE " + tableName + " SET MinimumAmount = ? WHERE ProductId = ?";
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, amount);
            statement.setInt(2, id);
            int res = statement.executeUpdate();
            return res > 0;
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public boolean removePro(int id) throws Exception {
        String query = "DELETE FROM " + tableName + " WHERE ProductId = ?";
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            int res = statement.executeUpdate();
            return res > 0;
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        }
    }


    public boolean decreaseStock(int id) throws Exception {
        String query = "UPDATE " + tableName + " SET Stock = Stock - 1 WHERE ProductId = ?";
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            int res = statement.executeUpdate();
            return res > 0;
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public boolean updateDiscount(int id, int discount) throws Exception {
        String query = "UPDATE " + tableName + " SET Discount = ? WHERE ProductId = ?";
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, discount);
            statement.setInt(2, id);
            int res = statement.executeUpdate();
            return res > 0;
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        }
    }

}
