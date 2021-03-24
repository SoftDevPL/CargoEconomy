package wg.cargoeco.eco.cargoecocomy.commands;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import wg.cargoeco.eco.cargoecocomy.CargoEconomy;
import wg.cargoeco.eco.cargoecocomy.engine.BudgetEconomy;

public class BalanceCommand implements CommandExecutor {

    private final BudgetEconomy economy;

    public BalanceCommand(PluginCommand command) {
        command.setExecutor(this);
        economy = CargoEconomy.getInstance().getEconomy();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(CargoEconomy.convertColors("&cOnly player can execute this command"));
            return true;
        }
        if(args.length>0){
            OfflinePlayer player = CargoEconomy.getOfflinePlayer(args[0]);
            if(player == null) {
                sender.sendMessage(CargoEconomy.convertColors("&cPlayer not found"));
                return true;
            }
            sender.sendMessage(CargoEconomy.convertColors("&fPlayer:&f&l " + player.getName() + "&f has: &a" + economy.format(economy.getBalance(player))));
            return true;
        }
        sender.sendMessage(CargoEconomy.convertColors("&fBalance: &a" + economy.format(economy.getBalance((Player) sender))));


        return true;
    }
}
