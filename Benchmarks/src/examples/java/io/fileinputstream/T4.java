package examples.java.io.fileinputstream;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class T4 {

  public static void main(String[] args) throws Exception {
    try {
      String url = "jdbc:odbc:databaseName";
      String driver = "sun.jdbc.odbc.JdbcOdbcDriver";
      String user = "guest";
      String password = "guest";
      FileInputStream fis = new FileInputStream("somefile.txt");
      Class.forName(driver);
      Connection connection = DriverManager.getConnection(url, user, password);
      Statement createTable = connection.createStatement();

      createTable.executeUpdate("CREATE TABLE source_code (name CHAR(20), source LONGTEXT)");

      String ins = "INSERT INTO source_code VALUES(?,?)";
      PreparedStatement statement = connection.prepareStatement(ins);

      statement.setString(1, "TryInputStream"); // Set first field
      statement.setAsciiStream(2, fis, fis.available()); // Stream is source

      int rowsUpdated = statement.executeUpdate();
      System.out.println("Rows affected: " + rowsUpdated);
      connection.close();
    } catch (Exception e) {
      System.err.println(e);
    }
  }
}