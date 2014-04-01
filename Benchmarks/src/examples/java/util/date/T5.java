package examples.java.util.date;

import java.util.Date;

public class T5 {

  public static void main(String args[]) {
    Date currentDate = new Date();

    System.out.println(currentDate);

    // Get date object initialized to the epoch (Jan 1 1970)
    Date epoch = new Date(0);

    System.out.println(epoch);
  }
}