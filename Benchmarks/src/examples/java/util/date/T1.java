package examples.java.util.date;
import java.util.Date;
import java.util.GregorianCalendar;

public class T1 {

  public static void main(String[] a) throws Exception {
    Date d1 = new GregorianCalendar(2000,11,31,23,59).getTime();

    Date today = new Date();

    long diff = today.getTime() - d1.getTime();

    System.out.println("The 21st century (up to " + today + 
      ") is " + (diff / (1000*60*60*24)) + " days old.");
  }
}