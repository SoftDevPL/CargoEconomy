package wg.cargoeco.eco.cargoecocomy;

import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import wg.cargoeco.eco.cargoecocomy.engine.BudgetEconomy;

public final class CargoEconomy extends JavaPlugin {

    @Getter
    private static CargoEconomy instance;
    private BudgetEconomy economy = null;


    public static String convertColors(String st) {
        return ChatColor.translateAlternateColorCodes('&', st);
    }

    @Override
    public void onEnable() {
        instance = this;
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") != null) {
            economy = new BudgetEconomy();
            Bukkit.getServer().getServicesManager().register(Economy.class, economy,
                    this, ServicePriority.Highest);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        instance = null;
    }
}
