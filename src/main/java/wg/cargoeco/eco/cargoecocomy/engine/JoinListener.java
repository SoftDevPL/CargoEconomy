package wg.cargoeco.eco.cargoecocomy.engine;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import wg.cargoeco.eco.cargoecocomy.CargoEconomy;

public class JoinListener implements Listener {
    private BudgetEconomy economy;

    public void init(){
        Bukkit.getPluginManager().registerEvents(this, CargoEconomy.getInstance());
        economy = CargoEconomy.getInstance().getEconomy();
    }

    @EventHandler
    public void creatingAccounts(PlayerJoinEvent event){
        economy.createPlayerAccount(event.getPlayer());
    }
}
