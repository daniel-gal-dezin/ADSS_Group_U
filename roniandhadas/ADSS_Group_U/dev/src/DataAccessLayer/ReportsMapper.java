package DataAccessLayer;

import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReportsMapper {

    private static final String MESSAGE_TABLE_NAME = "Reports";
    private final String connectionString;
    private final String tableName;

    public ReportsMapper() {
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

    public List<ReportDTO> select() {
        List<ReportDTO> results = new ArrayList<>();
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

    public List<ReportDTO> selectAllReports() {
        return new ArrayList<>(select());
    }

    public ReportDTO convertReaderToObject(ResultSet resultSet) throws SQLException {
        String store=resultSet.getString(1);
        String type=resultSet.getString(2);
        String report=resultSet.getString(3);
        return new ReportDTO(store, type, report);
    }

    public boolean insert(ReportDTO report) throws Exception {
        String query = "INSERT INTO " + tableName + " (Store, Type, Report) " +
                "VALUES (?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, report.getStore());
            statement.setString(2, report.getType());
            statement.setString(3, report.getReport());
            int res = statement.executeUpdate();
            return res > 0;
        } catch (SQLException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public List<ReportDTO> selectReportsByStoreName(String store) {
        List<ReportDTO> results = new ArrayList<>();
        String sqlQuery = "SELECT * FROM StockReports WHERE Store = ?";
        try (Connection connection = DriverManager.getConnection(connectionString);
             PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, store);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    results.add(convertReaderToObject(resultSet));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }
}
