package examples.java.util.hashtable;
import java.util.Hashtable;

public class T15{
  public static void main(String[] s) {
    Hashtable<String,String> table = new Hashtable<String,String>();
    table.put("key1", "value1");
    table.put("key2", "value2");
    table.put("key3", "value3");

    Hashtable<String,String> table2 = new Hashtable<String,String>();
    table2.put("key4", "value4");
    table2.put("key5", "value5");
    table2.put("key6", "value6");

    table2.putAll(table);

    System.out.println(table2);
  }
}