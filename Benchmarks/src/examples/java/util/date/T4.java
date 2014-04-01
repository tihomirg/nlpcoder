package examples.java.util.date;
import java.util.*;

public class T4 {

  public static void main(String args[]) {

    Date date = new Date();

    long msec = date.getTime();

    msec += 100 * 24 * 60 * 60 * 1000L;

    date.setTime(msec);

    System.out.println(date);
  }
}