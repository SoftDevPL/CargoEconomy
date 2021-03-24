package wg.cargoeco.eco.cargoecocomy.commands;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import wg.cargoeco.eco.cargoecocomy.CargoEconomy;

public class TransferCommand implements CommandExecutor {

    private final Economy economy;


    public TransferCommand(PluginCommand command){
        command.setExecutor(this);
        economy = CargoEconomy.getInstance().getEconomy();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return false;
    }
}
