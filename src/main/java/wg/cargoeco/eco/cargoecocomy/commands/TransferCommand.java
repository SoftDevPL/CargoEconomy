package wg.cargoeco.eco.cargoecocomy.commands;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import wg.cargoeco.eco.cargoecocomy.CargoEconomy;

public class TransferCommand implements CommandExecutor {

    private final Economy economy;


    public TransferCommand(PluginCommand command){
        command.setExecutor(this);
        economy = CargoEconomy.getInstance().getEconomy();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //sender is not a player
        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "Only player can execute this command");
            return true;
        }

        //no arguments
        if(args.length < 2){
            sender.sendMessage(CommandsManager.getDescription(label, command));
            return true;
        }

        //wrong amount format
        double amount = 0;
        try{
            amount = Double.parseDouble(args[1]);
        }
        catch (NumberFormatException e){
            sender.sendMessage(ChatColor.RED + "Wrong second argument");
            return true;
        }

        //receiver does not exists
        OfflinePlayer playerToSend = CargoEconomy.getOfflinePlayer(args[0]);
        if(playerToSend==null){
            sender.sendMessage(ChatColor.RED + "Player " + args[0] + " not found");
            return true;
        }

        //trying sending money
        Player player = (Player) sender;
        EconomyResponse result = economy.withdrawPlayer(player, amount);
        if(result.type == EconomyResponse.ResponseType.FAILURE){
            player.sendMessage(ChatColor.RED + "You don't have enough money!");
            return true;
        }
        economy.depositPlayer(playerToSend, result.amount);
        player.sendMessage(ChatColor.GREEN + "Successfully transfer your money");
        if(playerToSend.isOnline()){
            playerToSend.getPlayer().sendMessage(ChatColor.GREEN + "You receive: " + economy.format(result.amount) +
                    " from " + player.getName());
        }
        return true;
    }
}
