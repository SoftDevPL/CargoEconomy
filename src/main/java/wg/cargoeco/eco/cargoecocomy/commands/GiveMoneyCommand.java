package wg.cargoeco.eco.cargoecocomy.commands;

import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import wg.cargoeco.eco.cargoecocomy.CargoEconomy;

public class GiveMoneyCommand extends EconomyCommand {

    public GiveMoneyCommand(PluginCommand command){
        super(command);
    }

    @Override
    public boolean manageMoney(Player sender, double amount, OfflinePlayer typedPlayer) {
        EconomyResponse result = economy.depositPlayer(typedPlayer, amount);
        if(result.type == EconomyResponse.ResponseType.SUCCESS){
            sender.sendMessage(CargoEconomy.convertColors("&aSuccessfully added &l" + economy.format(result.amount)
                    + "&r&a to " + typedPlayer.getName() + " account"));
            return true;
        }
        sender.sendMessage(CargoEconomy.convertColors("&cFailed to add money"));
        return true;
    }
}
