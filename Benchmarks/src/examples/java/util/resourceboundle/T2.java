package examples.java.util.resourceboundle;
import java.util.Locale;
import java.util.ResourceBundle;

public class T2 {

  public static void main(String[] args) {
    ResourceBundle bundle = ResourceBundle.getBundle("MessagesBundle", Locale.UK);
    System.out.println("Message in " + Locale.UK + ": " + bundle.getString("greeting"));

    Locale.setDefault(new Locale("in", "ID"));
    bundle = ResourceBundle.getBundle("MessagesBundle");
    System.out.println("Message in " + Locale.getDefault() + ": " + bundle.getString("greeting"));
  }
}