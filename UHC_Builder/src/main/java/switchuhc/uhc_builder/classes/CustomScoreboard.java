package switchuhc.uhc_builder.classes;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import switchuhc.uhc_builder.UHC_Builder;
import switchuhc.uhc_builder.utilitaires.TimeConverter;
import switchuhc.uhc_builder.utilitaires.Timer;

public class CustomScoreboard {
    @Getter @Setter
    private Scoreboard scoreboard;
    private UHC_Builder main;

    public CustomScoreboard(UHC_Builder pl){
        scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        main = pl;
    }

    public void createScoreboardGame(Timer timer, String title) {
        Objective objective = scoreboard.registerNewObjective("example", "dummy");
        objective.setDisplayName(title);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.getScore("§b====================").setScore(14);

        objective.getScore("☰§b Informations").setScore(12);

        Team NbJoueur = objective.getScoreboard().registerNewTeam("NbJoueur");
        NbJoueur.addEntry("  §6Joueurs : §f");
        NbJoueur.setSuffix(String.valueOf(main.getPlayerList().size()));
        objective.getScore("  §6Joueurs : §f").setScore(11);

        Team tmpJeu = objective.getScoreboard().registerNewTeam("tmpJeu");
        tmpJeu.addEntry("  §6Durée : §f");
        tmpJeu.setSuffix(TimeConverter.ToString(TimeConverter.TimeConverteur(timer.getTempsActuel())));
        objective.getScore("  §6Durée : §f").setScore(9);

        objective.getScore("  ").setScore(8);

        Team tmpPVP = objective.getScoreboard().registerNewTeam("tmpPVP");
        tmpPVP.addEntry("  §6PVP : §f");
        if (timer.getTempsPVP() < 0) tmpPVP.setSuffix("✔");
        else tmpPVP.setSuffix(TimeConverter.ToString(TimeConverter.TimeConverteur(timer.getTempsPVP())));
        objective.getScore("  §6PVP : §f").setScore(7);

        Team tmpBord = objective.getScoreboard().registerNewTeam("tmpBord");
        tmpBord.addEntry("  §6Bordure : §f");
        if (timer.getTempsBordure() < 0) tmpBord.setSuffix("✔");
        else tmpBord.setSuffix(TimeConverter.ToString(TimeConverter.TimeConverteur(timer.getTempsBordure())));
        objective.getScore("  §6Bordure : §f").setScore(6);

        Team tmpNbJour = objective.getScoreboard().registerNewTeam("NbJour");
        tmpNbJour.addEntry("  §6Episode : §f");
        tmpNbJour.setSuffix(String.valueOf(0));
        objective.getScore("  §6Episode : §f").setScore(5);

        objective.getScore(" ").setScore(4);

        Team tmpPkills = objective.getScoreboard().registerNewTeam("kills");
        tmpPkills.addEntry("  §6Kills : §f");
        tmpPkills.setSuffix(String.valueOf(0));
        objective.getScore("  §6Kills : §f").setScore(3);

        objective.getScore("").setScore(2);
        objective.getScore("§bplay.montenegroland.fr").setScore(1);
    }

    public void updateScoreBoardTimer(Timer timer)
    {
        scoreboard.getTeam("tmpJeu").setSuffix(TimeConverter.ToString(TimeConverter.TimeConverteur(timer.getTempsActuel())));

        if (timer.getTempsPVP() < 0) scoreboard.getTeam("tmpPVP").setSuffix("✔");
        else scoreboard.getTeam("tmpPVP").setSuffix(TimeConverter.ToString(TimeConverter.TimeConverteur(timer.getTempsPVP())));

        if (timer.getTempsBordure() < 0) scoreboard.getTeam("tmpBord").setSuffix("✔");
        else scoreboard.getTeam("tmpBord").setSuffix(TimeConverter.ToString(TimeConverter.TimeConverteur(timer.getTempsBordure())));
    }

    public void updateScoreBoardNbPlayer(){
        scoreboard.getTeam("NbJoueur").setSuffix(String.valueOf(main.getPlayerList().size()));
    }

    public void updateScoreBoardNbJour(){
        scoreboard.getTeam("NbJour").setSuffix(String.valueOf(main.getGame().getNbJour()));
    }

    public void updateScoreBoardKills(Player player){
        player.getScoreboard().getTeam("kills").setSuffix(String.valueOf(player.getStatistic(Statistic.PLAYER_KILLS)));
    }
}
