package examples.java.io.streamtokenizer;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.StreamTokenizer;

public class T5 {

  public static void main(String args[]) {

    try {
      FileReader fr = new FileReader(args[0]);

      BufferedReader br = new BufferedReader(fr);

      StreamTokenizer st = new StreamTokenizer(br);

      // Consider end-of-line as a token
      st.eolIsSignificant(true);

      // Declare variable to count lines
      int lines = 1;

      // Process tokens
      while (st.nextToken() != StreamTokenizer.TT_EOF) {
        switch (st.ttype) {
        case StreamTokenizer.TT_EOL:
          ++lines;
        }
      }

      System.out.println("There are " + lines + " lines");

      fr.close();
    } catch (Exception e) {
      System.out.println("Exception: " + e);
    }
  }
}