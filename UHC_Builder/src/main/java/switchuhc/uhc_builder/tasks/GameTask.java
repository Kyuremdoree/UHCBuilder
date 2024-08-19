package switchuhc.uhc_builder.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import switchuhc.uhc_builder.UHC_Builder;
import switchuhc.uhc_builder.utilitaires.GameStatue;

public class GameTask extends BukkitRunnable {
    private UHC_Builder main;

    private int pvptime;
    private int borduretime;
    public GameTask(UHC_Builder pl)
    {
        this.main = pl;
        pvptime = main.getGame().getTemps().getTempsPVP();
        borduretime = main.getGame().getTemps().getTempsBordure();
    }

    @Override
    public void run() {
        main.getGame().getCustomScoreboard().updateScoreBoardTimer(main.getGame().getTemps());
        if (main.getGame().getTemps().getTempsActuel() == 0) {
            Bukkit.broadcastMessage("§eVous êtes invunérable aux dégats pendant 30 seconde !");

        }
        if (main.getGame().getTemps().getTempsActuel() == 30) {
            Bukkit.broadcastMessage("§4Vous êtes vunérable au dégat !");
        }

        if (main.getGame().getTemps().getTempsPVP() >= 0) {
            switch (main.getGame().getTemps().getTempsPVP()) {
                case 5:
                    Bukkit.broadcastMessage("§4PvP activé dans 5 !");
                    break;
                case 4:
                    Bukkit.broadcastMessage("§4PvP activé dans 4 !");
                    break;
                case 3:
                    Bukkit.broadcastMessage("§4PvP activé dans 3 !");
                    break;
                case 2:
                    Bukkit.broadcastMessage("§4PvP activé dans 2 !");
                    break;
                case 1:
                    Bukkit.broadcastMessage("§4PvP activé dans 1 !");
                    break;
                case 0:
                    Bukkit.broadcastMessage("§4PvP activé !");
                    break;
            }
            main.getGame().getTemps().decrementPVP(1);
        }
        if (main.getGame().getTemps().getTempsActuel() == pvptime + 1) {
            main.setGameStatue(GameStatue.Meetup);
        }

        if (main.getGame().getTemps().getTempsBordure() >= 0) {
            switch (main.getGame().getTemps().getTempsBordure()) {
                case 5:
                    Bukkit.broadcastMessage("§4Réduction de la Bordure dans 5 !");
                    break;
                case 4:
                    Bukkit.broadcastMessage("§4Réduction de la Bordure dans 4 !");
                    break;
                case 3:
                    Bukkit.broadcastMessage("§4Réduction de la Bordure dans 3 !");
                    break;
                case 2:
                    Bukkit.broadcastMessage("§4Réduction de la Bordure dans 2 !");
                    break;
                case 1:
                    Bukkit.broadcastMessage("§4Réduction de la Bordure dans 1 !");
                    break;
                case 0:
                    Bukkit.broadcastMessage("§4Réduction de la Bordure !");
                    break;
            }
            main.getGame().getTemps().decrementBorder(1);
        }

        if (main.getGame().getTemps().getTempsActuel() == borduretime + 1) {
            main.setGameStatue(GameStatue.Lategame);
            Bukkit.getScheduler().runTaskTimer(main, new BukkitRunnable() {
                @Override
                public void run() {
                    if (main.getGame().getWb().getSize() >= 150) {
                        main.getGame().getWb().setSize(main.getGame().getWb().getSize() - ((double) 1/20));
                    }

                    else {
                        main.setGameStatue(GameStatue.Endgame);
                    }
                }
            }, 0, 1);
        }

        if (main.getGameStatue() == GameStatue.Terminated) {
            cancel();
        }

        if(main.getGame().getTemps().getTempsActuel() % (60*20) == 0 ){
            main.getGame().setNbJour((int)(main.getGame().getTemps().getTempsActuel() / (60*20))+1);
            main.getGame().getCustomScoreboard().updateScoreBoardNbJour();
        }

        main.getGame().getTemps().incrementActuel(1);
    }

}
