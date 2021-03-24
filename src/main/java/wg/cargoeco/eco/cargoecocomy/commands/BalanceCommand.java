package wg.cargoeco.eco.cargoecocomy.commands;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import wg.cargoeco.eco.cargoecocomy.CargoEconomy;

public class BalanceCommand implements CommandExecutor {

    private final Economy economy;

    public BalanceCommand(PluginCommand command) {
        command.setExecutor(this);
        economy = CargoEconomy.getInstance().getEconomy();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Only player can execute this command");
            return true;
        }
        if(args.length>0){
            OfflinePlayer player = CargoEconomy.getOfflinePlayer(args[0]);
            if(player == null) {
                sender.sendMessage(ChatColor.RED + "Player not found");
                return true;
            }
            sender.sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD + player.getName()
                    + ChatColor.RESET + ChatColor.GREEN + " has " + economy.format(economy.getBalance(player)));
            return true;
        }
        sender.sendMessage(ChatColor.DARK_GREEN + "" + ChatColor.BOLD +
                "Balance: " + ChatColor.RESET + ChatColor.GREEN + economy.format(economy.getBalance((Player) sender)));


        return true;
    }
}