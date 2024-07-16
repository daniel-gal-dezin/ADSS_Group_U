package DataAccessLayer;

import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExpAndDamagedMapper {

    private static final String MESSAGE_TABLE_NAME = "ExpAndDamaged";
    private final String connectionString;
    private final String tableName;

    public ExpAndDamagedMapper() {
        String path = Paths.get(System.getProperty("user.dir"), "stockDB.db").toAbsolutePath().toString();
        //this.connectionString = "jdbc:sqlite:" + path;
        this.connectionString = "jdbc:sqlite:roniandhadas/ADSS_Group_U/dev/stockDB.db";
        this.tableName = MESSAGE_TABLE_NAME;

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<ExpAndDamagedDTO> select() {
        List<ExpAndDamagedDTO> results = new ArrayList<>();
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

    public List<ExpAndDamagedDTO> selectAllExpAndDamaged() {
        return new ArrayList<>(select());
    }

    public ExpAndDamagedDTO convertReaderToObject(ResultSet resultSet) throws SQLException {
        String type=resultSet.getString(1);
        String store=resultSet.getString(2);
        String category=resultSet.getString(3);
        String subcategory=resultSet.getString(4);
        int serialNumber=resultSet.getInt(5);
        int amount=resultSet.getInt(6);
        return new ExpAndDamagedDTO(type, store, category, subcategory, serialNumber, amount);
    }

    public boolean insert(ExpAndDamagedDTO expAndD) throws Exception {
        String query = "INSERT INTO " + tableName + " (Type, Store, Category, Subcategory, SerialNumber, Amount) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, expAndD.getType());
            statement.setString(2, expAndD.getStore());
            statement.setString(3, expAndD.getCategory());
            statement.setString(4, expAndD.getSubcategory());
            statement.setInt(5, expAndD.getSerialNumber());
            statement.setInt(6, expAndD.getAmount());
            int res = statement.executeUpdate();
            return res > 0;
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        }
    }

//    public List<ExpAndDamagedDTO> selectItemsPerStoreAndCat(String store, String category) {
//        List<ExpAndDamagedDTO> results = new ArrayList<>();
//        String sqlQuery = "SELECT * FROM ExpAndDamaged WHERE Store = ? AND Category = ?";
//        try (Connection connection = DriverManager.getConnection(connectionString);
//             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
//            preparedStatement.setString(1, store);
//            preparedStatement.setString(2, category);
//            try (ResultSet resultSet = preparedStatement.executeQuery()) {
//                while (resultSet.next()) {
//                    results.add(convertReaderToObject(resultSet));
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return results;
//    }

    public boolean removeProCategory(String type, String store, String category, int serialNumber) throws Exception {
        String query = "DELETE FROM " + tableName + " WHERE Type = ? AND Store = ? AND Category = ? AND SerialNumber = ?";
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, type);
            statement.setString(2, store);
            statement.setString(3, category);
            statement.setInt(4, serialNumber);
            int res = statement.executeUpdate();
            return res > 0;
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        }
    }


    public boolean increaseAmount(String type, String store, int serialNumber, int amount) throws Exception {
        String query = "UPDATE " + tableName + " SET Amount = Amount + ? WHERE Type = ? AND Store = ? AND SerialNumber = ?";
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, amount);
            statement.setString(2, type);
            statement.setString(3, store);
            statement.setInt(4, serialNumber);
            int res = statement.executeUpdate();
            return res > 0;
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        }
    }
}
