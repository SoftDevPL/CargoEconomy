package wg.cargoeco.eco.cargoecocomy.database;

import java.sql.*;

public class Database extends CustomSQLInterface {

    public static final Integer PLAYER_UUID = 0;
    public static final Integer BALANCE = 1;
    private final String economyTableName = "EconomyTable";
    private final String playerUUID = "playerUUID";
    private final String balance = "balance";

    private void delete(String query) {
        try (Connection conn = Database.this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void insertSomething(DatabaseInsertion operation, String query) {
        try (Connection conn = Database.this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            operation.insert(pstmt);
            pstmt.executeUpdate();
            close(pstmt);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createTable(String query, String databaseUrl) {
        try (Connection conn = DriverManager.getConnection(databaseUrl);
             Statement stmt = conn.createStatement()) {
            stmt.execute(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean withdrawAmount(String playerUUID, double amount) {
        double playerBalance = this.getPlayerBalance(playerUUID);
        if (playerBalance < amount) {
            return false;
        }
        String sql = "UPDATE " + this.economyTableName + " SET " + this.balance + "= ? WHERE " + this.playerUUID + " = ?";
        insertSomething(pstmt -> {
            pstmt.setString(2, playerUUID);
            pstmt.setDouble(1, playerBalance - amount);
        }, sql);
        return true;
    }

    public void init() {
        super.init("AdminGui");
        createEconomyDatabase(this.economyTableName, this.playerUUID, this.balance);
    }

    public void createEconomyDatabase(String economyTableName, String playerUUID, String balance) {
        String sql = "CREATE TABLE IF NOT EXISTS " + economyTableName + " (" + playerUUID + " TEXT NOT NULL, " + balance + " DOUBLE NOT NULL);";
        this.createTable(sql, this.databaseUrl);
    }

    public void depositAmount(String playerUUID, double amount) {
        double playerBalance = this.getPlayerBalance(playerUUID);
        String sql = "UPDATE " + this.economyTableName + " SET " + this.balance + "= ? WHERE " + this.playerUUID + " = ?";
        insertSomething(pstmt -> {
            pstmt.setString(2, playerUUID);
            pstmt.setDouble(1, playerBalance + amount);
        }, sql);
    }

    public void createPlayerAccount(String playerUUID) {
        String sql = "INSERT INTO " + this.economyTableName + " (" + this.playerUUID + ", " + this.balance + ") VALUES(?,?)";
        insertSomething(pstmt -> {
            pstmt.setString(1, playerUUID);
            pstmt.setDouble(2, 0);
        }, sql);

    }

    public double getPlayerBalance(String playerUUID) {
        String sql = " SELECT * FROM " + this.economyTableName + this.playerUUID + " = " + "\"" + playerUUID + "\"";
        return new Worker<Double>().getSomething(rs -> rs.getDouble(this.balance), sql);
    }

    private interface DatabaseOperation<T> {
        T operate(ResultSet rs) throws SQLException;
    }

    private interface DatabaseInsertion {
        void insert(PreparedStatement pstmt) throws SQLException;
    }

    private class Worker<T> {
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
}
