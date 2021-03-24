package wg.cargoeco.eco.cargoecocomy.commands;

import org.bukkit.command.Command;
import wg.cargoeco.eco.cargoecocomy.CargoEconomy;

public class CommandsManager {

    public void init(){
        new GiveMoneyCommand(CargoEconomy.getInstance().getCommand("givemoney"));
        new ClearMoneyCommand(CargoEconomy.getInstance().getCommand("clearmoney"));
        new WithdrawCommand(CargoEconomy.getInstance().getCommand("withdrawmoney"));
        new TransferCommand(CargoEconomy.getInstance().getCommand("transfermoney"));
        new BalanceCommand(CargoEconomy.getInstance().getCommand("balance"));
    }

    public static String getDescription(String label, Command command) {
        String[] strings = command.getUsage().split(" ", 2);
        String usage = strings.length < 2 ? "" : " " + command.getUsage().split(" ", 2)[1];
        return CargoEconomy.convertColors("&2&lUse &7=> &2&l/" + label + "&7" + usage);
    }
}
