package dialogtest.handlers;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

public class ResultListener implements KeyListener {

	private ITextViewer viewer;
	private IDocument doc;
	private int offset;
	private Shell searchPopup;
	private boolean firstTime = true;
	
	public ResultListener(Shell searchPopup, ITextViewer viewer, IDocument doc, int offset) {
		this.searchPopup = searchPopup;
		this.viewer = viewer;
		this.doc = doc;
		this.offset = offset;
	}

	@Override
	public void keyReleased(KeyEvent event) {
		if(event.keyCode == SWT.CR){
			if (!this.firstTime){
			List list = (List) event.widget;
			String selection = list.getSelection()[0];

			try {
				doc.replace(offset, 0, selection);
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			int cursor = offset + selection.length();
			viewer.setSelectedRange(cursor, 0);
			viewer.revealRange(cursor, 0);
			
			searchPopup.close();
			} else this.firstTime = false;
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

}
