package wg.cargoeco.eco.cargoecocomy.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;

public class CommandsManager {

    public void init(){

    }

    public static String getDescription(String label, Command command) {
        String[] strings = command.getUsage().split(" ", 2);
        String usage = strings.length < 2 ? "" : " " + command.getUsage().split(" ", 2)[1];
        return ChatColor.GREEN + "" + ChatColor.BOLD + "Use" + ChatColor.GRAY + " => " + ChatColor.GREEN + "" + ChatColor.BOLD + "/" + label + ChatColor.GRAY + usage;
    }
}
