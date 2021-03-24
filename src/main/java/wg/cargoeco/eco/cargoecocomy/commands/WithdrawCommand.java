package wg.cargoeco.eco.cargoecocomy.commands;

import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import wg.cargoeco.eco.cargoecocomy.CargoEconomy;

public class WithdrawCommand extends EconomyCommand {
    public WithdrawCommand(PluginCommand command) {
        super(command);
    }

    @Override
    public boolean manageMoney(Player sender, double amount, OfflinePlayer typedPlayer) {
        EconomyResponse result = economy.withdrawPlayer(typedPlayer, amount);
        if(result.type == EconomyResponse.ResponseType.FAILURE){
            sender.sendMessage(CargoEconomy.convertColors("&cPlayer does not have so much money"));
            return true;
        }
        sender.sendMessage(CargoEconomy.convertColors("&aSuccessfully removed: &f&l"
                + economy.format(result.amount) + "&afrom &f&l" + typedPlayer.getName() + "'s account"));
        return true;
    }
}
