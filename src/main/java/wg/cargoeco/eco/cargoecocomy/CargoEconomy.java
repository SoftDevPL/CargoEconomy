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
import wg.cargoeco.eco.cargoecocomy.database.SQLManager;
import wg.cargoeco.eco.cargoecocomy.engine.BudgetEconomy;

import java.util.Arrays;


public final class CargoEconomy extends JavaPlugin {

    @Getter
    private static CargoEconomy instance;
    @Getter
    private Economy economy = null;
    @Getter
    private SQLManager sqlManager;


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
        sqlManager = new SQLManager();
        sqlManager.init();
        new CommandsManager().init();


        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") != null) {
            economy = new BudgetEconomy();
            Bukkit.getServer().getServicesManager().register(Economy.class, economy,
                    this, ServicePriority.Highest);
        }
        enablingMessage();
    }

    @Override
    public void onDisable() {
        disablingMessage();
        instance = null;
    }

    private void enablingMessage() {
        getServer().getConsoleSender().sendMessage(convertColors(""));
        getServer().getConsoleSender().sendMessage(convertColors("             &2CCCCCCCCCCCCC     &aEEEEEEEEEEEEEEEEEEEEEE"));
        getServer().getConsoleSender().sendMessage(convertColors("            &2CCC::::::::::::C   &aE::::::::::::::::::::E"));
        getServer().getConsoleSender().sendMessage(convertColors("          &2CC:::::::::::::::C   &aE::::::::::::::::::::E"));
        getServer().getConsoleSender().sendMessage(convertColors("         &2C:::::CCCCCCCC::::C   &aEE::::::EEEEEEEEE::::E"));
        getServer().getConsoleSender().sendMessage(convertColors("       &2C:::::C       CCCCCC     &aE:::::E       EEEEEE"));
        getServer().getConsoleSender().sendMessage(convertColors("      &2C:::::C                   &aE:::::E"));
        getServer().getConsoleSender().sendMessage(convertColors("      &2C:::::C                   &aE::::::EEEEEEEEEE             &fCargoEconomy v" + getPluginVersion()));
        getServer().getConsoleSender().sendMessage(convertColors("      &2C:::::C                   &aE:::::::::::::::E             &fRunning on Spigot - " + getMinecraftVersion(Bukkit.getServer())));
        getServer().getConsoleSender().sendMessage(convertColors("      &2C:::::C                   &aE:::::::::::::::E             &fMade by DevieTeam"));
        getServer().getConsoleSender().sendMessage(convertColors("      &2C:::::C                   &aE::::::EEEEEEEEEE"));
        getServer().getConsoleSender().sendMessage(convertColors("      &2C:::::C                   &aE:::::E"));
        getServer().getConsoleSender().sendMessage(convertColors("       &2C:::::C       CCCCCC     &aE:::::E       EEEEEE"));
        getServer().getConsoleSender().sendMessage(convertColors("         &2C:::::CCCCCCCC::::C   &aEE::::::EEEEEEEE:::::E"));
        getServer().getConsoleSender().sendMessage(convertColors("          &2CC:::::::::::::::C   &aE::::::::::::::::::::E"));
        getServer().getConsoleSender().sendMessage(convertColors("            &2CCC::::::::::::C   &aE::::::::::::::::::::E"));
        getServer().getConsoleSender().sendMessage(convertColors("               &2CCCCCCCCCCCCC   &aEEEEEEEEEEEEEEEEEEEEEE"));
        getServer().getConsoleSender().sendMessage(convertColors(""));
        getServer().getConsoleSender().sendMessage(convertColors("                    &fAction: &bPlugin Enabled!"));
    }

    private void disablingMessage() {
        getServer().getConsoleSender().sendMessage(convertColors(""));
        getServer().getConsoleSender().sendMessage(convertColors("             &2CCCCCCCCCCCCC     &aEEEEEEEEEEEEEEEEEEEEEE"));
        getServer().getConsoleSender().sendMessage(convertColors("            &2CCC::::::::::::C   &aE::::::::::::::::::::E"));
        getServer().getConsoleSender().sendMessage(convertColors("          &2CC:::::::::::::::C   &aE::::::::::::::::::::E"));
        getServer().getConsoleSender().sendMessage(convertColors("         &2C:::::CCCCCCCC::::C   &aEE::::::EEEEEEEEE::::E"));
        getServer().getConsoleSender().sendMessage(convertColors("       &2C:::::C       CCCCCC     &aE:::::E       EEEEEE"));
        getServer().getConsoleSender().sendMessage(convertColors("      &2C:::::C                   &aE:::::E"));
        getServer().getConsoleSender().sendMessage(convertColors("      &2C:::::C                   &aE::::::EEEEEEEEEE             &fCargoEconomy v" + getPluginVersion()));
        getServer().getConsoleSender().sendMessage(convertColors("      &2C:::::C                   &aE:::::::::::::::E             &fRunning on Spigot - " + getMinecraftVersion(Bukkit.getServer())));
        getServer().getConsoleSender().sendMessage(convertColors("      &2C:::::C                   &aE:::::::::::::::E             &fMade by DevieTeam"));
        getServer().getConsoleSender().sendMessage(convertColors("      &2C:::::C                   &aE::::::EEEEEEEEEE"));
        getServer().getConsoleSender().sendMessage(convertColors("      &2C:::::C                   &aE:::::E"));
        getServer().getConsoleSender().sendMessage(convertColors("       &2C:::::C       CCCCCC     &aE:::::E       EEEEEE"));
        getServer().getConsoleSender().sendMessage(convertColors("         &2C:::::CCCCCCCC::::C   &aEE::::::EEEEEEEE:::::E"));
        getServer().getConsoleSender().sendMessage(convertColors("          &2CC:::::::::::::::C   &aE::::::::::::::::::::E"));
        getServer().getConsoleSender().sendMessage(convertColors("            &2CCC::::::::::::C   &aE::::::::::::::::::::E"));
        getServer().getConsoleSender().sendMessage(convertColors("               &2CCCCCCCCCCCCC   &aEEEEEEEEEEEEEEEEEEEEEE"));
        getServer().getConsoleSender().sendMessage(convertColors(""));
        getServer().getConsoleSender().sendMessage(convertColors("                      &fAction: &cDisabling....."));
    }


    @Nullable
    public static OfflinePlayer getOfflinePlayer(String name) {
        return Arrays.stream(Bukkit.getOfflinePlayers()).filter(player ->
                player.getName().equalsIgnoreCase(name)).findAny().orElse(null);
    }
}
