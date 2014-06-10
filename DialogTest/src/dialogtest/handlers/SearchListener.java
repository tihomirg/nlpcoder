package dialogtest.handlers;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

public class SearchListener implements Listener {
	
	private List snippets;
	private ExpressionSynthesizer synthesizer;

	public SearchListener(List snippets, ExpressionSynthesizer synthesizer) {
		this.snippets = snippets;
		this.synthesizer = synthesizer;
	}

	@Override
	public void handleEvent(Event event) {
		Text t = (Text) event.widget;
		String[] selections = synthesizer.run();
		
		snippets.removeAll();
		for (String string : selections) {
			snippets.add(string);
		}
		
		snippets.setFocus();
		snippets.select(0);
		snippets.redraw();
	}
}
