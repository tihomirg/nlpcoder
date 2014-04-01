package examples.java.util.date;
import java.util.Date;

public class T3 {

  public static void main(String args[]) {
    Date currentDate = new Date();

    long msec = currentDate.getTime();
    
    long days = msec/(24 * 60 * 60 * 1000);

    System.out.println(days);
  }
}