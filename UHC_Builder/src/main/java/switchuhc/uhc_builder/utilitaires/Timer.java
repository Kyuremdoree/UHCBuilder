package switchuhc.uhc_builder.utilitaires;

import lombok.Getter;
import lombok.Setter;

public class Timer {

    @Getter @Setter
    private int tempsActuel;

    @Getter
    private int tempsBordure;

    @Getter @Setter
    private int tempsInvulnerabilite;

    @Getter
    private int tempsPVP;

    public void setTempsBordure(int tempsBordure) {
        if(tempsBordure >= tempsPVP)
            this.tempsBordure = tempsBordure;
    }

    public void setTempsPVP(int tempsPVP) {
        if (tempsPVP >= this.tempsInvulnerabilite)
            this.tempsPVP = tempsPVP;
    }

    public void decrementPVP(int nb){
        tempsPVP = tempsPVP - nb;
    }

    public void decrementBorder(int nb){
        tempsBordure = tempsBordure - nb;
    }

    public void decrementInvul(int nb){
        tempsInvulnerabilite = tempsInvulnerabilite - nb;
    }

    public void incrementPVP(int nb){
        setTempsPVP(tempsPVP + nb);
    }

    public void incrementBorder(int nb){
        setTempsBordure(tempsBordure + nb);
    }

    public void incrementInvul(int nb){
        tempsInvulnerabilite = tempsInvulnerabilite + nb;
    }

    public void incrementActuel(int nb){
        tempsActuel = tempsActuel + nb;
    }


    public Timer(int tempsBordure, int tempsPVP, int tempsInvulnerabilite){
        tempsActuel = 0;
        setTempsBordure(tempsBordure);
        setTempsPVP(tempsPVP);
        setTempsInvulnerabilite(tempsInvulnerabilite);
    }
}
