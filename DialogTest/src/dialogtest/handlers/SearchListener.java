package dialogtest.handlers;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import search.SearchEngine;

public class SearchListener implements Listener {
	
	private List snippets;
	private SearchEngine searchEngine;

	public SearchListener(List snippets, SearchEngine searchEngine) {
		this.snippets = snippets;
		this.searchEngine = searchEngine;
	}

	@Override
	public void handleEvent(Event event) {
		Text t = (Text) event.widget;
		String[] selections = searchEngine.run(t.getText());
		
		snippets.removeAll();
		for (String string : selections) {
			snippets.add(string);
		}
		
		snippets.setFocus();
		snippets.select(0);
		snippets.redraw();
	}
}
