package examples.java.util.collections;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.swing.JPanel;

/**  
 * Demonstrates the use of instance comparisons.
 *
 * @author <a href=mailto:kraythe@arcor.de>Robert Simmons jr. (kraythe)</a>
 * @version $Revision: 1.3 $
 */
public class T24 {
  /** A set of demon objects. */
  public static final Set OBJECT_SET;

  static {
    Set objectSet = new HashSet();
    objectSet.add(new Integer(5));
    objectSet.add(new String("Hardcore Java"));
    objectSet.add(new Float(22.5f));
    objectSet.add(new JPanel());
    objectSet.add(new Character('x'));
    objectSet.add(new ArrayList());
    objectSet.add(new Double(354.5676));
    objectSet.add(null);
    OBJECT_SET = Collections.unmodifiableSet(objectSet);
  }

  /** 
   * Demo method.
   *
   * @param args Command Line arguments.
   */
  public static void main(final String[] args) {
    final Iterator iter = OBJECT_SET.iterator();
    Object obj = null;

    while (iter.hasNext()) {
      obj = iter.next();
      if (obj instanceof Number) {
        System.out.println(obj);
      }
    }
  }
}
