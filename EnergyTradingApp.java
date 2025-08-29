import java.sql.*;
import java.util.Scanner;

public class EnergyTradingApp {

    private static Connection conn = null;
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        conn = DBConnection.getConnection();
        if (conn == null) {
            System.out.println("Database connection failed. Exiting...");
            return;
        }

        while (true) {
            System.out.println("\n===== Energy Trading Menu =====");
            System.out.println("1. Add a Trade");
            System.out.println("2. View All Trades");
            System.out.println("3. Update Trade");
            System.out.println("4. Delete Trade");
            System.out.println("5. Search Trades by Counterparty / Commodity");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1: addTrade(); break;
                case 2: viewTrades(); break;
                case 3: updateTrade(); break;
                case 4: deleteTrade(); break;
                case 5: searchTrades(); break;
                case 6:
                    System.out.println("Exiting...");
                    closeConnection();
                    return;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private static void addTrade() {
        try {
            System.out.print("Enter TradeDate (YYYY-MM-DD): ");
            String date = sc.nextLine();
            System.out.print("Enter Counterparty: ");
            String counterparty = sc.nextLine();
            System.out.print("Enter Commodity: ");
            String commodity = sc.nextLine();
            System.out.print("Enter Volume: ");
            double volume = sc.nextDouble();
            System.out.print("Enter Price: ");
            double price = sc.nextDouble();
            sc.nextLine(); // consume newline
            System.out.print("Enter TradeType (BUY/SELL): ");
            String tradeType = sc.nextLine().toUpperCase();

            String sql = "INSERT INTO dbo.Trades (TradeDate, Counterparty, Commodity, Volume, Price, TradeType) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, date);
            ps.setString(2, counterparty);
            ps.setString(3, commodity);
            ps.setDouble(4, volume);
            ps.setDouble(5, price);
            ps.setString(6, tradeType);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Trade added successfully!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void viewTrades() {
        try {
            String sql = "SELECT * FROM dbo.Trades";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            System.out.println("\n--- All Trades ---");
            while (rs.next()) {
                System.out.printf("ID: %d | Date: %s | Counterparty: %s | Commodity: %s | Volume: %.2f | Price: %.2f | TradeType: %s\n",
                        rs.getInt("TradeID"),
                        rs.getDate("TradeDate"),
                        rs.getString("Counterparty"),
                        rs.getString("Commodity"),
                        rs.getDouble("Volume"),
                        rs.getDouble("Price"),
                        rs.getString("TradeType"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateTrade() {
        try {
            System.out.print("Enter TradeID to update: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter new Price: ");
            double price = sc.nextDouble();
            System.out.print("Enter new Volume: ");
            double volume = sc.nextDouble();
            sc.nextLine();

            String sql = "UPDATE dbo.Trades SET Price = ?, Volume = ? WHERE TradeID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, price);
            ps.setDouble(2, volume);
            ps.setInt(3, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Trade updated successfully!");
            } else {
                System.out.println("No trade found with that ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteTrade() {
        try {
            System.out.print("Enter TradeID to delete: ");
            int id = sc.nextInt();
            sc.nextLine();

            String sql = "DELETE FROM dbo.Trades WHERE TradeID = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Trade deleted successfully!");
            } else {
                System.out.println("No trade found with that ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void searchTrades() {
        try {
            System.out.print("Search by (1 = Counterparty, 2 = Commodity): ");
            int choice = sc.nextInt();
            sc.nextLine();

            String sql;
            if (choice == 1) {
                System.out.print("Enter Counterparty: ");
                String counterparty = sc.nextLine();
                sql = "SELECT * FROM dbo.Trades WHERE Counterparty LIKE ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, "%" + counterparty + "%");
                ResultSet rs = ps.executeQuery();
                displaySearchResults(rs);

            } else if (choice == 2) {
                System.out.print("Enter Commodity: ");
                String commodity = sc.nextLine();
                sql = "SELECT * FROM dbo.Trades WHERE Commodity LIKE ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, "%" + commodity + "%");
                ResultSet rs = ps.executeQuery();
                displaySearchResults(rs);

            } else {
                System.out.println("Invalid choice.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void displaySearchResults(ResultSet rs) throws SQLException {
        System.out.println("\n--- Search Results ---");
        while (rs.next()) {
            System.out.printf("ID: %d | Date: %s | Counterparty: %s | Commodity: %s | Volume: %.2f | Price: %.2f | TradeType: %s\n",
                    rs.getInt("TradeID"),
                    rs.getDate("TradeDate"),
                    rs.getString("Counterparty"),
                    rs.getString("Commodity"),
                    rs.getDouble("Volume"),
                    rs.getDouble("Price"),
                    rs.getString("TradeType"));
        }
    }

    private static void closeConnection() {
        try {
            if (conn != null) conn.close();
            System.out.println("Database connection closed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
