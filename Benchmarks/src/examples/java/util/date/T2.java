package examples.java.util.date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class T2 {

  public static void main(String[] a) throws Exception{

    DateFormat df = new SimpleDateFormat ("yyyy-MM-dd");

    Date d1 = df.parse("2001-01-01");

    Date d2 = df.parse("2000-01-01");

    String relation = null;
    if (d1.equals(d2))
      relation = "the same date as";
    else if (d1.before(d2))
      relation = "before";
    else if (d1.after(d2))
      relation = "after";
    System.out.println(d1 + " is " + relation + ' ' + d2);
  }
}