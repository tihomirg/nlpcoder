package dialogtest.handlers;

import java.util.LinkedList;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import api.Local;
import search.ISText;

public class SearchListener implements Listener {
	
	private List snippets;
	private ISText iSText;
	private java.util.List<Local> locals;

	public SearchListener(List snippets, ISText iSText, java.util.List<Local> locals) {
		this.snippets = snippets;
		this.iSText = iSText;
		this.locals = locals;
	}

	@Override
	public void handleEvent(Event event) {
		Text t = (Text) event.widget;
		String[] selections = iSText.run(t.getText(), this.locals);
						
		snippets.removeAll();
		for (String string : selections) {
			snippets.add(string);
		}
		
		snippets.setFocus();
		snippets.select(0);
		snippets.redraw();
	}
}
