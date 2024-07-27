package switchuhc.uhc_builder.tasks;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import switchuhc.uhc_builder.UHC_Builder;
import switchuhc.uhc_builder.utilitaires.Cycle;

public class DayNightCycleTask extends BukkitRunnable {

    UHC_Builder main;
    long fullCycleDuration;
    long dayDuration;
    long nightDuration;

    // Début du jour et de la nuit en ticks
    long dayStart = 0;
    long nightStart;

    public DayNightCycleTask(UHC_Builder pl) {
        main = pl;
        dayDuration = main.getGame().getTemps().getCycleDayNight();
        nightDuration = dayDuration;
        nightStart = nightDuration;
        fullCycleDuration = (long) (dayDuration + nightDuration);
    }
    long currentTick = 0;
    World world = Bukkit.getWorld("world");
    @Override
    public void run() {
        if (currentTick < dayDuration) {
            world.setTime((dayStart + currentTick) % fullCycleDuration);
        } else {
            world.setTime((nightStart + (currentTick - dayDuration)) % fullCycleDuration);
        }
        if(currentTick % fullCycleDuration == dayStart) {
            Bukkit.broadcastMessage(ChatColor.GOLD+"Le jour se lève !");
            main.getGame().setCycle(Cycle.Day);
        } else if (currentTick % fullCycleDuration == nightStart) {
            Bukkit.broadcastMessage(ChatColor.GOLD+"Le jour se lève !");
            main.getGame().setCycle(Cycle.Day);
        }
        currentTick = (currentTick + 1) % fullCycleDuration;
    }
}
