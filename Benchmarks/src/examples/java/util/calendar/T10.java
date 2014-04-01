package examples.java.util.calendar;
import java.util.Calendar;

public class T10{

  public static void main(String[] args){
     Calendar calendar = Calendar.getInstance ();
     
     System.out.println(calendar.get(Calendar.YEAR));
     System.out.println(calendar.get(Calendar.MONTH));
     System.out.println(calendar.get(Calendar.DATE));
     System.out.println(calendar.get(Calendar.HOUR));
     System.out.println(calendar.get(Calendar.MINUTE));
     System.out.println(calendar.get(Calendar.SECOND));
     System.out.println(calendar.get(Calendar.MILLISECOND));
  }

}