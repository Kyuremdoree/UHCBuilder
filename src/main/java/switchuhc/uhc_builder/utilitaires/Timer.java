package switchuhc.uhc_builder.utilitaires;

public class Timer {
    private int tempsActuel;

    private int tempsBordure;

    private int tempsInvulnerabilite;


    private int tempsPVP;

    public int getTempsActuel() {
        return tempsActuel;
    }

    public void setTempsActuel(int tempsActuel) {
        this.tempsActuel = tempsActuel;
    }

    public int getTempsBordure() {
        return tempsBordure;
    }

    public void setTempsBordure(int tempsBordure) {
        if(tempsBordure >= tempsPVP)
            this.tempsBordure = tempsBordure;
    }

    public int getTempsInvulnerabilite() {
        return tempsInvulnerabilite;
    }

    public void setTempsInvulnerabilite(int tempsInvulnerabilite) {
        this.tempsInvulnerabilite = tempsInvulnerabilite;
    }

    public int getTempsPVP() {
        return tempsPVP;
    }

    public void setTempsPVP(int tempsPVP) {
        this.tempsPVP = tempsPVP;
    }

    public void decrementPVP(){
        tempsPVP--;
    }

    public void decrementBorder(){
        tempsBordure--;
    }

    public void decrementInvul(){
        tempsInvulnerabilite--;
    }

    public void incrementPVP(){
        tempsPVP++;
    }

    public void incrementBorder(){
        tempsBordure++;
    }

    public void incrementInvul(){
        tempsInvulnerabilite++;
    }

    public void incrementActuel(){
        tempsActuel++;
    }


    public Timer(int tempsBordure, int tempsPVP, int tempsInvulnerabilite){
        tempsActuel = 0;
        this.tempsBordure = tempsBordure;
        this.tempsPVP = tempsPVP;
        this.tempsInvulnerabilite = tempsInvulnerabilite;
    }
}
