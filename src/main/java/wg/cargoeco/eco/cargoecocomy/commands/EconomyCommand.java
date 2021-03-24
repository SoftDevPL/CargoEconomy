package wg.cargoeco.eco.cargoecocomy.commands;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import wg.cargoeco.eco.cargoecocomy.CargoEconomy;
import wg.cargoeco.eco.cargoecocomy.engine.BudgetEconomy;

public abstract class EconomyCommand implements CommandExecutor {

    protected final BudgetEconomy economy;

    public EconomyCommand(PluginCommand command) {
        this.economy = CargoEconomy.getInstance().getEconomy();
        command.setExecutor(this);
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

        return manageMoney((Player)sender, amount, playerToSend);
    }

    public abstract boolean manageMoney(Player sender, double amount, OfflinePlayer typedPlayer);

}
