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

public class ClearMoneyCommand implements CommandExecutor {

    private final Economy economy;

    public ClearMoneyCommand(PluginCommand pluginCommand) {
        pluginCommand.setExecutor(this);
        economy = CargoEconomy.getInstance().getEconomy();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(CargoEconomy.convertColors("&cOnly player can execute this command"));
            return true;
        }

        if (args.length > 0) {
            OfflinePlayer player = CargoEconomy.getOfflinePlayer(args[0]);
            if (player == null) {
                sender.sendMessage(CargoEconomy.convertColors("&cPlayer not found"));
                return true;
            }
            economy.withdrawPlayer(player, economy.getBalance(player));
            sender.sendMessage(CargoEconomy.convertColors("&2Balance &ffor player: &f&l" + player.getName() + " &a has been reset"));
            return true;
        }
        economy.withdrawPlayer(((Player) sender).getPlayer(), economy.getBalance(((Player) sender).getPlayer()));
        sender.sendMessage(CargoEconomy.convertColors("&aYour balance has been reset"));

        return true;
    }
}
