package switchuhc.uhc_builder.tasks;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import switchuhc.uhc_builder.UHC_Builder;
import switchuhc.uhc_builder.utilitaires.GameStatue;

public class TpTask extends BukkitRunnable implements Listener {
    private UHC_Builder main;
    public TpTask(UHC_Builder pl){
        this.main = pl;
    }

    private int timer = 5;

    @Override
    public void run()
    {
        if(timer == 0)
        {
            cancel();
            Bukkit.broadcastMessage("Téléportation Terminé !");
            main.setGameStatue(GameStatue.Mining);
            Bukkit.broadcastMessage("La partie COMMENCE !");
            GameTask task = new GameTask(this.main);
            task.runTaskTimer(this.main,0,20);
        }
        timer--;
    }

    @EventHandler
    public void OnMove(PlayerMoveEvent event){
        event.setCancelled(true);
    }
}
