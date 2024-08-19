package switchuhc.uhc_builder.classes;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import switchuhc.uhc_builder.UHC_Builder;

public class CustomTabList {
    private UHC_Builder main;

    @Getter
    private Scoreboard tabList;

    public CustomTabList(UHC_Builder pl, Scoreboard scoreboard){
        this.main = pl;
        tabList = scoreboard;
    }

    public void createTabList(){
        Objective objective = tabList.registerNewObjective("string","string1");
        objective.setDisplayName(main.getGameTitle());
        objective.setDisplaySlot(DisplaySlot.PLAYER_LIST);
        objective.getScore("§b====================").setScore(1);
    }
}
