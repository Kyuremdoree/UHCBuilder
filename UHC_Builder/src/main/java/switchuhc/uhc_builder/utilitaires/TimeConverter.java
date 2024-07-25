package switchuhc.uhc_builder.utilitaires;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TimeConverter {

    private static final List<String> symbolTime = Arrays.asList("d", "h", "m", "s");
    public static List<Integer> TimeConverteur(int i){
        int hours = i / (60 * 60); // Calcul des heures
        int minutes = (i % (60 * 60)) / 60; // Calcul des minutes restantes après avoir enlevé les heures
        int seconds = i % 60;

        List<Integer> tmp = new ArrayList<Integer>();
        if (hours != 0)
            tmp.add(hours);
        if (minutes != 0)
            tmp.add(minutes);
        tmp.add(seconds);
        return tmp;
    }

    public static String ToString(List<Integer> li){

        DecimalFormat decimalFormat = new DecimalFormat("00");
        String result = "";
        int y = symbolTime.size() - 1;
        for(int i = li.size() - 1; i >= 0; i--){
            result = decimalFormat.format(li.get(i)) + symbolTime.get(y) + result;
            y--;
        }
        return result;
    }
}
