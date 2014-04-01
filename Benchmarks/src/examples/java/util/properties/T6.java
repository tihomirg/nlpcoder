package examples.java.util.properties;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.AbstractListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ToolTipManager;

public class T6 extends JList {

  SortedListModel model;

  Properties tipProps;

  public T6(Properties props) {
    model = new SortedListModel();
    setModel(model);
    ToolTipManager.sharedInstance().registerComponent(this);

    tipProps = props;
    addProperties(props);
  }

  private void addProperties(Properties props) {
    Enumeration names = props.propertyNames();
    while (names.hasMoreElements()) {
      model.add(names.nextElement());
    }
  }

  public String getToolTipText(MouseEvent event) {
    Point p = event.getPoint();
    int location = locationToIndex(p);
    String key = (String) model.getElementAt(location);
    String tip = tipProps.getProperty(key);
    return tip;
  }

  public static void main(String args[]) {
    JFrame frame = new JFrame("Custom Tip Demo");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Properties props = System.getProperties();
    T6 list = new T6(props);
    JScrollPane scrollPane = new JScrollPane(list);
    frame.add(scrollPane);
    frame.setSize(300, 300);
    frame.setVisible(true);
  }
}

class SortedListModel extends AbstractListModel {

  SortedSet<Object> model;

  public SortedListModel() {
    model = new TreeSet<Object>();
  }

  public int getSize() {
    return model.size();
  }

  public Object getElementAt(int index) {
    return model.toArray()[index];
  }

  public void add(Object element) {
    if (model.add(element)) {
      fireContentsChanged(this, 0, getSize());
    }
  }

  public void addAll(Object elements[]) {
    Collection<Object> c = Arrays.asList(elements);
    model.addAll(c);
    fireContentsChanged(this, 0, getSize());
  }

  public void clear() {
    model.clear();
    fireContentsChanged(this, 0, getSize());
  }

  public boolean contains(Object element) {
    return model.contains(element);
  }

  public Object firstElement() {
    return model.first();
  }

  public Iterator iterator() {
    return model.iterator();
  }

  public Object lastElement() {
    return model.last();
  }

  public boolean removeElement(Object element) {
    boolean removed = model.remove(element);
    if (removed) {
      fireContentsChanged(this, 0, getSize());
    }
    return removed;
  }
}