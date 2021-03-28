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


public final class CargoEconomy extends JavaPlugin {

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
        getServer().getConsoleSender().sendMessage(convertColors(" "));
        getServer().getConsoleSender().sendMessage(convertColors("        &2,ad8888ba,   &a88888888888 "));
        getServer().getConsoleSender().sendMessage(convertColors("       &2d8'      `8b  &a88          "));
        getServer().getConsoleSender().sendMessage(convertColors("      &2d8'            &a88          "));
        getServer().getConsoleSender().sendMessage(convertColors("      &288             &a88aaaaa     "));
        getServer().getConsoleSender().sendMessage(convertColors("      &288             &a88\"\"\"\"\""));
        getServer().getConsoleSender().sendMessage(convertColors("      &2Y8,            &a88          "));
        getServer().getConsoleSender().sendMessage(convertColors("       &2Y8a.    .a8P  &a88          "));
        getServer().getConsoleSender().sendMessage(convertColors("         &2`Y8888Y'    &a88888888888 "));
        getServer().getConsoleSender().sendMessage(convertColors(" "));
        getServer().getConsoleSender().sendMessage(convertColors("         &fCargoEconomy v    "+ getPluginVersion()));
        getServer().getConsoleSender().sendMessage(convertColors("         &fRunning on Spigot -  " + getMinecraftVersion(Bukkit.getServer())));
        getServer().getConsoleSender().sendMessage(convertColors("         &fMade by DevieTeam"));
        getServer().getConsoleSender().sendMessage(convertColors(" "));
        getServer().getConsoleSender().sendMessage(convertColors("         &fAction: &bPlugin Enabled!"));
        getServer().getConsoleSender().sendMessage(convertColors(" "));
    }

    private void disablingMessage() {
        getServer().getConsoleSender().sendMessage(convertColors(" "));
        getServer().getConsoleSender().sendMessage(convertColors("        &2,ad8888ba,   &a88888888888 "));
        getServer().getConsoleSender().sendMessage(convertColors("       &2d8'      `8b  &a88          "));
        getServer().getConsoleSender().sendMessage(convertColors("      &2d8'            &a88          "));
        getServer().getConsoleSender().sendMessage(convertColors("      &288             &a88aaaaa     "));
        getServer().getConsoleSender().sendMessage(convertColors("      &288             &a88\"\"\"\"\""));
        getServer().getConsoleSender().sendMessage(convertColors("      &2Y8,            &a88          "));
        getServer().getConsoleSender().sendMessage(convertColors("       &2Y8a.    .a8P  &a88          "));
        getServer().getConsoleSender().sendMessage(convertColors("         &2`Y8888Y'    &a88888888888 "));
        getServer().getConsoleSender().sendMessage(convertColors(" "));
        getServer().getConsoleSender().sendMessage(convertColors("         &fCargoEconomy v    "+ getPluginVersion()));
        getServer().getConsoleSender().sendMessage(convertColors("         &fRunning on Spigot -  " + getMinecraftVersion(Bukkit.getServer())));
        getServer().getConsoleSender().sendMessage(convertColors("         &fMade by DevieTeam"));
        getServer().getConsoleSender().sendMessage(convertColors(" "));
        getServer().getConsoleSender().sendMessage(convertColors("         &fAction: &cDisabling...."));
        getServer().getConsoleSender().sendMessage(convertColors(" "));
    }


    @Nullable
    public static OfflinePlayer getOfflinePlayer(String name) {
        return Arrays.stream(Bukkit.getOfflinePlayers()).filter(player ->
                player.getName().equalsIgnoreCase(name)).findAny().orElse(null);
    }
}
