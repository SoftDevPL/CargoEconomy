package wg.cargoeco.eco.cargoecocomy.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import wg.cargoeco.eco.cargoecocomy.CargoEconomy;

public class CommandsManager {

    public void init(){
        new GiveMoneyCommand(CargoEconomy.getInstance().getCommand("givemoney"));
        new ClearMoneyCommand(CargoEconomy.getInstance().getCommand("clearmoney"));
        new WithdrawCommand(CargoEconomy.getInstance().getCommand("withdrawmoney"));
        new TransferCommand(CargoEconomy.getInstance().getCommand("transfer"));
        new BalanceCommand(CargoEconomy.getInstance().getCommand("balance"));
    }

    public static String getDescription(String label, Command command) {
        String[] strings = command.getUsage().split(" ", 2);
        String usage = strings.length < 2 ? "" : " " + command.getUsage().split(" ", 2)[1];
        return ChatColor.GREEN + "" + ChatColor.BOLD + "Use" + ChatColor.GRAY + " => " + ChatColor.GREEN + "" + ChatColor.BOLD + "/" + label + ChatColor.GRAY + usage;
    }
}
