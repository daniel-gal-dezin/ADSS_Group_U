package DataAccessLayer;

import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemsMapper {

    private static final String MESSAGE_TABLE_NAME = "Items";
    private final String connectionString;
    private final String tableName;

    public ItemsMapper() {
        String path = Paths.get(System.getProperty("user.dir"), "stockDB.db").toAbsolutePath().toString();
        this.connectionString = "jdbc:sqlite:" + path;
        this.tableName = MESSAGE_TABLE_NAME;
    }

    public List<ItemDTO> select() {
        List<ItemDTO> results = new ArrayList<>();
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

    public List<ItemDTO> selectAllItems() {
        return new ArrayList<>(select());
    }

    public ItemDTO convertReaderToObject(ResultSet resultSet) throws SQLException {
        int id=resultSet.getInt(1);
        int productId=resultSet.getInt(2);
        String expDate=resultSet.getString(3);
        String place=resultSet.getString(4);
        return new ItemDTO(productId, id, expDate, place);
    }

    public boolean insert(ItemDTO item) throws Exception {
        String query = "INSERT INTO " + tableName + " (ID, ProductId, ExpDate, Place) " +
                "VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, item.getId());
            statement.setInt(2, item.getProductId());
            statement.setString(3, item.getExpDate());
            statement.setString(4, item.getPlace());
            int res = statement.executeUpdate();
            return res > 0;
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        }
    }


    public boolean removeIt(int productId, int id) throws Exception {
        String query = "DELETE FROM " + tableName + " WHERE ProductId = ? AND ID = ?";
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, productId);
            statement.setInt(2, id);
            int res = statement.executeUpdate();
            return res > 0;
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public boolean updatePlace(int productId, int id, String place) throws Exception {
        String query = "UPDATE " + tableName + " SET Place = ? WHERE ProductId = ? AND ID = ?";
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, place);
            statement.setInt(2, productId);
            statement.setInt(3, id);
            int res = statement.executeUpdate();
            return res > 0;
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        }
    }
}
