package switchuhc.uhc_builder.tasks;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import switchuhc.uhc_builder.UHC_Builder;
import switchuhc.uhc_builder.utilitaires.GameStatue;

import java.util.Random;

public class StartTask extends BukkitRunnable {

    @Getter @Setter
    private UHC_Builder main;

    private int decompte = 5;

    public StartTask(UHC_Builder main) {
        this.main = main;
    }

    @Override
    public void run() {
        if (decompte == 0) {
            Bukkit.broadcastMessage("Début des téléportations !");
            Random random = new Random();
            Location Spawn;
            for (Player p : main.getPlayerList()) {
                p.getInventory().clear();
                for (int i = 0; i < (4 * 9); i++) {
                    if (main.getStartInventory().getItem(i) != null) {
                        p.getInventory().setItem(i, main.getStartInventory().getItem(i).clone());
                    } else continue;
                }
                p.getInventory().clear();
                p.setHealth(20);
                p.setFoodLevel(20);
                p.setExp(0);
                p.setLevel(0);
                p.setGameMode(GameMode.SURVIVAL);
                Spawn = new Location(Bukkit.getWorld("world"), random.nextInt(2 * main.getGame().getBordureSize() + 1) - main.getGame().getBordureSize(), 100, random.nextInt(2 * main.getGame().getBordureSize() + 1) - main.getGame().getBordureSize());
                p.teleport(Spawn);
            }
            main.setGameStatue(GameStatue.Starting);
            TpTask task = new TpTask(this.main);
            task.runTaskTimer(this.main,0,20);
            cancel();
        }
        else {
            Bukkit.broadcastMessage("Démarrage dans §b" + String.valueOf(decompte) + " §f!");
            decompte--;
        }
    }
}
