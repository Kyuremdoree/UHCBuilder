package switchuhc.uhc_builder.tasks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import switchuhc.uhc_builder.UHC_Builder;
import switchuhc.uhc_builder.utilitaires.Cycle;

public class DayNightCycleTask extends BukkitRunnable {

    UHC_Builder main;
    long fullCycleDuration = 24000;
    long dayDuration;
    long nightDuration;

    long dayStart = 0;
    long nightStart = 12000;

    long addition;

    public DayNightCycleTask(UHC_Builder pl) {
        main = pl;
        dayDuration = (long) main.getGame().getTemps().getCycleDayNight() * 20;
        nightDuration = dayDuration;
        addition = fullCycleDuration / (dayDuration*2);
    }
    long currentTick = 0;
    World world = Bukkit.getWorld("world");
    @Override
    public void run() {
        world.setTime(currentTick % fullCycleDuration);
        if(currentTick % fullCycleDuration == dayStart) {
            Bukkit.broadcastMessage(ChatColor.GOLD+"Le jour se lève !");
            main.getGame().setCycle(Cycle.Day);
        } else if (currentTick % fullCycleDuration == nightStart) {
            Bukkit.broadcastMessage(ChatColor.BLUE +"Le soleil se couche et la nuit se lève !");
            main.getGame().setCycle(Cycle.Night);
        }
        currentTick = (currentTick + addition) % fullCycleDuration;
    }
}
