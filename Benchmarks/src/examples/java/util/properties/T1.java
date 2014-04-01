package examples.java.util.properties;
import java.util.Properties;

public class T1 {
    public static void main(String args[]) {
        Properties prop = new Properties();
        prop.put("a", "1");
        prop.put("b", "2");
        prop.put("c", "3");
        Properties book = new Properties(prop);
        book.put("A", "4");
        book.put("B", "5");
        
        System.out.println("a " + book.getProperty("a"));
        System.out.println("A " + book.getProperty("b"));
        System.out.println("c: " + book.getProperty("c"));

        System.out.println("z: " + book.getProperty("z", "default"));
    }
}