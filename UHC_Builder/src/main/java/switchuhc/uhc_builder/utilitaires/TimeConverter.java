package switchuhc.uhc_builder.utilitaires;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TimeConverter {

    private static List<String> symbolTime = Arrays.asList("d", "h", "m", "s");
    public static List<Integer> TimeConverteur(int i){
        int hours = i / (60 * 60); // Calcul des heures
        int minutes = (i % (60 * 60)) / 60; // Calcul des minutes restantes après avoir enlevé les heures
        int seconds = i % 60;

        List<Integer> tmp = new ArrayList<Integer>();
        tmp.add(hours);
        tmp.add(minutes);
        tmp.add(seconds);
        return tmp;
    }

    public static String ToString(List<Integer> li){

        DecimalFormat decimalFormat = new DecimalFormat("00");
        String result = "";
        for(int i = li.size() - 1; i >= 0; i--){
            result = result + decimalFormat.format(li.get(i)) + symbolTime.get(i);
        }
        return result;
    }
}
