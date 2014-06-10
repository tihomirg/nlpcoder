package dialogtest.handlers;

import org.eclipse.swt.*;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet309 {

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell();
		shell.setSize(600, 190);
		shell.setText("Expression Search");
		
		shell.setLocation(1000, 300);
		
		shell.setLayout(new GridLayout(1, true));
		Text text;

		text = new Text(shell, SWT.SEARCH);
		text.setMessage("search");
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

	    final List list = new List(shell, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL);
	    
	    Listener inputListener = new Listener() {
	    	@Override
	    	public void handleEvent(Event event) {
	    		Text t = (Text) event.widget;
	    		System.out.println(t.getText());
	    		list.setFocus();
	    		list.select(0);
	    		list.redraw();
	    	}
	    };
	    text.addListener(SWT.DefaultSelection, inputListener);
	    
	    list.setLayoutData(new GridData(GridData.FILL_HORIZONTAL)); 
		Listener listListener = new Listener() {
			@Override
			public void handleEvent(Event event) {
				List list = (List) event.widget;
				String selection = list.getSelection()[0];
				System.out.println(selection);
			}
		};	    
		list.addListener(SWT.SINGLE, listListener);
		
		list.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent event) {
				if(event.keyCode == SWT.CR){
				List list = (List) event.widget;
				String selection = list.getSelection()[0];
				System.out.println(selection);
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		
		
	    String[] listItems = { "A", "B", "C", "D", "E" };
	    
	    // Add the items, one by one
	    for (int i = 0, n = listItems.length; i < n; i++) {
	      list.add(listItems[i]);
	    }
		
		//shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();

	}
}