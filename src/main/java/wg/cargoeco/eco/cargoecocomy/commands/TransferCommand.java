package wg.cargoeco.eco.cargoecocomy.commands;

import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;

public class TransferCommand extends EconomyCommand {


    public TransferCommand(PluginCommand command){
        super(command);
    }

    @Override
    public boolean manageMoney(Player player, double amount, OfflinePlayer typedPlayer) {

        EconomyResponse result = economy.withdrawPlayer(player, amount);
        if(result.type == EconomyResponse.ResponseType.FAILURE){
            player.sendMessage(ChatColor.RED + "You don't have enough money!");
            return true;
        }
        economy.depositPlayer(typedPlayer, result.amount);
        player.sendMessage(ChatColor.GREEN + "Successfully transfer your money");
        if(typedPlayer.isOnline()){
            typedPlayer.getPlayer().sendMessage(ChatColor.GREEN + "You receive: " + economy.format(result.amount) +
                    " from " + player.getName());
        }
        return true;
    }
}
