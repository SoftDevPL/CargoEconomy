package wg.cargoeco.eco.cargoecocomy.commands;

import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import wg.cargoeco.eco.cargoecocomy.CargoEconomy;

public class TransferCommand extends EconomyCommand {


    public TransferCommand(PluginCommand command){
        super(command);
    }

    @Override
    public boolean manageMoney(Player player, double amount, OfflinePlayer typedPlayer) {

        EconomyResponse result = economy.withdrawPlayer(player, amount);
        if(result.type == EconomyResponse.ResponseType.FAILURE){
            player.sendMessage(CargoEconomy.convertColors("&cYou don't have enough money!"));
            return true;
        }
        economy.depositPlayer(typedPlayer, result.amount);
        player.sendMessage(CargoEconomy.convertColors("&aSuccessfully transfer your money"));
        if(typedPlayer.isOnline()){
            typedPlayer.getPlayer().sendMessage(CargoEconomy.convertColors("&aYou receive: &f&l" + economy.format(result.amount) +
                    " &afrom &f" + player.getName()));
        }
        return true;
    }
}
