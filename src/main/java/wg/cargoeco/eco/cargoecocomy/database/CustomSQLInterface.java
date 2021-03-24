package wg.cargoeco.eco.cargoecocomy.database;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import wg.cargoeco.eco.cargoecocomy.CargoEconomy;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.bukkit.Bukkit.getServer;

public class CustomSQLInterface {

    public String databaseName;
    public String databaseUrl;
    protected CargoEconomy plugin;
    protected boolean ok = true;
    File database;

    public static void close(Statement statement) {
        try {
            if (statement != null) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void init(String filename) {
        this.init(filename, true);
    }

    protected void init(String filename, boolean create) {
        this.plugin = CargoEconomy.getInstance();
        this.databaseName = filename + ".db";
        this.databaseUrl = "jdbc:sqlite:" + "plugins/" + this.plugin.getDataFolder().getName() + "/" + this.databaseName;
        database = new File(this.plugin.getDataFolder(), this.databaseName);
        if (create) init();
        else {
            if (!this.plugin.getDataFolder().exists()) {
                ok = false;
                return;
            }
            database = new File(this.plugin.getDataFolder(), this.databaseName);
            if (!database.exists()) {
                ok = false;
            }
        }
    }

    private void init() {
        CheckIfDatabaseExists();
    }

    boolean isOk() {
        return this.ok;
    }

    public void CheckIfDatabaseExists() {
        MakeFolderIfNotExists();
        if (!database.exists()) {
            createDatabase();
        }
    }

    protected boolean MakeFolderIfNotExists() {
        if (!this.plugin.getDataFolder().exists()) {
            return this.plugin.getDataFolder().mkdir();
        }
        return true;
    }

    public void createDatabase() {
        createNewDatabase();
    }

    public Connection connect() {
        Connection conn = null;
        try {
            // The newInstance() call is a work around for some
            // broken Java implementations
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
            // handle the error
        }
        try {
            conn = DriverManager.getConnection(this.databaseUrl);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void createNewDatabase() {

        this.databaseUrl = "jdbc:sqlite:" + "plugins/" + this.plugin.getDataFolder().getName() + "/" + this.databaseName;

        try (Connection conn = DriverManager.getConnection(this.databaseUrl)) {
            getServer().getConsoleSender().sendMessage("==> Database Initialization <==");
            if (conn != null) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + this.databaseName + ChatColor.GREEN + " -> database has been created!");
            } else {
                Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + this.databaseName + ChatColor.GREEN + " -> database has been loaded successfully!!");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
