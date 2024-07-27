package switchuhc.uhc_builder.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import switchuhc.uhc_builder.UHC_Builder;

import java.util.Arrays;

public class HostCommand implements CommandExecutor {
    private UHC_Builder main;
    public HostCommand(UHC_Builder pl) { this.main = pl; }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] strings) {
        if (!(sender instanceof Player) || !(sender.isOp()))  {
            sender.sendMessage(ChatColor.RED + "Vous n'avez pas les permissions pour faire cette action !");
            return false;
        }
        Player player = (Player) sender;
        if (!(label.equals("host"))) return false;
        if (strings.length == 0){
            player.sendMessage("- say <param>: émet un message à tout les joueurs \n- heal : permet de heal tout les joueurs");
            return true;
        }
        if (strings[0].toString().contains("say")){
            StringBuilder message = new StringBuilder();
            for (int i=1; i < strings.length;i++) {
                message.append(" ").append(strings[i]);
            }
            Bukkit.broadcastMessage("§4[Host] " + ChatColor.YELLOW + sender.getName() + "§f :" + message);
            return true;
        }
        if (strings[0].toString().contains("heal") && main.getGameStatue().ordinal() < 5) {
            Bukkit.getOnlinePlayers().forEach(players -> players.setHealth(players.getMaxHealth()));
            Bukkit.broadcastMessage(ChatColor.DARK_BLUE+"Tout les joueurs ont été soignez !");
            return true;
        }
        return false;
    }
}
