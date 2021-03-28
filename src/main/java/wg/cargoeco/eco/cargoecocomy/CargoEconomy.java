package wg.cargoeco.eco.cargoecocomy;

import com.sun.istack.internal.Nullable;
import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import wg.cargoeco.eco.cargoecocomy.commands.CommandsManager;
import wg.cargoeco.eco.cargoecocomy.configs.ConfigsManager;
import wg.cargoeco.eco.cargoecocomy.database.SQLManager;
import wg.cargoeco.eco.cargoecocomy.engine.BudgetEconomy;
import wg.cargoeco.eco.cargoecocomy.engine.JoinListener;

import java.util.Arrays;
import java.util.logging.Logger;


public final class CargoEconomy extends JavaPlugin {

    public final Logger logger =  Logger.getLogger("");
    public static final String ANSI_RESET = "\u001b[0m";
    public static final String ANSI_RED = "\u001b[31m";
    public static final String ANSI_CYAN = "\u001b[36m";
    public static final String ANSI_GREEN = "\u001b[32m";
    public static final String ANSI_BRIGHT_GREEN = "\u001b[32;1m";

    @Getter
    private static CargoEconomy instance;
    @Getter
    private BudgetEconomy economy;
    @Getter
    private SQLManager sqlManager;
    @Getter
    private ConfigsManager configsManager;


    public static String convertColors(String st) {
        return ChatColor.translateAlternateColorCodes('&', st);
    }

    private String getMinecraftVersion(Server server) {
        String version = server.getVersion();
        int start = version.indexOf("MC: ") + 4;
        int end = version.length() - 1;
        return version.substring(start, end);
    }

    private String getPluginVersion() {
        PluginDescriptionFile pdf = this.getDescription();
        return pdf.getVersion();
    }

    @Override
    public void onEnable() {
        instance = this;
        configsManager = new ConfigsManager();
        sqlManager = new SQLManager();

        configsManager.init();
        sqlManager.init();

        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") != null) {
            this.economy = new BudgetEconomy();
            Bukkit.getServer().getServicesManager().register(Economy.class, economy,
                    this, ServicePriority.Highest);
        }

        new JoinListener().init();
        new CommandsManager().init();

        enablingMessage();
    }

    @Override
    public void onDisable() {
        disablingMessage();
        instance = null;
    }

    private void enablingMessage() {
        logger.info(" ");
        logger.info(ANSI_GREEN + "        ,ad8888ba,  " + ANSI_BRIGHT_GREEN + " 88888888888 " + ANSI_RESET);
        logger.info(ANSI_GREEN + "       d8'      `8b " + ANSI_BRIGHT_GREEN + " 88          " + ANSI_RESET);
        logger.info(ANSI_GREEN + "      d8'           " + ANSI_BRIGHT_GREEN + " 88          " + ANSI_RESET);
        logger.info(ANSI_GREEN + "      88            " + ANSI_BRIGHT_GREEN + " 88aaaaa     " + ANSI_RESET);
        logger.info(ANSI_GREEN + "      88            " + ANSI_BRIGHT_GREEN + " 88\"\"\"\"\"" + ANSI_RESET);
        logger.info(ANSI_GREEN + "      Y8,           " + ANSI_BRIGHT_GREEN + " 88          " + ANSI_RESET);
        logger.info(ANSI_GREEN + "       Y8a.    .a8P " + ANSI_BRIGHT_GREEN + " 88          " + ANSI_RESET);
        logger.info(ANSI_GREEN + "         `Y8888Y'   " + ANSI_BRIGHT_GREEN + " 88888888888 " + ANSI_RESET);
        logger.info(" ");
        logger.info("         CargoEconomy v"+ getPluginVersion());
        logger.info("         Running on Spigot - " + getMinecraftVersion(Bukkit.getServer()));
        logger.info("         Made by DevieTeam");
        logger.info(" ");
        logger.info("         Action: " + ANSI_CYAN +"Plugin Enabled!" + ANSI_RESET);
        logger.info(" ");
    }

    private void disablingMessage() {
        logger.info(" ");
        logger.info(ANSI_GREEN + "        ,ad8888ba,  " + ANSI_BRIGHT_GREEN + " 88888888888 " + ANSI_RESET);
        logger.info(ANSI_GREEN + "       d8'      `8b " + ANSI_BRIGHT_GREEN + " 88          " + ANSI_RESET);
        logger.info(ANSI_GREEN + "      d8'           " + ANSI_BRIGHT_GREEN + " 88          " + ANSI_RESET);
        logger.info(ANSI_GREEN + "      88            " + ANSI_BRIGHT_GREEN + " 88aaaaa     " + ANSI_RESET);
        logger.info(ANSI_GREEN + "      88            " + ANSI_BRIGHT_GREEN + " 88\"\"\"\"\"" + ANSI_RESET);
        logger.info(ANSI_GREEN + "      Y8,           " + ANSI_BRIGHT_GREEN + " 88          " + ANSI_RESET);
        logger.info(ANSI_GREEN + "       Y8a.    .a8P " + ANSI_BRIGHT_GREEN + " 88          " + ANSI_RESET);
        logger.info(ANSI_GREEN + "         `Y8888Y'   " + ANSI_BRIGHT_GREEN + " 88888888888 " + ANSI_RESET);
        logger.info(" ");
        logger.info("         CargoEconomy v"+ getPluginVersion());
        logger.info("         Running on Spigot - " + getMinecraftVersion(Bukkit.getServer()));
        logger.info("         Made by DevieTeam");
        logger.info(" ");
        logger.info("         Action: " + ANSI_RED +"Disabling...." + ANSI_RESET);
        logger.info(" ");
    }


    @Nullable
    public static OfflinePlayer getOfflinePlayer(String name) {
        return Arrays.stream(Bukkit.getOfflinePlayers()).filter(player ->
                player.getName().equalsIgnoreCase(name)).findAny().orElse(null);
    }
}
