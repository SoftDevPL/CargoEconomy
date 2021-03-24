package wg.cargoeco.eco.cargoecocomy.database;

import wg.cargoeco.eco.cargoecocomy.engine.Account;
import wg.cargoeco.eco.cargoecocomy.engine.Bank;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Database extends CustomSQLInterface {

    private final String economyTableName = "EconomyTable";
    private final String playerUUID = "playerUUID";
    private final String balance = "balance";

    private final String banksTable = "banksTable";
    private final String ownerUUID = "ownerUUID";
    private final String bankName = "bankName";
    private final String membersUUID = "membersUUID";
    private final String money = "money";

    public void init() {
        super.init("AdminGui");
        createEconomyTable(this.economyTableName, this.playerUUID, this.balance);
        createBanksTable(this.banksTable, this.ownerUUID, this.bankName, this.membersUUID, this.money);
    }

    private void delete(String query) {
        try (Connection conn = Database.this.connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private interface DatabaseOperation<T> {
        T operate(ResultSet rs) throws SQLException;
    }

    private interface DatabaseInsertion {
        void insert(PreparedStatement pstmt) throws SQLException;
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

    private void createTable(String query, String databaseUrl) {
        try (Connection conn = DriverManager.getConnection(databaseUrl);
             Statement stmt = conn.createStatement()) {
            stmt.execute(query);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void createEconomyTable(String economyTableName, String playerUUID, String balance) {
        String sql = "CREATE TABLE IF NOT EXISTS " + economyTableName + " (" + playerUUID + " TEXT NOT NULL, " + balance + " DOUBLE NOT NULL);";
        this.createTable(sql, this.databaseUrl);
    }
    public void createBanksTable(String banksTable, String ownerUUID, String bankName, String membersUUID, String money) {
        String sql = "CREATE TABLE IF NOT EXISTS " + banksTable + " (" + ownerUUID + " TEXT NOT NULL, " + bankName + " TEXT NOT NULL, " + membersUUID + " TEXT NOT NULL, " + money  + " DOUBLE NOT NULL);";
        this.createTable(sql, this.databaseUrl);

    }

    public void createBankAccount(Bank bank) {
        String sql = "INSERT INTO " + this.banksTable + " (" + this.ownerUUID + ", " + this.bankName + ", " + this.membersUUID + ", " + this.money + ") VALUES(?,?,?,?)";
        insertSomething(pstmt -> {
            pstmt.setString(1, bank.getOwnerUUID().toString());
            pstmt.setString(2, bank.getBankName());
            pstmt.setString(3, UUID.randomUUID().toString());
            pstmt.setDouble(4, 0);
        }, sql);
    }

    public void updatePlayerBalance(String playerUUID, double amount) {
        String sql = "UPDATE " + this.economyTableName + " SET " + this.balance + "= ? WHERE " + this.playerUUID + " = ?";
        insertSomething(pstmt -> {
            pstmt.setString(2, playerUUID);
            pstmt.setDouble(1, amount);
        }, sql);
    }

    public void updateBank(Bank bank) {
        String sql = "UPDATE " + this.banksTable + " SET " + this.bankName  +"= ?, SET "+ this.money  + "= ? WHERE " + this.ownerUUID + " = ?";
        insertSomething(pstmt -> {
            pstmt.setString(1, bank.getBankName());
            pstmt.setDouble(2, bank.getMoney());
            pstmt.setString(3, bank.getOwnerUUID().toString());
        }, sql);
    }


    public double getPlayerBalance(String playerUUID) {
        String sql = " SELECT * FROM " + this.economyTableName + this.playerUUID + " = " + "\"" + playerUUID + "\"";
        return new Worker<Double>().getSomething(rs -> rs.getDouble(this.balance), sql);
    }

    public void createPlayerAccount(String playerUUID) {
        String sql = "INSERT INTO " + this.economyTableName + " (" + this.playerUUID + ", " + this.balance + ") VALUES(?,?)";
        insertSomething(pstmt -> {
            pstmt.setString(1, playerUUID);
            pstmt.setDouble(2, 0);
        }, sql);
    }

    public List<Account> getAllAccounts() {
        String sql = "SELECT * FROM " + this.economyTableName;
        return new Worker<List<Account>>().getSomething(rs -> {
            List<Account> accounts = new ArrayList<>();
            while (rs.next()) {
                Account account = new Account(
                        UUID.fromString(rs.getString(this.playerUUID)),
                        rs.getDouble(this.balance)
                );
                accounts.add(account);
            }
            return accounts;
        }, sql);
    }

    public List<Bank> getAllBanks() {
        String sql = "SELECT * FROM " + this.banksTable;
        return new Worker<List<Bank>>().getSomething(rs -> {
            List<Bank> banks = new ArrayList<>();
            while (rs.next()) {
                Bank bank = new Bank(
                        UUID.fromString(rs.getString(this.ownerUUID)),
                        rs.getString(this.bankName),
                        // tutaj idzie members
                        rs.getDouble(this.money)
                );
                banks.add(bank);
            }
            return banks;
        }, sql);
    }

    public void deleteBank(Bank bank) {
        String sql = "DELETE FROM " + this.banksTable + " WHERE " + this.ownerUUID + " = " + "\"" + bank.getOwnerUUID().toString() + "\"";
        delete(sql);
    }
}
