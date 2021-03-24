package wg.cargoeco.eco.cargoecocomy.database;

import java.sql.*;

public class Database extends CustomSQLInterface {

    public void delete(String query) {
        try (Connection conn = Database.this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertSomething(DatabaseInsertion operation, String query) {
        try (Connection conn = Database.this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            operation.insert(pstmt);
            pstmt.executeUpdate();
            close(pstmt);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createTable(String query, String databaseUrl) {
        try (Connection conn = DriverManager.getConnection(databaseUrl);
             Statement stmt = conn.createStatement()) {
            stmt.execute(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public interface DatabaseOperation<T> {
        T operate(ResultSet rs) throws SQLException;
    }


    /**
     * ================================
     * Database variables
     * ================================
     */

    private final String economyTableName = "EconomyTable";

    public class Worker<T> {
        public T getSomething(DatabaseOperation<T> operation, String query) {
            T temp = null;
            try (Connection conn = Database.this.connect();
                 PreparedStatement pstmt = conn.prepareStatement(query)) {
                ResultSet rs = pstmt.executeQuery();
                temp = operation.operate(rs);
                close(pstmt);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return temp;
        }
    }

    private final String playerUUID = "playerUUID";
    private final String balance = "balance";

    private boolean withdrawAmount(String playerUUID, double amount) {
        double playerBalance = this.getPlayerBalance(playerUUID);
        if (playerBalance < amount) {
            return false;
        }
        String sql = "UPDATE " + this.economyTableName + " SET " + this.balance + "= ? WHERE " + this.playerUUID + " = ?";
        insertSomething(pstmt -> {
            pstmt.setString(1, playerUUID);
            pstmt.setDouble(2, playerBalance - amount);
        }, sql);
        return true;
    }

    public static final Integer PLAYER_UUID = 0;
    public static final Integer BALANCE = 1;

    /**
     * ================================
     * Database queries
     * ================================
     */

    public void init() {
        super.init("AdminGui");
        createEconomyDatabase(this.economyTableName, this.playerUUID, this.balance);
    }


    private void createEconomyDatabase(String economyTableName, String playerUUID, String balance) {
        String sql =  "CREATE TABLE IF NOT EXISTS " + economyTableName + " (" + playerUUID + " TEXT NOT NULL, " + balance + " DOUBLE NOT NULL);";
        this.createTable(sql, this.databaseUrl);
    }

    private void depositAmount(String playerUUID, double amount) {
        double playerBalance = this.getPlayerBalance(playerUUID);
        String sql = "UPDATE " + this.economyTableName + " SET " + this.balance + "= ? WHERE " + this.playerUUID + " = ?";
        insertSomething(pstmt -> {
            pstmt.setString(1, playerUUID);
            pstmt.setDouble(2, playerBalance + amount);
        }, sql);
    }

    private void createPlayerAccount(String playerUUID) {
        String sql = "INSERT INTO " + this.economyTableName + " (" + this.playerUUID + ", " + this.balance + ") VALUES(?,?)";
        insertSomething(pstmt -> {
            pstmt.setString(1, playerUUID);
            pstmt.setDouble(2, 0);
        }, sql);

    }

    public interface DatabaseInsertion {
        void insert(PreparedStatement pstmt) throws SQLException;
    }

    private double getPlayerBalance(String playerUUID) {
        String sql = " SELECT * FROM " + this.economyTableName + this.playerUUID + " = " + "\"" + playerUUID + "\"";
        return new Worker<Double>().getSomething(rs -> rs.getDouble(this.balance), sql);
    }


}
