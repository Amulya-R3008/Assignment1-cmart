import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=EnergyTradingDB;integratedSecurity=true;encrypt=true;trustServerCertificate=true;";

    public static Connection getConnection() {
        try {
            // Load SQL Server JDBC driver (optional for newer versions)
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            // Get connection using Windows Authentication
            return DriverManager.getConnection(URL);
        } catch (ClassNotFoundException e) {
            System.out.println("SQL Server JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
        return null;
    }
}
