package examples.java.util.timezone;
import java.util.Arrays;
import java.util.TimeZone;

public class T1 {

    public static void main(String[] args) {
        String[] allTimeZones = TimeZone.getAvailableIDs();
        
        Arrays.sort(allTimeZones);

        for (String timezone : allTimeZones) {
            System.out.println(timezone);
        }
    }
}